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
  timeOut: "3000",
  extendedTimeOut: "1000",
  showEasing: "linear",
  hideEasing: "linear",
  showMethod: "fadeIn",
  hideMethod: "fadeOut",
};
let reg = new Vue({
  el: "#burn",
  data: {
    newUser: {},
    errors: [],
    message: null,
    rePassword: "",
  },
  methods: {
    checkRegistration: function (event) {
      event.preventDefault();

      console.log(this.newUser.fistName);

      axios
        .post("rest/registration", {
          fistName: this.newUser.fistName,
          lastName: this.newUser.lastName,
          dateOfBirth: this.newUser.dateOfBirth,
          gender: this.newUser.gender,
          username: this.newUser.username,
          password: this.newUser.password,
        })
        .then((response) => {
          document.getElementById("imeKupca").value = "";
          document.getElementById("prezimeKupca").value = "";
          document.getElementById("datumRodjenjaKupca").value = "";
          document.getElementById("polKupca").value = "";
          document.getElementById("korisnickoImeKupca").value = "";
          document.getElementById("lozinkaKupca").value = "";
        })
        .catch((err) => {
          toastr["error"](
            "Korisnik sa navedenim korisničkim imenom već postoji. Molimo pokušajte ponovo."
          );
        });
    },
  },
});

/*$(document).ready(function() {
	$('#formaZaRegistraciju').submit(function(event) {
		event.preventDefault();
		let username = $('input[name="newUsername"]').val();
		let password = $('input[name="newPassword"]').val();
        let firstName = $('input[name="newFirstName"]').val();
        let lastName = $('input[name="newLastName"]').val();
        let dateOfBirth = $('input[name="newDateOfBirth"]').val().toString('dd.mm.yyyy.');
        let gender = $('select[name="selectionOfGender"]').val();
        
		$('#error').hide();
		$.post({
			url: 'rest/registration',
			data: JSON.stringify({
                                 username: username, 
                                 password: password,
                                 fistName: firstName,
                                 lastName : lastName,
                                 gender : gender,
                                dateOfBirth : dateOfBirth
                                
                            }),
			contentType: 'application/json',
			success: function(product) {
				$('#success').text('Korisnik je uspješno ulogovan.');
				$("#success").show().delay(3000).fadeOut();
			},
			error: function(message) {
				$('#error').text(message.responseText);
				$("#error").show().delay(3000).fadeOut();
			}
		});

        console.log(gender);
        console.log(dateOfBirth);
	});
});*/
