Vue.component("customer-orders", {

     data() {
          return {
              orders : []
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
                <table  class="r-table" cellpadding="0" cellspacing="0" border="0"   data-toggle="table"  data-search="true" data-show-pagination-switch="true">
                     <thead>
                          <tr>
                                <th>Identifikator porudžbine</th>
                               <th>Kupac</th>
                               <th>Cijena</th>
                               <th>Status</th>
                               <th><button>Otkaži porudžbinu</button></th>
                               
                               
                          </tr>
                     </thead>
                </table>
           </div>
           <div class="tbl-content">
                <table class="table table-striped table-bordered" cellpadding="0" cellspacing="0" border="0"  >
                     <tbody v-for="order in orders">
                          <tr >
                          <td data-toggle="collapse" data-target="#collapsedRow1"> {{ order.id }} </td>
                               <td data-toggle="collapse" data-target="#collapsedRow1"> {{ order.customer }} </td>
                               <td data-toggle="collapse" data-target="#collapsedRow1"> {{ order.price }} </td>
                               <td v-if="order.status=='PENDING'" data-toggle="collapse" data-target="#collapsedRow1"> Obrada </td>
                               <td v-else-if="order.status=='IN_PREPARATION'" data-toggle="collapse" data-target="#collapsedRow1"> U pripremi </td>
                               <td v-else-if="order.status=='AWAITING_DELIVERER'" data-toggle="collapse" data-target="#collapsedRow1"> Čeka dostavljača </td>
                               <td v-else-if="order.status=='SHIPPING'" data-toggle="collapse" data-target="#collapsedRow1"> U transportu </td>
                               <td v-else-if="order.status=='DELIVERED'" data-toggle="collapse" data-target="#collapsedRow1"> Dostavljena </td>
                               <td v-else data-toggle="collapse" data-target="#collapsedRow1"> Otkazana </td>
                               <td v-else data-toggle="collapse" data-target="#collapsedRow1"><span>-</span></td>
                          </tr>
  
                           <tr>
                          <td colspan="12" class="hiddenRow">
                            <div class="accordion-body collapse container-fluid" id="collapsedRow1">
                              <table class="table table-striped table-bordered">
                                <thead>
                                  <tr> </th>
                                    <th>Tip</th>
                                    <th>Broj naručenih artikala</th>
                                    <th>Količina</th>
                                    <th>Opis</th>
                                  </tr>
                                </thead>
                                <tbody>
                                <tr v-for="item in order.orderedItems">
                                    <td><img v-bind:src="item.product.logo" class="rest-img"></td>
                                    <td>{{order.dateAndTime | dateFormat('DD.MM.YYYY. HH:mm')}}</td>
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
                          </td>
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
              <h1 class="cursive-font primary-color">Trenutno nema porudžbina.</h1>
              
          </div>
      </div>
      </div>
  </div>
  </section >
  </div>
      
      
  `,
  
  methods:{
     
  
  }
  
  });