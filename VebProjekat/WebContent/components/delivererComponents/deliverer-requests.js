Vue.component("deliverer-requests", {

    data() {
        return {
            requests : []
        }
    },
	
	template: ` 
    <div id="container">
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section" v-if="requests.length!=0">
    <h1></h1>

    <div class="r-gap"></div>

    <div id="coms">
         <div class="tbl-header">
              <table  class="r-table" cellpadding="0" cellspacing="0" border="0"  >
                   <thead>
                        <tr>
                              <th>Identifikator porudžbine</th>
                             <th>Restoran</th>
                             <th>Status</th>
                            
                            
                             
                             
                        </tr>
                   </thead>
              </table>
         </div>
         <div class="tbl-content">
              <table class="r-table" cellpadding="0" cellspacing="0" border="0"  >
                   <tbody v-for="request in requests">
                        <tr >
                        <td>{{request.orderID}}</td>
                        
                        <td>{{request.restaurantID}}</td>
                        <td v-if="request.status=='WAITING'">Čeka na odobravanje </td>
                        <td v-else-if="request.status=='APPROVED'">Odobren </td>
                        <td v-else>Odbijen </td>
                        </tr>
                         
                   </tbody>
              </table>
         </div>
    </div>


    <div class="r-gap"></div>

</section>
<section class="r-section" v-else>
<div class="gtco-section">
<div class="gtco-container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
            <h1 class="cursive-font primary-color">Trenutno nema zahtjeva.</h1>
            
        </div>
    </div>
    </div>
</div>
</section >
</div>
    
    
`,
mounted : function() {
    axios
   .get('rest/requests/getAllRequestsForDeliverer')
   .then(response => (this.requests = response.data))
}

});