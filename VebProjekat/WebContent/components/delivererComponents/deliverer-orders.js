Vue.component("deliverer-orders", {

    data() {
        return {
            orders : [],
            message: null,
            selectedOrder : {}
        }
    },
	
	template: ` 
    <div id="container">
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section" v-if="orders.length!=0">
    <h1></h1>

    <div class="r-gap"></div>

    <div id="coms">
         <div class="tbl-header">
              <table  class="r-table" cellpadding="0" cellspacing="0" border="0">
                   <thead>
                        <tr>
                              <th>Identifikator porudžbine</th>
                             <th>Kupac</th>
                             <th>Cijena</th>
                             <th>Status</th>
                             <th></th>
                             <th></th>
                             
                             
                        </tr>
                   </thead>
              </table>
         </div>
         <div class="tbl-content">
              <table class="r-table" cellpadding="0" cellspacing="0" border="0"  >
                   <tbody>
                        <tr v-for="order in orders"  >
                        <td > {{ order.id }} </td>
                             <td > {{ order.customer }} </td>
                             <td > {{ order.price }} </td>
                             <td v-if="order.status=='PENDING'" > Obrada </td>
                             <td v-else-if="order.status=='IN_PREPARATION'" > U pripremi </td>
                             <td v-else-if="order.status=='AWAITING_DELIVERER'" > Čeka dostavljača </td>
                             <td v-else-if="order.status=='SHIPPING'" > Nedostavljena </td>
                             <td v-else-if="order.status=='DELIVERED'" > Dostavljena </td>
                             <td v-else> Otkazana </td>
                             <td v-if="order.status=='SHIPPING'"><button  @click = "deliverOrder(order)">Dostavljena</button></td>
                             <td v-else ><span>-</span></td>
                             <td><button  data-toggle="modal"
                             data-target="#detailsModal" @click="selectOrder(order)">Detaljnije</button></td>

                        </tr>

                        
                   </tbody>
              </table>
         </div>

         <div class="modal fade"  id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
         <div class="modal-dialog modal-dialog-centered" role="document"  style="width:800px;">
              <div class="modal-content">
                   <div class="modal-header border-bottom-0">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                             <span aria-hidden="true">&times;</span>
                        </button>
                   </div>
                   <div class="modal-body">
                   <table class="r-table">
                   <thead>
                     <tr>
                        <th>Slika artikla</th>
                        <th>Datum i vrijeme</th>
                       <th>Ime artikla</th>
                       <th>Cijena artikla</th>
                       <th>Tip</th>
                       <th>Broj naručenih artikala</th>
                       <th>Količina</th>
                       <th>Opis</th>
                     </tr>
                   </thead>
                   <tbody>
                   <tr v-for="item in selectedOrder.orderedItems">
                       <td><img v-bind:src="item.product.logo" ></td>
                       <td>{{selectedOrder.dateAndTime | dateFormat('DD.MM.YYYY. HH:mm')}}</td>
                       <td>{{item.product.name}}</td>
                       <td>{{item.product.price}}</td>
                       <td v-if="item.product.type=='FOOD'">Hrana</td>
                       <td v-else>Piće</td>
                       <td>{{item.amount}}</td>
                       <td v-if="item.product.type=='FOOD'">{{item.product.quantity}}g</td>
                       <td v-else>{{item.product.quantity}}ml</td>
                       <td>{{item.product.description}}</td>
                     </tr>
                   </tbody>
                 </table>
                   </div>
              </div>
         </div>
    </div>
    </div>


    <div class="r-gap"></div>

</section>
<section class="r-section" v-else>
<div class="gtco-section">
<div class="gtco-container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
            <h1 class="cursive-font primary-color">Trenutno nema porudžbina.</h1>
            
        </div>
    </div>
    </div>
</div>
</section >
</div>
    
    
`,
mounted : function() {
    axios
   .get('rest/orders/getAllApprovedOrders')
   .then(response => (this.orders = response.data))
},

methods:{
     deliverOrder : function(order){

          axios
          .put('rest/orders/deliverOrder/' + order.id)
          .then( response => {
            this.orders=[];
            response.data.forEach(x => {
                 this.orders.push(x);
            })
                 
            });

          },
    selectOrder : function(order) {
    			this.selectedOrder = order;
                   console.log("Tu smo " + order.id);
    		  
    	}


},
     filters: {
          dateFormat: function (value, format) {
              var parsed = moment(value);
              return parsed.format(format);
          }

     }
         



});