toastr.options = {
  closeButton: false,
  debug: false,
  newestOnTop: false,
  progressBar: false,
  positionClass: "toast-top-right",
  preventDuplicates: true,
  onclick: null,
  showDuration: "300",
  hideDuration: "1000",
  timeOut: "5000",
  extendedTimeOut: "1000",
  showEasing: "linear",
  hideEasing: "linear",
  showMethod: "fadeIn",
  hideMethod: "fadeOut",
};

Vue.component("admin-addNewRestaurant", {
  data() {
    return {
      availableManagers: [],
      newRestaurant: {},
      file: {},
      errors: [],
      managers: [],
      mapLocation: "",
      selected: {},
    };
  },

  template: `
  <div>

  <img src="images/ce3232.png" width="100%" height="90px">

    <div id="usersiii" class="container">
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
                                        <option value="Fast food">Fast food</option>
                                   </select>
                              </div>
                         </div>
                    </div>

                    <!-- Text input-->



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
                                   <select v-model="selected"  name="department" class="form-control selectpicker" style="width: 300px;">
                                        <option>Izaberi menadžera</option>
                                        <option v-for="man in availableManagers">{{ man.username }}</option>
                                   </select>
                              </div>
                              <div class="input-group" v-if="!availableManagers.length">
                                   <a href="addNewUser.html">Dodaj novog menadžera</a>
                              </div>

                         </div>
                    </div>

                    <div class="form-group">
                         <label class="col-md-4 control-label">Lokacija</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="input-group">
                                   <input v-model="newRestaurant.location" id="pac-input" placeholder="Traži.." class="form-control"
                                        type="text" style="width: 300px;">
                              </div>
                         </div>
                    </div>

                    <!-- <div class="form-group">
                         <label class="col-md-4 control-label"> </label>
                         <div class="col-md-4 inputGroupContainer">
                              <p>Unesite adresu u formatu: [adresa, grad, poštanski broj] ili odaberite lokaciju s mape</p>

                              <div class="input-group">
                                   <input placeholder="Traži na mapi..." id="pac-input" class="form-control" style="width: 300px;"
                                        type="text" v-model="mapLocation">
                                        
                              </div>
                         </div>
                    </div> -->

                    <div class="form-group">
                         
                         <div id="map"
                              style="width:70%;height:400px; margin:auto; border-style:inset; border-color:#ce32322d;">
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
                              &nbsp&nbsp&nbsp  &nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                              <button style="text-align: center;" type="submit" @click="registerRestaurant"
                                   class="section-btn">Potvrda</button>
                         </div>
                    </div>


                    <div><br><br><br><br><br></div>

               </fieldset>
          </form>
          </div>
     </div>
  `,

  mounted() {
    axios
      .get("rest/managers/getAllAvailableManagers")
      .then((response) => (this.availableManagers = response.data));
  },

  methods: {
    onChangeFileUpload($event) {
      this.file = this.$refs.file.files[0];
      this.encodeImage(this.file);
      console.log(this.newRestaurant.logo);
    },
    encodeImage(input) {
      if (input) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.newRestaurant.logo = e.target.result;
        };
        reader.readAsDataURL(input);
      }
    },
    registerRestaurant: function (event) {
      event.preventDefault();

      console.log(this.newRestaurant.logo);
      if(this.newRestaurant.typeOfRestaurant == undefined || this.newRestaurant.name == '' || this.newRestaurant.logo == undefined 
      || this.selected == undefined  || this.newRestaurant.location == undefined){
        toastr["error"]("Sva polja moraju biti popunjena!");
      }else{
          this.errors = [];
          if (!this.errors.length) {
          let aName = this.newRestaurant.location.split(",")[0];
          let aCity = this.newRestaurant.location.split(",")[1];
          let aPostCode = this.newRestaurant.location.split(",")[2];

          axios
               .post("rest/restaurants/registerNewRestaurant", {
               managerID: this.selected,
               typeOfRestaurant: this.newRestaurant.typeOfRestaurant,
               name: this.newRestaurant.name,
               logo: this.newRestaurant.logo,
               location: {
               latitude: 19.84,
               longitude: 24.24,
               address: {
                    addressName: aName,
                    city: aCity,
                    postalCode: aPostCode,
               },
               },
               })
               .then((response) => {
               toastr["success"]("Uspješno dodat novi restoran!");
               window.location.assign("adminDashboard.html#/adminsRestaurants");
               })
               .catch((err) => {
               toastr["error"]("Trenutno nije moguće dodati restoran.");
               });
          return true;
          }
          this.errors.forEach((element) => {
          console.log(element);
          });
     }
    },
  },
});
