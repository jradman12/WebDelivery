toastr.options = {
    closeButton: false,
    debug: false,
    newestOnTop: false,
    progressBar: false,
    positionClass: "toast-top-right",
    preventDuplicates: true,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "5000",
    extendedTimeOut: "1000",
    showEasing: "linear",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
  };

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
                toastr["success"]("Proizvod uspješno izmijenjen!");
                                setTimeout(function(){ location.href = "managerDashboard.html" }, 1500); }            )
            

            

        }
    }

});