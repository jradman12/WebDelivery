let manRest = new Vue({

	el : "#managerRestaurantPage",

	data : {
        restaurant : {}
		
	},

    mounted(){
        axios
        .get("rest/manager/getRestaurantFromLoggedManager")
        .then(response => (this.restaurant=response.data))
    }

});
