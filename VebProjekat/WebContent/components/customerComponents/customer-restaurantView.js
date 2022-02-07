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

Vue.component("customer-restaurantView", {
  data() {
    return {
      restaurant: [],
      comments: [],
      productsDTO: [],
      show: false,
      newComment: {},
      message: null,
    };
  },

  template: ` 
    <div id="dying">

            <img src="images/ce3232.png" width="100%" height="90px">

            <section class="r-section">
                <div class="recent-listing">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="item">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="listing-item">
                                                <div class="left-image">
                                                    <a><img v-bind:src="restaurant.logo" class="rest-img" alt=""></a>
                                                </div>
                                                <div class="right-content align-self-center">
                                                    <a>
                                                        <h4>{{restaurant.name}}</h4>
                                                    </a>
                                                    <h6>{{restaurant.status == 'OPEN' ? 'Otvoren' : 'Zatvoren'}}</h6>
                                                    <p>{{restaurant.averageRating}}</p>
                                                    <span>{{ (restaurant.location).address.addressName }}</span>
                                                    <span>, </span>
                                                    <span>{{ (restaurant.location).address.city }}</span>
                                                    <span>, </span>
                                                    <span>{{ (restaurant.location).address.postalCode }}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                        </div>
                        <div id="map" ref="mapElement" style="width:80%;height:500px; border-style: inset; border-color:#ce32322d;">
                        </div>
                        <section>
                        <div class="gtco-section">
                            <div class="gtco-container">
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
                                        <h1 class="cursive-font primary-color">JELOVNIK</h1>
                                        <p>Uživajte!</p>
                                    </div>
                                </div>

                                
                                <div class="row" v-if="productsDTO.length!=0">

                                    <div class="col-lg-4 col-md-4 col-sm-6" v-for="(productDTO,index) in productsDTO">
                                        <a v-bind:href="productDTO.product.logo" class="fh5co-card-item image-popup">
                                        <figure>
                                            <div class="overlay"><i class="ti-plus"></i></div>
                                            <img v-bind:src="productDTO.product.logo" alt="Image" class="img-responsive">
                                        </figure>
                                        <div class="fh5co-text">
                                            <h2>{{productDTO.product.name}}</h2>
                                            <p>{{productDTO.product.description}}</p>
                                            <p><span class="price cursive-font">{{productDTO.product.price}} RSD</span></p>
                                            <p>
                                                <input type="number" style="height: 30px; width: 20%; text-align: center;" step="1" :value="productDTO.amount"
                                                @input="updateQuantity(index, $event)"
                                                        @blur="checkQuantity(index, $event)" />
                                                <button style="margin-top:7px; color: #7a1a1a;" @click="addToCart(index)">Dodaj u korpu</button>
                                            </p>
                                        </div>
                                        </a>
                                    </div>
                                </div>

                                <div class="row" v-else>

                                    <h3>Jelovnik je trenutno prazan. </h3>
                                </div>
                            </div>
                        </div>
                    </section> 

                        <section style="width: 80%; text-align: center;">
                            <div class="row">
                                <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
                                    <h1 class="cursive-font primary-color">Recenzije restorana</h1>
                                    <br>
                                    <p v-if="show">Zadovoljni uslugama restorana?<a href="#writeCommentModal" data-toggle="modal" >Ostavite recenziju</a> i pomozite drugima!</p>
                                </div>
                            </div>
                            <div v-if="comments.length!=0">
                            <div class="tbl-header">
                                <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                                    <thead>
                                        <tr>
                                            <th>Ocjena</th>
                                            <th>Komentar</th>
                                            <th>Autor</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>

                            <div class="tbl-content">
                                <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                                    <tbody>
                                        <tr v-for="comment in comments">
                                        <td>{{ comment.rating }}</td>
                                        <td>{{ comment.text }}</td>
                                        <td>{{ comment.author }}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            </div>
                            <div v-else>
                                <h3>Trenutno nema komentara. </h3>
                            </div>
                        </section>

                    </div>
                </div>

            </section>


            <div class="modal fade" id="writeCommentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                 <div class="modal-content">
                      <div class="modal-header border-bottom-0">
                           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                           </button>
                      </div>
                      <div class="modal-body">
                           <div class="form-title text-center">
                                <h4>Ocjenjivanje usluga restorana</h4>
                           </div>
                           <div id='burn' class="d-flex flex-column text-center">
                                <form>
                                    
                                     <div class="form-group">

                                          <select id="selectRating" required name="selectionOfGender" v-model="newComment.rating" class="form-control">

                                               
                                               <option value="1">1</option>
                                               <option value="2">2</option>
                                               <option value="3">3</option>
                                               <option value="4">4</option>
                                               <option value="5">5</option>
                                          </select>
                                     </div>
  
                                     <div class="form-group">

                                          <textarea id="textOfComment" required type="text" class="form-control" v-model="newComment.text"

                                               placeholder="Komentar"></textarea>
                                     </div>
                                     <button type="submit" @click="addNewComment($event)"
                                          class="btn btn-info btn-block btn-round">Potvrda</button>
                                </form>
  
    
                           </div>
                      </div>
                 </div>
            </div>
       </div>
       
  
        </div>
`,

  mounted: function () {
    // var mymap = L.map(this.$refs["mapElement"]).setView(
    //   [46.392411189814645, 16.270751953125004],
    //   6
    // );
    // leaflet wont inject since there is a v-if thats fetching data async so nooooope do sth else
  },
  created() {
    axios.get("rest/restaurants/getCurrentRestaurant").then(
      (response) => (this.restaurant = response.data),
      axios
        .get("rest/restaurants/getProductsOfCurrentRestaurant")
        .then((response) => (this.productsDTO = response.data))
    );

    axios
      .get("rest/comments/getCommentsForRestaurant")
      .then((response) => (this.comments = response.data));

    axios
      .get("rest/orders/orderFromRestaurantDeliveredToCustomer")
      .then((response) => (this.show = response.data));
  },

  updated() {
    var center = [19.84, 45.24];

    this.$nextTick(function () {
      mapboxgl.accessToken =
        "pk.eyJ1Ijoia2p1YmlpIiwiYSI6ImNremQzcHJ0azA1MHoydm1xcTc2d2hhcHAifQ.tqj6PT5zCeut-6puPQ9ELA";
      var map = new mapboxgl.Map({
        container: "map",
        style: "mapbox://styles/mapbox/streets-v11",
        center: center,
        zoom: 12,
      });

      const nav = new mapboxgl.NavigationControl();
      map.addControl(nav);

      // forward geocoding
      var location =
        this.restaurant.location.address.addressName +
        this.restaurant.location.address.city +
        this.restaurant.location.address.postalCode;

      location = encodeURIComponent(location);

      axios
        .get(
          "https://api.mapbox.com/geocoding/v5/mapbox.places/" +
            location +
            ".json?types=place%2Cpostcode%2Caddress&access_token=pk.eyJ1Ijoia2p1YmlpIiwiYSI6ImNremQzcHJ0azA1MHoydm1xcTc2d2hhcHAifQ.tqj6PT5zCeut-6puPQ9ELA"
        )
        .then((res) => {
          const marker = new mapboxgl.Marker() // Initialize a new marker
            .setLngLat(res.data.features[1].center) // Marker [lng, lat] coordinates
            .addTo(map);
          console.log("coords are " + res.data.features[0].center);
        })
        .catch((err) => console.log(err));
    });
  },

  methods: {
    updateQuantity: function (index, event) {
      //var product = this.productsDTO[index].product;
      var value = event.target.value;
      var valueInt = parseInt(value);
      console.log("im in updateQ");
      // Minimum quantity is 1, maximum quantity is 100, can left blank to input easily
      if (value === "") {
        this.productsDTO[index].amount = value;
        console.log(
          "value empty, i set amount to " +
            value +
            ", so its " +
            this.productsDTO[index].amount
        );
      } else if (valueInt > 0 && valueInt < 100) {
        this.productsDTO[index].amount = valueInt;
        console.log(
          "value int, i set amount to " +
            valueInt +
            ", so its " +
            this.productsDTO[index].amount
        );
      }

      //this.products[index].product = product;
      //this.$set(this.products, index, product);
    },
    checkQuantity: function (index, event) {
      // Update amount to 1 if it is empty
      console.log("in checkQ");
      if (event.target.value === "" || event.target.value === 0) {
        //var product = this.restaurant.menu[index];
        this.productsDTO[index].amount = 1;
        console.log(
          " i set it to " + this.productsDTO[index].amount + "(should be 1)"
        );
        // this.$set(this.products, index, product);
      }
    },
    addToCart(index) {
      axios
        .post("rest/cart/addCartItem", {
          product: this.productsDTO[index].product,
          amount: this.productsDTO[index].amount,
        })
        .then((response) =>
          toastr["success"](
            "Proizvod " +
              response.data.product.name +
              " uspješno dodat u korpu!"
          )
        );
    },

    addNewComment: function (event) {
      event.preventDefault();
      axios
        .post("rest/comments/addComment", {
          rating: this.newComment.rating,
          text: this.newComment.text,
        })
        .then((response) => {
          toastr["success"]("Uspješno dodat komentar.");
          $("#writeCommentModal").modal("hide");
          document.getElementById("selectRating").value = "";
          document.getElementById("textOfComment").value = "";
        });
    },
  },
});
