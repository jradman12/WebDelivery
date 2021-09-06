function fixData(users) {
	for (var u in users) {
		u.dateOfBirth = new Date(parseInt(u.dateOfBirth));
        console.log(u.username);
	}
	return users;
}

let getUsers = new Vue({

	el : "#dash",

	data : {
        loggedInUser : {}
	},

    created : function() {
         axios
        .get('rest/users/getLoggedUser')
        .then(response => (this.loggedInUser = response.data))
    }

});