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
			
			console.log(this.newUser.fistName)
			
            this.errors = [];
            
            if (!this.errors.length) {
                axios
                    .post('rest/registerService/registration', {
                   
                                 "fistName": this.newUser.fistName,
                                 "lastName" : this.newUser.lastName,
                                 "dateOfBirth" : this.newUser.dateOfBirth,
								 "gender" : this.newUser.gender,
								 "username": this.newUser.username, 
                                 "password" : this.newUser.password
                    })
                    .then(response => {
                        this.message = response.data;
                        document.getElementById('imeKupca').value='';
                        document.getElementById('prezimeKupca').value='';
                        document.getElementById('datumRodjenjaKupca').value='';
                        document.getElementById('polKupca').value='';
                        document.getElementById('korisnickoImeKupca').value='';
                        document.getElementById('lozinkaKupca').value='';
                    })
                    .catch(err => {
                        this.message = response.data;
                        document.getElementById('imeKupca').value='';
                        document.getElementById('prezimeKupca').value='';
                        document.getElementById('datumRodjenjaKupca').value='';
                        document.getElementById('polKupca').value='';
                        document.getElementById('korisnickoImeKupca').value='';
                        document.getElementById('lozinkaKupca').value='';
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