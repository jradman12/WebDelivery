Vue.component("home-deliverer", {

    data() {
        return {
            loggedInUser : {}
        }
    },

	
	template: ` 
    	<div>
         <img src="images/ce3232.png" width="100%" height="90px">
         <section data-stellar-background-ratio="0.5">
         <div class="container" >
              <div class="row">
     
                   <div class="col-md-8 col-ms-12">
                        
                             <h1>Dobrodošli {{loggedInUser.fistName}}! </h1>
                             
                        
                   </div>  
              </div>
         </div>
     </section>    
     </div>
`,

mounted : function() {
    axios
   .get('rest/users/getLoggedUser')
   .then(response => (this.loggedInUser = response.data))
}
});