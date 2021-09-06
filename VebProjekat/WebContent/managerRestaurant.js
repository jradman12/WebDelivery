let manRest = new Vue({

	el : "#managerRestaurantPage",

	data : {
        restaurant : {},
		message : ''
	},

	methods :{

		setProduct(product){
			console.log('tryna set it')
			axios
			.post('rest/products/setCurrentProduct', product)
			.then(response => (this.message = (response.data), alert(message)))
			
		setTimeout(function(){ location.href = "updateArticle.html" }, 3000);
	}


	}


});
