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

Vue.component("customer-orders", {
  data() {
    return {
      dtos: [],
      orders: [],
      message: null,
      show: true,
      statusFilter: "",
      selectedOrder: {},
      show: true,
      currentSort: "name",
      currentSortDir: "asc",
      filter: "",
      typeFilter: "",
      statusFilter: "",
      sortFilter: "",
      priceFrom: "",
      priceTo: "",
      dateFrom: "",
      dateTo: "",
    };
  },

  template: ` 
     <div id="container">
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section" v-if="dtos.length!=0">
    <h1>Pregled porudžbina</h1>

    <div class="col-lg-12" style="margin-top: 50px;">
        <!-- ----- pretraga ----- -->
        <input type="search" v-model="filter" id="pac-input" placeholder="Naziv restorana"
            style="width: 300px; margin-bottom: 10px;"> <span class="r-span2"></span>
        <span>Cijena: <input type="number" v-model="priceFrom" placeholder="od"
                style="width: 70px; margin-bottom: 10px;"> <input type="number" v-model="priceTo"
                placeholder="do" style="width: 70px; margin-bottom: 10px;"></span>
        <span class="r-span2"></span>
        <span>Datum: <input type="date" v-model="dateFrom" placeholder="od"
                style="width: 143px; margin-bottom: 10px;"> <input type="date" v-model="dateTo"
                placeholder="do" style="width: 143px; margin-bottom: 10px;"></span>
        <!-- ----- filteri ----- -->
        <select v-model="typeFilter" style="width: 160px;">
            <option value="">Tip restorana</option>
            <option value="Kineski">Kineski</option>
            <option value="Fast food">Fast food</option>
            <option value="Indijski">Indijski</option>
            <option value="Italijanski">Italijanski</option>
            <option value="Roštilj">Roštilj</option>
            <option value="Picerija">Picerija</option>
        </select>
        <span class="r-span"></span>
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
        <span class="r-span2"></span><span class="r-span2"></span><span class="r-span2"></span><span class="r-span2"></span>
        <span class="r-span2"></span><span class="r-span2"></span><span class="r-span2"></span><span class="r-span" style="margin-right: 30px;"></span>

        <!-- ----- sort ----- -->
        <select v-model="sortFilter" @change="sort()" style="width: 160px; ">
            <option value="">Sortiraj</option>
            <option value="name, asc">Naziv restorana, asc</option>
            <option value="name, desc">Naziv restorana, desc</option>
            <option value="price, asc">Cijena, asc</option>
            <option value="price, desc">Cijena, desc</option>
            <option value="date, asc">Datum, asc</option>
            <option value="date, desc">Datum, desc</option>
        </select>
        <div class="r-gap"></div>
    </div>
    <div class="r-gap"></div>

        <div>
            <div class="tbl-header">
                <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                    <thead>
                        <tr>
                            <th>Identifikator porudžbine</th>
                            <th>Kupac</th>
                            <th>Cijena</th>
                            <th>Status</th>
                            <th>Mijenjanje statusa</th>
                            <th>O porudžbini</th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="tbl-content">
                <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                        <tr v-for="(dto, index) in sortedOrders">
                        <td> {{ dto.order.id }} </td>
                        <td> {{ dto.restName }} </td>
                        <td> {{ dto.restType }} </td>
                        <td> {{ dto.order.customerID }} </td>
                        <td> {{ dto.order.dateAndTime | dateFormat('DD.MM.YYYY. HH:mm')}}</td>
                        <td> {{ dto.order.price }} </td>
                        <td v-if="dto.order.status=='PENDING'"> Obrada </td>
                        <td v-else-if="dto.order.status=='IN_PREPARATION'"> U pripremi </td>
                        <td v-else-if="dto.order.status=='AWAITING_DELIVERER'"> Čeka dostavljača </td>
                        <td v-else-if="dto.order.status=='SHIPPING'"> Nedostavljena </td>
                        <td v-else-if="dto.order.status=='DELIVERED'"> Dostavljena </td>
                        <td v-else-if="dto.order.status=='AWAITING_APPROVING'"> Čeka na odobravanje </td>
                        <td v-else> Otkazana </td>
                            <td v-if="dto.order.status=='PENDING'"><button @click="cancelOrder(index)">Otkaži porudžbinu</button></td>
                            <td v-else><span>-</span></td>
                            <td><button data-toggle="modal" data-target="#detailsModal"
                                    @click="selectOrder(dto.order)">Detaljnije</button></td>

                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="modal fade" id="detailsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document" style="width:800px;">
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
                                    <td><img v-bind:src="item.product.logo"></td>
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
    </section>
</div>
     
`,
  mounted: function () {
    axios
      .get("rest/orders/getCustomersOrders")
      .then((response) => (this.dtos = response.data));
  },

  methods: {
    sort: function () {
      console.log(this.sortFilter);
      if (this.sortFilter.includes("name")) {
        this.currentSort = "restName";
      } else if (this.sortFilter.includes("price")) {
        this.currentSort = "order.price";
      } else {
        this.currentSort = "order.dateAndTime";
      }
      this.currentSortDir = this.sortFilter.includes("desc") ? "desc" : "asc";
      console.log(this.currentSortDir);
    },

    cancelOrder: function (index) {
      axios.delete("rest/orders/cancelOrder/" + this.dtos[index].order.id).then(
        (response) => toastr["success"]("Uspješno otkazana porudžbina!"),
        //this.dtos.splice(index, 1)
        (this.dtos[index].order.status = "CANCELED")
      );
    },

    selectOrder: function (order) {
      this.selectedOrder = order;
    },
  },

  computed: {
    filteredOrders() {
      if (this.priceFrom != "" || this.priceTo != "") return this.priceFiltered;
      else if (this.filter != "") return this.nameFilteredOrders;
      else if (this.dateFrom != "" && this.dateTo != "")
        return this.dateFiltered;
      else return this.dtos;
    },

    nameFilteredOrders() {
      return this.dtos.filter((c) => {
        if (this.filter == "") return true;
        return c.restName.toLowerCase().includes(this.filter.toLowerCase());
      });
    },
    priceFiltered() {
      var from = [];
      var to = [];
      if (this.priceFrom != "") {
        from = this.dtos.filter((o) => {
          return o.order.price > this.priceFrom;
        });
      }
      from.forEach((x) => console.log(x.order.price));

      if (this.priceTo != "") {
        to = this.dtos.filter((o) => {
          return o.order.price < this.priceTo;
        });
      }

      //console.log('-------------------------------------------------')
      //to.forEach(x => console.log(x.order.price))
      //console.log('------------presjekkk------------------------')
      //from.filter(val => to.includes(val)).forEach(x => console.log(x))

      if (from.length != 0 && to.length != 0) {
        //console.log('prvi')
        from.filter((val) => to.includes(val)).forEach((x) => console.log(x));
        return from.filter((val) => to.includes(val));
      } else if (from.length != 0 && to.length == 0) {
        //console.log('drugi')

        from.forEach((x) => console.log(x));
        return from;
      } else {
        // console.log('treci')

        to.forEach((x) => console.log(x));
        return to;
      }
    },

    dateFiltered() {
      return this.dtos.filter((dto) => {
        return moment(new Date(dto.order.dateAndTime)).isBetween(
          new Date(this.dateFrom),
          new Date(this.dateTo)
        );
      });
    },

    filterType() {
      return this.filteredOrders.filter((x) => {
        if (!this.typeFilter) return true;
        return x.restType === this.typeFilter;
      });
    },
    filterStatus() {
      return this.filteredOrders.filter((x) => {
        if (!this.statusFilter) return true;
        return x.order.status === this.statusFilter;
      });
    },
    sortedOrders: function () {
      var takeUs = [];
      if (this.typeFilter != "") takeUs = this.filterType;
      else if (this.statusFilter != "") takeUs = this.filterStatus;
      else takeUs = this.filteredOrders;
      var ret = [];
      if (this.currentSort != "order.dateAndTime") {
        ret = takeUs.sort((a, b) => {
          let modifier = 1;
          if (this.currentSortDir === "desc") modifier = -1;

          if (this.currentSort === "order.price") {
            if (a.order.price < b.order.price) return -1 * modifier;
            if (a.order.price > b.order.price) return 1 * modifier;
          } else {
            if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
            if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
          }
          return 0;
        });
      } else {
        if (this.currentSortDir === "asc") {
          ret = takeUs.sort((a, b) => {
            return (
              new Date(a.order.dateAndTime).getTime() -
              new Date(b.order.dateAndTime).getTime()
            );
          });
        } else {
          ret = takeUs.sort((a, b) => {
            return (
              (new Date(a.order.dateAndTime).getTime() -
                new Date(b.order.dateAndTime).getTime()) *
              -1
            );
          });
        }
      }
      return ret;
    },
  },

  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(value);
      return parsed.format(format);
    },
  },
});
