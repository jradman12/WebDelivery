Vue.component("customer-restaurantView", {

    data() {
        return {
            restaurant: [],
            comments: [],
            productsDTO : []
        }
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
                            <row></row>
                        </div>
                        <div class="listing-item">
                            <div id="map" style="width:80%;height:500px; border-style: inset; border-color:#ce32322d;">
                            </div>
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

                                <div class="row" v-if="productsDTO==null">

                                    <h3>Jelovnik je trenutno prazan! </h3>
                                </div>
                                <div class="row" v-else>

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
                            </div>
                        </div>
                    </section> 

                        <section style="width: 80%; text-align: center;">
                            <div class="row">
                                <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
                                    <h1 class="cursive-font primary-color">Recenzije restorana</h1>
                                    <br>
                                    <p>Zadovoljni uslugama restorana? <a href="#">Ostavite recenziju</a> i pomozite drugima!</p>
                                </div>
                            </div>
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
                                        // <tr v-for="comment in comments">
                                        //     <td>{{ comment.rating }}</td>
                                        //     <td>{{ comment.text }}</td>
                                        //     <td>{{ comment.author }}</td>
                                        // </tr>
                                    </tbody>
                                </table>
                            </div>
                        </section>

                    </div>
                </div>

            </section>
        </div>
`,
    created() {
        axios
        .get("rest/restaurants/getCurrentRestaurant")
        .then(response => (this.restaurant = response.data), 
			axios
        .get("rest/restaurants/getProductsOfCurrentRestaurant")
        .then(response => (this.productsDTO = response.data))
	)

       
    },

    methods: {
        updateQuantity: function(index, event) {
          //var product = this.productsDTO[index].product;
          var value = event.target.value;
          var valueInt = parseInt(value);
    	console.log('im in updateQ')
         // Minimum quantity is 1, maximum quantity is 100, can left blank to input easily
         if (value === "") {
           this.productsDTO[index].amount = value;
           console.log('value empty, i set amount to ' + value + ", so its " + this.productsDTO[index].amount)
        } else if (valueInt > 0 && valueInt < 100) {
           this.productsDTO[index].amount = valueInt;
           console.log('value int, i set amount to ' + valueInt + ", so its " + this.productsDTO[index].amount)

        }
          
    	//this.products[index].product = product;
          //this.$set(this.products, index, product);
        },
        checkQuantity: function(index, event) {
          // Update amount to 1 if it is empty
          console.log('in checkQ')
          if (event.target.value === "" || event.target.value === 0) {
            //var product = this.restaurant.menu[index];
             this.productsDTO[index].amount = 1;
             console.log(' i set it to ' + this.productsDTO[index].amount + '(should be 1)')
           // this.$set(this.products, index, product);
          }
        },
        addToCart(index){
            axios
            .post('rest/cart/addCartItem', {
                "product" : this.productsDTO[index].product,
                "amount" : this.productsDTO[index].amount
            })
            .then(response => (alert(response.data.product.name + ' successfully added to cart!')))
        }
    }



});