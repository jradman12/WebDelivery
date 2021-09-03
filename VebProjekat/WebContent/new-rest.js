Vue.component("new-rest", {
	data: function () {
		    return {
                rests : [],
                availableManagers: [],
                newRestaurant : {},
                message : {},
                dobio : '-'
		    }
	},
	template: ` 
    <div class="container">

          <form class="well form-horizontal">
               <fieldset>

                    <!-- Form Name -->
                    <legend>
                         <center>
                              <h2><b>Dodaj novi restoran</b></h2>
                         </center>
                    </legend><br>

                    <!-- Text input-->

                    <div class="form-group">
                         <label class="col-md-4 control-label">Naziv</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="input-group">
                                   <input placeholder="Naziv restorana" class="form-control" style="width: 300px;"
                                        type="text" v-model="newRestaurant.name">
                              </div>
                         </div>
                    </div>

                    <div class="form-group">
                         <label class="col-md-4 control-label">Tip</label>
                         <div class="col-md-4 selectContainer">
                              <div class="input-group">
                                   <select v-model="newRestaurant.typeOfRestaurant" name="department"
                                        style="width: 300px;" class="form-control selectpicker">
                                        <option value="">Tip restorana</option>
                                        <option value="Italijanski">Italijanski</option>
                                        <option value="Kineski">Kineski</option>
                                        <option value="Indijski">Indijski</option>
                                        <option value="Roštilj">Roštilj</option>
                                        <option value="Vegan">Vegan</option>
                                   </select>
                              </div>
                         </div>
                    </div>

                    <!-- Text input-->

                    <div class="form-group">
                         <label class="col-md-4 control-label">Lokacija</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="input-group">
                                   <input v-model="newRestaurant.location" placeholder="Lokacija" class="form-control" type="text" style="width: 300px;">
                              </div>
                         </div>
                    </div>

                    <div class="form-group">
                         <label class="col-md-4 control-label">Logo</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="custom-file">
                                   <input type="file" id="file" ref="file"
                                        v-on:change="onChangeFileUpload()" class="custom-file-input"
                                        style="width: 300px;">
                              </div>
                         </div>
                    </div>


                    <div class="form-group">
                         <label class="col-md-4 control-label">Menadžer</label>
                         <div class="col-md-4 selectContainer">
                                   <div class="input-group" v-if="availableManagers.length">
                                        <select name="department" class="form-control selectpicker"
                                             style="width: 300px;">
                                             <option value="">Izaberi menadžera</option>
                                             <option v-for="man in availableManagers">{{ man.username }}</option>
                                        </select>
                                   </div>
                                   <div class="input-group" v-if="!availableManagers.length">
                                        <a href="addNewRest.html#/newManager" >Dodaj novog menadžera {{ dobio }}</a>
                                   </div>

                         </div>
                    </div>


                    <!-- Select Basic -->

                    <!-- Success message -->
                    <!---- <div class="alert alert-success" role="alert" id="success_message"> <i
                              class="glyphicon glyphicon-thumbs-up"></i> Uspješno kreiran restoran!</div>-->

                    <!-- Button -->
                    <div class="form-group" style="align-content: center;">
                         <label class="col-md-4 control-label"></label>
                         <div class="col-md-4"><br>
                              &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  
                              <button style="text-align: center;" type="submit" @click="registerRestaurant"
                                   class="section-btn">Potvrda</button>
                         </div>
                    </div>

                    <div class="form-group" style="align-content: center;">
                    </div>
                    


                    <div><br><br><br><br><br><br><br><br><br><br><br><br><br></div>

               </fieldset>
          </form>
     </div>
`
	, 
	methods : {
        registerRestaurant: function (event) {
            event.preventDefault();

            console.log(this.newRestaurant.logo)

            this.errors = [];
            if (!this.errors.length) {
                let aName = this.newRestaurant.location.split(",")[0];
                let aCity = this.newRestaurant.location.split(",")[1];
                let aPostCode = this.newRestaurant.location.split(",")[2];

                axios
                    .post('rest/restaurants/registerNewRestaurant', {

                        "typeOfRestaurant": this.newRestaurant.typeOfRestaurant,
                        "name": this.newRestaurant.name,
                        "logo": this.newRestaurant.logo,
                        "location" : {
                            "latitude" : 19.84,
                            "longitude" : 24.24,
                            "address" : {
								"addressName" : aName,
								"city" : aCity,
								"postalCode" : aPostCode
							}
                        }
                    })
                    .then(response => {
                        this.message = response.data;
                    })
                    .catch(err => {
                        console.log("There has been an error! Please check this out: ");
                        console.log(err);
                    })
                return true;
            }
            this.errors.forEach(element => {
                console.log(element)
            });
        }
	},
	mounted () {
          this.$bus.$on('sendin', function(x) {
               console.log('poslali smo ovo: ' + x);
             this.myManager.username = x;
          }.bind(this));
        axios
            .get('rest/restaurants/getAllRestaurants')
            .then(response => (this.rests = response.data)),
            axios
            .get('rest/managers/getAllAvailableManagers')
            .then(response => (this.availableManagers = response.data))
           
    }
});