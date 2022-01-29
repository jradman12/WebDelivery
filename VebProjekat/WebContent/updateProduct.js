var updateArticle = new Vue({ 
    el: '#updateProduct',
    data: {
        product: {},
        restID : '',
        message : ''
    },
    mounted () {
        axios
          .get('rest/products/getCurrentProduct')
          .then(response => (
            this.product = response.data.product, 
            this.restID = response.data.restaurantID))
          
    },
    methods :{
        updateProduct(event){
            event.preventDefault();
            console.log('here i am, praying')
            axios
            .put('rest/restaurants/updateProduct/' + this.restID, this.product)
            .then(response => {this.message = response.data; 
                                alert(this.message);
                                setTimeout(function(){ location.href = "managerDashboard.html" }, 1500); }            )
            

            

        }
    }

});