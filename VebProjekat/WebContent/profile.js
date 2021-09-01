function fixDate(user) {

	user.dateOfBirth = new Date(parseInt(user.dateOfBirth));
	
	return user;
}

var user = new Vue({ 
    el: '#profilePage',
    data: {
        loggedUser: {},
        mode : 'BROWSE'
        
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

        updateUser : function(loggedUser){
            

            axios
    		.post("rest/users/updateUser",loggedUser)
            .catch(err => {
                console.log("There has been an error! Please check this out: ");
                console.log(err);
            })


           
    		

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