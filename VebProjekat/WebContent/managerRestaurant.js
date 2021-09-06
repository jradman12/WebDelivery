let manRest = new Vue({

	el : "#managerRestaurantPage",

	data : {
        restaurant : {},
		products : []
	},

    mounted(){
        axios.all([
            axios
            .get("rest/managers/getRestaurantFromLoggedManager"),

            axios
            .get("rest/products/getRestaurantMenu")
        ])
            .then(axios.spread((data1, data2) => {
                 this.restaurant = data1;
                 this.products = data2;
            }))
    }

});
