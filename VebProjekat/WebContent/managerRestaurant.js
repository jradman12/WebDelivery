let manRest = new Vue({

	el : "#managerRestaurantPage",

	data : {
        restaurant : {}
		
	},

    mounted(){
        axios
        .get("rest/managers/getRestaurantFromLoggedManager")
        .then(response => (this.restaurant=response.data))
    }

});
