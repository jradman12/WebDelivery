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

Vue.component("admin-addNewUser", {
  data() {
    return {
      newUser: {},
      errors: [],
      message: null,
    };
  },

  template: ` 
    <div>
		<img src="images/ce3232.png" width="100%" height="90px">


        <div class="container">

        <form id="newUser" class="well form-horizontal" style="align-content: center;">
            <fieldset>

                <legend>
                    <h2 style="text-align: center;"><b>Dodaj novog korisnika</b></h2>
                </legend><br>

                <div class="form-group">
                    <label class="col-md-4 control-label">Tip</label>
                    <div class="col-md-4 selectContainer">
                        <div class="input-group">
                            <select v-model="newUser.type" style="width: 300px;" id="tipUsera" class="form-control selectpicker" required>
                                <option value="">Tip korisnika</option>
                                <option value="MANAGER">Menadžer</option>
                                <option value="DELIVERER">Dostavljač</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Korisničko ime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newUser.username" id="korisnickoIme" placeholder="Korisničko ime..." class="form-control"
                                style="width: 300px;" type="text" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Lozinka</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newUser.password" placeholder="Lozinka..." id="lozinka" class="form-control"
                                style="width: 300px;" type="text" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Ime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newUser.fistName" id="ime" placeholder="Ime..." class="form-control"
                                style="width: 300px;" type="text" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Prezime</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newUser.lastName" id="prezime" placeholder="Prezime..." class="form-control"
                                style="width: 300px;" type="text" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Datum rođenja</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                            <input v-model="newUser.dateOfBirth" type="date" id="datum" class="form-control" style="width: 300px;" required>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">Pol</label>
                    <div class="col-md-4 selectContainer">
                        <div class="input-group">
                            <select v-model="newUser.gender" style="width: 300px;" id="pol" class="form-control selectpicker" required>
                                <option value="">Pol</option>
                                <option value="MALE">Muško</option>
                                <option value="FEMALE">Žensko</option>
                                <option value="OTHER">Ostalo</option>
                            </select>
                        </div>
                    </div>
                </div>



                <!-- Success message -->
                <div class="alert alert-success" role="alert" id="success_message"> <i
                        class="glyphicon glyphicon-thumbs-up"></i> Uspješno kreiran korisnik!</div>

                <!-- Button -->
                <div class="form-group">
                    <label class="col-md-4 control-label"></label>
                    <div class="col-md-4"><br>
                        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit"
                            class="section-btn" @click="addNewUser">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspPOTVRDI
                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>
                    </div>
                </div>

                <div><br><br><br><br><br><br><br></div>

            </fieldset>
        </form>
    </div>
    </div><!-- /.container -->

`,

  methods: {
    addNewUser: function (event) {
      event.preventDefault();
      console.log(this.newUser.password)
      if(this.newUser.fistName == '' || this.newUser.lastName == '' || this.newUser.type == undefined 
      || this.newUser.dateOfBirth == undefined || this.newUser.gender == undefined || this.newUser.username == '' || this.newUser.password == ''){
        toastr["error"]("Sva polja moraju biti popunjena!");
      }else{
        if (this.newUser.type === "MANAGER") {
          axios
            .post("rest/managers/registration", {
              fistName: this.newUser.fistName,
              lastName: this.newUser.lastName,
              dateOfBirth: this.newUser.dateOfBirth,
              gender: this.newUser.gender,
              username: this.newUser.username,
              password: this.newUser.password,
              restaurantID: null,
              role: "MANAGER",
            })
            .then((response) => {
              this.message = response.data;
              toastr["success"]("Uspješno registrovan novi menadžer!");
              window.location.assign(response.data);
            })
            .catch((err) => {
              console.log("There has been an error! Please check this out: ");
              console.log(err);
            });
        } else if (this.newUser.type === "DELIVERER") {
          axios
            .post("rest/deliverers/registration", {
              fistName: this.newUser.fistName,
              lastName: this.newUser.lastName,
              dateOfBirth: this.newUser.dateOfBirth,
              gender: this.newUser.gender,
              username: this.newUser.username,
              password: this.newUser.password,
              role: "DELIVERER",
            })
            .then((response) => {
              this.message = response.data;
              toastr["success"]("Uspješno registrovan novi dostavljač!");
              window.location.assign(response.data);
            })
            .catch((err) => {
              console.log("There has been an error! Please check this out: ");
              console.log(err);
            });
        }
      
        document.getElementById("ime").value = "";
        document.getElementById("prezime").value = "";
        document.getElementById("datum").value = "";
        document.getElementById("pol").value = "";
        document.getElementById("tipUsera").value = "";
        document.getElementById("korisnickoIme").value = "";
        document.getElementById("lozinka").value = "";
    }
    }
  },
});
