let getUsers = new Vue({

	el : ".r-section",

	data : {
		users : null
	},
    mounted () {
        axios
          .get('rest/users/getAllUsers')
          .then(response => (this.users = response.data))
         

          

    }
	

});