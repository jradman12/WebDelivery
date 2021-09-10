let logouttt = new Vue({

	el : "#logout",

	data : {
		errors : [],
		message : null 
	},

	methods: {
		logOut : function(event){
				event.preventDefault();
				console.log('in logout')
				
			axios
				.get('rest/ls/logout')
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

