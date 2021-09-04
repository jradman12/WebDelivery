let rest = new Vue({

    el: "#xxx",

    data: {
        restaurant : {}
    },

    mounted() {
        axios
        .get("rest/restaurants/getCurrentRestaurant")
        .then(response => (this.restaurant=response.data))
    }

      // mounted() {

    //     axios.all([
    //         axios
    //             .get("rest/managers/getRestaurantFromLoggedManager"),
    //         axios
    //             .post('rest/restaurants/setCurrentRestaurant', restaurant.id)

    //     ])
    //         .then(axios.spread((data1, data2) => {
    //             this.restaurant = data1;
    //             this.message = data2;
    //         }))

    // }

});