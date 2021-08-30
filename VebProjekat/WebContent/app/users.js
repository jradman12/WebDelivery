function fixData(users) {
	for (var u in users) {
		u.dateOfBirth = new Date(parseInt(u.dateOfBirth));
        console.log(u.username);
	}
	return users;
}

let getUsers = new Vue({

	el : "#app",

	data : {
		users : null
	},
    mounted () {
        axios
          .get('rest/users/getAllUsers')
          .then(response => (this.users = fixData(response.data)))
         

          

    },

  filters: {
      dateFormat: function (value, format) {
          var parsed = moment(value);
          return parsed.format(format);
      }
     }
	

});