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
                            <input v-model="newManager.password" placeholder="Lozinka..." class="form-control" style="width: 300px;" type="text">
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
                                <option>Muški</option>
                                <option>Ženski</option>
                                <option>Ostalo</option>
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
                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit"
                            class="section-btn" v-on:click="sendme()">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="addNewRest.html#/">POTVRDI</a>
                                &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>

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
        sendme:function(){
            console.log('clicked sendme');
            this.$bus.$emit('sendin', this.newManager.username)
        }
      },

    mounted() {
        axios
            .get('rest/managers/getAllAvailableManagers')
            .then(response => (this.availableManagers = response.data))
    }
});