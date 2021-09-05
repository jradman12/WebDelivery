let logouttt = new Vue({

	el : "#logoout",

	data : {
		errors : [],
		message : null 
	},

	methods: {
		logout : function(){

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

