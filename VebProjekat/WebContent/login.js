let login = new Vue({

	el : "#log",

	data : {
		user: {},
		errors : [],
		message : null 
	},

	methods: {
		checkLogin : function(event){
			console.log("Tryna login, bitch");
			event.preventDefault();

			axios
				.post('rest/login', {
					"username" : this.user.username,
					"password" : this.user.password
				})
				.then(response => {
					this.message = response.data;
					window.location.assign(response.data)
				})
				.catch(err => {
					console.log("There has been an error! Please check this out: ");
					console.log(err);
				})
				return true;

				
		}
		
	},

});

// $(document).ready(function() {
// 	$('#forma').submit(function(event) {
// 		event.preventDefault();
// 		let username = $('input[name="username"]').val();
// 		let password = $('input[name="password"]').val();
// 		$('#error').hide();
// 		$.post({
// 			url: 'rest/login',
// 			data: JSON.stringify({username: username, password: password}),
// 			contentType: 'application/json',
// 			success: function(product) {
// 				$('#success').text('Korisnik je uspješno ulogovan.');
// 				$("#success").show().delay(3000).fadeOut();
// 			},
// 			error: function(message) {
// 				$('#error').text(message.responseText);
// 				$("#error").show().delay(3000).fadeOut();
// 			}
// 		});
// 	});
// });