let newUser = new Vue({

	el : "#newUser",

	data : {
		newUser: {},
		errors : [],
		message : null 
	},

	methods: {
		addNewUser : function(event){
            event.preventDefault();

            if(this.newUser.type === "MANAGER"){
                axios
                .post('rest/managers/registration', {
               
                             "fistName": this.newUser.fistName,
                             "lastName" : this.newUser.lastName,
                             "dateOfBirth" : this.newUser.dateOfBirth,
                             "gender" : this.newUser.gender,
                             "username": this.newUser.username, 
                             "password" : this.newUser.password,
                             "restaurantID" : null,
                             "role" : "MANAGER"
                })
                .then(response => {
                    this.message = response.data;
                    alert("Uspješno registrovan novi menadžer!");
                    window.location.assign(response.data)
                })
                .catch(err => {
                    console.log("There has been an error! Please check this out: ");
                    console.log(err);
                })
            } else if(this.newUser.type === "DELIVERER") {
                axios
                .post('rest/deliverers/registration', {
               
                             "fistName": this.newUser.fistName,
                             "lastName" : this.newUser.lastName,
                             "dateOfBirth" : this.newUser.dateOfBirth,
                             "gender" : this.newUser.gender,
                             "username": this.newUser.username, 
                             "password" : this.newUser.password,
                             "role" : "DELIVERER"
                })
                .then(response => {
                    this.message = response.data;
                    alert("Uspješno registrovan novi dostavljač!");
                    window.location.assign(response.data)
                })
                .catch(err => {
                    console.log("There has been an error! Please check this out: ");
                    console.log(err);
                })
            }
				
		}
		
	},

});