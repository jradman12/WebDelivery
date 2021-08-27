let reg = new Vue({
	
	el: "#burn",
	data: {
		 newUser: {},
         errors: [],
         message: null,
         rePassword: ''
	},
	methods: {
		checkRegistration: function (event) {
            event.preventDefault();
			
			console.log(this.newUser.firstName)
			
            this.errors = [];

            if (!this.errors.length) {
                axios
                    .post('rest/registerService/registration', {
                   
                                 "fistName": this.newUser.firstName,
                                 "lastName" : this.newUser.lastName,
                                 "dateOfBirth" : this.newUser.dateOfBirth,
								 "gender" : this.newUser.gender,
								 "username": this.newUser.username, 
                                 "password" : this.newUser.password
                    })
                    .then(response => {
                        this.message = response.data;
                        console.log("\n\n ------- PODACI -------\n");
                        console.log(response.data);
                        //toastr["success"]("Let's go, Log in !!", "Success registration!");
                        console.log("\n\n ----------------------\n\n");
                                              location.href = response.data; // we get from backend redirection to login with this
                    })
                    .catch(err => {
                        console.log("\n\n ------- ERROR -------\n");
                        console.log(err);
                 //       toastr["error"]("We have alredy user with same username, try another one", "Fail");
                        console.log("\n\n ----------------------\n\n");
                    })
                return true;
            }
			this.errors.forEach(element => {
                console.log(element)
               // toastr["error"](element, "Fail")
            });
        }

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
			url: 'rest/registerService/registration',
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