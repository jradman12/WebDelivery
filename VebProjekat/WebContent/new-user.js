Vue.component("new-user", {
    data: function () {
        return {
            availableManagers: [],
            errors: [],
            managers: [],
            newManager : {}
        }
    },
    template: ` 
    <div class="container">

        <form class="well form-horizontal" style="align-content: center;">
            <fieldset>

                <legend>
                    <h2 style="text-align: center;"><b>Dodaj novog menadžera</b></h2>
                </legend><br>

                <div class="form-group">
                    <label class="col-md-4 control-label">Tip</label>
                    <div class="col-md-4 selectContainer">
                        <div class="input-group">
                            <select style="width: 300px;" class="form-control selectpicker">
                                <option value="">Menadžer</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Korisničko ime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newManager.username" placeholder="Korisničko ime..." class="form-control" style="width: 300px;"
                                type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Lozinka</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newManager.password"  placeholder="Lozinka..." class="form-control" style="width: 300px;" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Ime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newManager.fistName" placeholder="Ime..." class="form-control" style="width: 300px;" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Prezime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newManager.lastName" placeholder="Prezime..." class="form-control" style="width: 300px;" type="text">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Datum rođenja</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newManager.dateOfBirth" type="date" class="form-control" style="width: 300px;" >
                       </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Pol</label>
                    <div class="col-md-4 selectContainer">
                        <div class="input-group">
                            <select v-model="newManager.gender" style="width: 300px;" class="form-control selectpicker">
                                <option value="">Pol</option>
                                <option>MALE</option>
                                <option>FEMALE</option>
                                <option>OTHER</option>
                            </select>
                        </div>
                    </div>
                </div>

                

                <!-- Success message -->
                <div class="alert alert-success" role="alert" id="success_message"> <i
                        class="glyphicon glyphicon-thumbs-up"></i> Uspješno kreiran korisnik!</div>

                    <div class="form-group">
                        <label class="col-md-4 control-label"></label>
                        <div class="col-md-4"><br>
                            <button type="button" class="section-btn" @click="addNewManager()"> Potvrdi </button>
                        </div>
                    </div>

                <div><br><br><br><br><br><br><br></div>

            </fieldset>
        </form>
    </div>
    </div>
`
    ,
    methods: {
        addNewManager:function(){

            // console.log('clicked sendme');
            // this.$root.$emit('sendin', this.newManager);
        //    this.$router.push('/')

        // add logic for registering new manager, so when mounted other route will fetch new data FROM BACKEND
        // also u should try this with prevent default bc u forgot u dumbass
        // also try without push goddamit as i remember it causes reload aka lost data so yeAH two more lol
        console.log('in ADDNEWMANAGER')
        axios
        .post('rest/managers/registration', {
       
                     "fistName": this.newManager.fistName,
                     "lastName" : this.newManager.lastName,
                     "dateOfBirth" : this.newManager.dateOfBirth,
                     "gender" : this.newManager.gender,
                     "username": this.newManager.username, 
                     "password" : this.newManager.password,
                     "restaurant" : null,
                     "role" : "MANAGER"
        })
        .then(response => {
            this.message = response.data;
            window.location.assign(response.data)
        })
        .catch(err => {
            console.log("There has been an error! Please check this out: ");
            console.log(err);
        })

         console.log('lets push it back!')
         this.$router.push('/')
            // it should cause page to reload???
            //this.$router.push('/') it does not :)))
         }
      }
});