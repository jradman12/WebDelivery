Vue.component("customer-cart", {

  data() {
    return {
      cartItems: [],
      price : 0, 
      customerID : '',
      customerType : {},
      promotions: [
        {
          code: "GOLD",
          discount: "5%"
        },
        {
          code: "SILVER",
          discount: "3%"
        },
        {
          code: "PLATINUM",
          discount: "2%"
        }
      ],
      promoCode: ""
    }
  },
  template: ` 
    <div>
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section">
        <div class="recent-listing">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-heading">
                            <h1>Moja korpa</h1>
                            <br><span style="float: right; margin-right: 10px; margin-bottom: 2px;">{{ itemCount
                                }} proizvoda u korpi</span>

                        </div>
                    </div>

                    <div class="col-lg-12">

                    </div>

                    <div class="r-gap"></div>

                    <div v-if="cartItems.length > 0">
                        <div v-for="(cartItem,index) in cartItems">
                            <div class="col-lg-12">
                                <div class="item">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="listing-item">
                                                <div class="left-image">
                                                    <a href="#"><img class="rest-img" :src="cartItem.product.logo"
                                                            :alt="cartItem.product.name"></a>
                                                </div>
                                                <div class="right-content align-self-center">

                                                    <a href="#" style="margin-top:12px;">
                                                        <h4>{{cartItem.product.name}}</h4>
                                                    </a>
                                                    <div>
                                                        <span>
                                                            <h6>{{cartItem.product.description}}</h6>
                                                        </span>
                                                    </div>
                                                    <div>
                                                        <span>
                                                            <h3>{{ cartItem.product.price  }}</h3>
                                                        </span>
                                                    </div>

                                                </div>
                                                <div
                                                    style=" float: right;width: 30%;position: absolute; right: 0; top: calc(50% - 30px); margin-right: 50px;">
                                                    <div>
                                                        <input type="number" style="height: 40px; width: 30%; position: absolute;text-align: center;
                                                    left: 60px;
                                                    top: 80px; " step="1" :value="cartItem.amount"
                                                            @input="updateQuantity(index, $event)"
                                                            @blur="checkQuantity(index, $event)" />
                                                    </div>

                                                    <div>
                                                        <button @click="removeItem(index)"
                                                            style="height: 40px; ;font-size: 16px;position: absolute; right: 2px; top: 80px;">Ukloni
                                                            iz korpe</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-else style="text-align: center;">
                        <h3>Vaša korpa je prazna.</h3>
                        <button>Potražite proizvode</button>
                    </div>
                </div>
            </div>
        </div>

    </section>
    <div class="xxxx container recent-listing">
        <div class="col-lg-12">

            <div class="listing-item">
               
                    </div>


                    <div class="summary">
                        <ul>
                            <li>Cijena <span>{{ subTotal  }}</span></li>
                            <li v-if="customerType.discount > 0">Popust <span>{{ discountPrice  }}</span></li>
                            <li class="total">Ukupno <span>{{ totalPrice}}</span></li>
                        </ul>
                        <div class="checkout">
                            <button type="button">Potvrdi porudžbinu</button>
                        </div>
                    </div>


                </section>
            </div>
        </div>
    </div>

</div>
`,

  created() {
    axios
    .get("rest/cart/getCart")
    .then(response => (this.cartItems = response.data.items,
                       this.price = response.data.price,
                       this.customerID = response.data.customerID ))
    },

    mounted(){

      axios 
       .get("rest/customers/getCustomerType")
       .then(response => (this.customerType = response.data))

    },

  computed: {
    itemCount: function () {
      var count = 0;

      for (var i = 0; i < this.cartItems.length; i++) {
        count += parseInt(this.cartItems[i].amount) || 0;
      }

      return count;
    },
    subTotal: function () {
      var subTotal = 0;

      for (var i = 0; i < this.cartItems.length; i++) {
        subTotal += this.cartItems[i].amount * this.cartItems[i].product.price;
      }

      return subTotal;
    },
    discountPrice: function () {
      return this.subTotal * this.customerType.discount / 100;
    },
    totalPrice: function () {
      return this.subTotal - this.discountPrice;
    }
  },

  methods: {
    updateQuantity: function (index, event) {
     
      var cartItem = this.cartItems[index];
      var value = event.target.value;
      var valueInt = parseInt(value);

      // Minimum quantity is 1, maximum quantity is 100, can left blank to input easily
      if (value === "") {
        cartItem.amount = value;
      } else if (valueInt > 0 && valueInt < 100) {
        cartItem.amount = valueInt;
      }
      this.$set(this.cartItems, index, cartItem);
		axios
		.put("rest/cart/updateCartItem/" + this.cartItems[index].product.name, this.cartItems[index])
		.then(response => alert('successfully updated ' + response.data.product.name))
    },
    checkQuantity: function (index, event) {
      // Update quantity to 1 if it is empty
      if (event.target.value === "") {
        var cartItem = this.cartItems[index];
        cartItem.amount = 1;
        this.$set(this.cartItems, index, cartItem);
      }
    },

    

    removeItem: function (index) {
       axios
       .delete("rest/cart/removeCartItem/" + this.cartItems[index].product.name)
       .then( this.cartItems.splice(index, 1))       
       

    }
   
  }
});