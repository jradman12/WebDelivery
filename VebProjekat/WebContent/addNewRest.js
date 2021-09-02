let mans = new Vue({

	el : "#mans",

	data : {
		availableManagers : []
	},

     mounted () 
         {
             axios
             .get('rest/restaurants/getAllAvailableManagers')
             .then(response => (this.availableManagers=response.data))
         }

});