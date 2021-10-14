Vue.component("manager-orders", {

    data() {
        return {
            orders : [],
            selectedOrder : {},
            currentSort: 'dateAndTime',
               currentSortDir: 'asc',
               statusFilter: '',
               sortFilter: '',
               priceFrom: '',
               priceTo: '',
               dateFrom: '',
               dateTo: ''
        }
    },
	
	template: ` 
    <div id="container">
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section" v-if="orders.length!=0">
    <h1>Pregled porudžbina</h1>

    <div class="col-lg-12" style="margin-top: 50px;">
                    <!-- ----- pretraga ----- -->
                    <span>Cijena: <input type="number" v-model="priceFrom" placeholder="od"
                            style="width: 70px; margin-bottom: 10px;"> <input type="number" v-model="priceTo"
                            placeholder="do" style="width: 70px; margin-bottom: 10px;"></span>
                    <span class="r-span"></span>
                    <span>Datum: <input type="date" v-model="dateFrom" placeholder="od"
                            style="width: 143px; margin-bottom: 10px;"> <input type="date" v-model="dateTo"
                            placeholder="do" style="width: 143px; margin-bottom: 10px;"></span>
                    <!-- ----- filteri ----- -->
                    <span class="r-span2"></span>
                    <select v-model="statusFilter" style="width: 160px;">
                        <option value="">Status</option>
                        <option value="PENDING">Obrada</option>
                        <option value="IN_PREPARATION">U pripremi</option>
                        <option value="AWAITING_DELIVERER">Čeka dostavljača </option>
                        <option value="AWAITING_APPROVING">Čeka odobrenje </option>
                        <option value="SHIPPING">U transportu </option>
                        <option value="DELIVERED">Dostavljena </option>
                        <option value="CANCELED">Otkazana </option>
                    </select>
                    <span class="r-span"></span>

                    <!-- ----- sort ----- -->
                    <select v-model="sortFilter" @change="sort()" style="width: 160px; ">
                        <option value="">Sortiraj</option>
                        <option value="price, asc">Cijena, asc</option>
                        <option value="price, desc">Cijena, desc</option>
                        <option value="date, asc">Datum, asc</option>
                        <option value="date, desc">Datum, desc</option>
                    </select>
                    <div class="r-gap"></div>
                </div>
    <div class="r-gap"></div>

    <div id="coms">
         <div class="tbl-header" style="margin-top:50px">
              <table  class="r-table" cellpadding="0" cellspacing="0" border="0"   data-toggle="table"  data-search="true" data-show-pagination-switch="true">
                   <thead>
                        <tr>
                        <th>Identifikator porudžbine</th>
                        <th>Kupac</th>
                        <th>Datum</th>
                        <th>Cijena</th>
                        <th>Status</th>
                             <th>Promijeni status</th>
                             <th></th>
                             
                             
                        </tr>
                   </thead>
              </table>
         </div>
         <div class="tbl-content">
              <table class="r-table" cellpadding="0" cellspacing="0" border="0"  >
                   <tbody v-for="order in sortedOrders">
                        <tr >
                        <td > {{ order.id }} </td>
                             <td > {{ order.customerID }} </td>
                             <td > {{ order.dateAndTime | dateFormat('DD.MM.YYYY. HH:mm')  }} </td>
                             <td > {{ order.price }} </td>
                             <td v-if="order.status=='PENDING'" > Obrada </td>
                             <td v-else-if="order.status=='IN_PREPARATION'" > U pripremi </td>
                             <td v-else-if="order.status=='AWAITING_DELIVERER'" > Čeka dostavljača </td>
                             <td v-else-if="order.status=='SHIPPING'" > U transportu </td>
                             <td v-else-if="order.status=='DELIVERED'" > Dostavljena </td>
                             <td v-else-if="order.status=='AWAITING_APPROVING'" > Čeka na odobravanje </td>
                             <td v-else > Otkazana </td>
                             <td v-if="order.status=='PENDING'"><button @click="changeStatusInPreparation(order)">U pripremi</button></td>
                             <td v-else-if="order.status=='IN_PREPARATION'"><button @click="changeStatusInAwaitingDeliverer(order)">Čeka dostavljača</button></td>
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
   .get('rest/orders/getAllOrdersForRestaurant')
   .then(response => (this.orders = response.data))
},

methods:{
     sort: function () {
          console.log(this.sortFilter);
           if (this.sortFilter.includes("price")) {
              this.currentSort = 'price';
          } else {
              this.currentSort = 'dateAndTime';
          }
          this.currentSortDir = this.sortFilter.includes("desc") ? 'desc' : 'asc';
          console.log(this.currentSortDir);
      },

     changeStatusInPreparation : function(order){

          axios
          .put('rest/orders/updateOrderStatusIP/' + order.id)
          .then( response => {
               this.orders=[];
               response.data.forEach(x => {
                    this.orders.push(x);
               })
                    
               });
          

          },

     changeStatusInAwaitingDeliverer : function(order){

               axios
               .put('rest/orders/updateOrderStatusAD/' + order.id)
               .then( response => {
                    this.orders=[];
                    response.data.forEach(x => {
                         this.orders.push(x);
                    })
                         
                    });
               
     
               },

     selectOrder : function(order){
          this.selectedOrder=order;

     }


},

computed: {
     filteredOrders() {
          if (this.priceFrom != '' || this.priceTo != '') {console.log('radimo prajs'); return this.priceFiltered;}
          else if(this.dateFrom != '' && this.dateTo != '') { console.log('radimo dejt');  return this.dateFiltered;} 
          else { console.log('ipak samo orders' ); return this.orders };
     },

    
     priceFiltered() {
          var from = []
          var to = []
          if (this.priceFrom != '') {
               from = this.orders.filter(o => {
                    return (o.price > this.priceFrom)
               })
          }

          if (this.priceTo != '') {
               to = this.orders.filter(o => { return (o.price < this.priceTo) })
          }

          //console.log('-------------------------------------------------')
          //to.forEach(x => console.log(x.order.price))
          //console.log('------------presjekkk------------------------')
          //from.filter(val => to.includes(val)).forEach(x => console.log(x))

          if (from.length != 0 && to.length != 0) {
               //console.log('prvi')
               from.filter(val => to.includes(val)).forEach(x => console.log(x))
               return from.filter(val => to.includes(val));
          }
          else if (from.length != 0 && to.length == 0) {
               //console.log('drugi')

               from.forEach(x => console.log(x));
               return from;
          }
          else {
               // console.log('treci')

               to.forEach(x => console.log(x));
               return to;
          }

     },

     dateFiltered() {
       return this.orders.filter(a => {
           return moment(new Date(a.dateAndTime)).isBetween(new Date(this.dateFrom), new Date(this.dateTo))
       });
   },

   
     filterStatus() {
               return this.filteredOrders.filter(x => {
                    if (!this.statusFilter) return true;
                    return (x.status === this.statusFilter);
               })

     },
     sortedOrders: function () {
       var takeUs = [];
       if (this.statusFilter != '') { console.log('statussss ' ); takeUs = this.filterStatus;}
       else { console.log('ipak cu uzet filtered '); takeUs = this.filteredOrders;}
       var ret =[];
       if(this.currentSort != 'dateAndTime'){
            console.log('sort prajssssssssssss ')
            ret = takeUs.sort((a, b) => {
               let modifier = 1;
               if (this.currentSortDir === 'desc') modifier = -1;
               if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
               if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                return 0;
           }
       )} else {
          console.log('taj sam sort (date, initial) ')

           if (this.currentSortDir === 'asc'){
                
               ret = takeUs.sort((a,b) => {
                   return new Date(a.dateAndTime).getTime() - new Date(b.dateAndTime).getTime();
               })
           }else {
               ret = takeUs.sort((a,b) => {
                   return (new Date(a.dateAndTime).getTime() - new Date(b.dateAndTime).getTime()) * (-1);
               })
           }

       } return ret;
   }
},

     filters: {
          dateFormat: function (value, format) {
              var parsed = moment(value);
              return parsed.format(format);
          }

     }
         



});