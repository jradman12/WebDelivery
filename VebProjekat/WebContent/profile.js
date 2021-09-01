function fixDate(user) {

	user.dateOfBirth = new Date(parseInt(user.dateOfBirth));
	
	return user;
}

var user = new Vue({ 
    el: '#profilePage',
    data: {
        loggedUser: {},
        mode : 'BROWSE',
        message : null
        
    },
    mounted () {
        axios
          .get('rest/users/getLoggedUser')
          .then(response => (this.loggedUser = fixDate(response.data)))
          
    },
    methods : {
        changeMode: function(e) {
                e.preventDefault()
                this.mode='EDIT'
        },

        updateUser : function(e){
            
            e.preventDefault()
            axios
    		.post("rest/users/updateUser", 
                {"fistName": this.loggedUser.fistName,
                "lastName" : this.loggedUser.lastName,
                "dateOfBirth" : this.loggedUser.dateOfBirth,
                "gender" : this.loggedUser.gender,
                "username": this.loggedUser.username, 
                "password" : this.loggedUser.password,
                "role" : this.loggedUser.role})
            .then(response => {
                this.message = response.data;
            })
            .catch(err => {
                console.log("There has been an error! Please check this out: ");
                console.log(err);
            })
            return true;
        }

          

           
    		

        
    },
    components: {
      	vuejsDatepicker
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	}
});