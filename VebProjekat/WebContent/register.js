$(document).ready(function() {
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
});