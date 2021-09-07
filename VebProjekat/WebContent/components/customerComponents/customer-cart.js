Vue.component("customer-cart", {

  data() {
    return {
      products: [
        {
          image: "https://via.placeholder.com/200x150",
          name: "Najlepše želje s keksom",
          description: "Jede mi se mnogo, pa eto",
          price: 120,
          quantity: 2
        },
        {
          image: "https://via.placeholder.com/200x150",
          name: "Stark smoki",
          description: "Čekam ponoć da se pozdravimo!",
          price: 153,
          quantity: 1
        }
      ],
      tax: 5,
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
      promoCode: "",
      discount: 0
    }
  },
  template: ` 
    <div id="cart">
    <img src="images/ce3232.png" width="100%" height="90px">
    <section class="r-section">
        <div id="usersiii" class="recent-listing">
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

                    <div v-if="products.length > 0">
                        <div v-for="(product,index) in products">
                            <div class="col-lg-12">
                                <div class="item">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="listing-item">
                                                <div class="left-image">
                                                    <a href="#"><img class="rest-img" :src="product.image"
                                                            :alt="product.name"></a>
                                                </div>
                                                <div class="right-content align-self-center">

                                                    <a href="#" style="margin-top:12px;">
                                                        <h4>{{product.name}}</h4>
                                                    </a>
                                                    <div>
                                                        <span>
                                                            <h6>{{product.description}}</h6>
                                                        </span>
                                                    </div>
                                                    <div>
                                                        <span>
                                                            <h3>{{product.price  }}</h3>
                                                        </span>
                                                    </div>

                                                </div>
                                                <div
                                                    style=" float: right;width: 30%;position: absolute; right: 0; top: calc(50% - 30px); margin-right: 50px;">
                                                    <div>
                                                        <input type="number" style="height: 40px; width: 30%; position: absolute;text-align: center;
                                                    left: 60px;
                                                    top: 80px; " step="1" :value="product.quantity"
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
                <section class="xxxx" v-if="products.length > 0">
                    <div class="promotion">
                        <label for="promo-code">Koji ste tip korisnika?</label>
                        <input type="text" id="promo-code" v-model="promoCode" /> <button type="button"
                            @click="checkPromoCode"></button>
                    </div>


                    <div class="summary">
                        <ul>
                            <li>Cijena <span>{{ subTotal  }}</span></li>
                            <li v-if="discount > 0">Popust <span>{{ discountPrice  }}</span></li>
                            <li>Dostava <span>{{ tax }}</span></li>
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
computed: {
  itemCount: function() {
    var count = 0;

    for (var i = 0; i < this.products.length; i++) {
      count += parseInt(this.products[i].quantity) || 0;
    }

    return count;
  },
  subTotal: function() {
    var subTotal = 0;

    for (var i = 0; i < this.products.length; i++) {
      subTotal += this.products[i].quantity * this.products[i].price;
    }

    return subTotal;
  },
  discountPrice: function() {
    return this.subTotal * this.discount / 100;
  },
  totalPrice: function() {
    return this.subTotal - this.discountPrice + this.tax;
  }
},

methods: {
  updateQuantity: function(index, event) {
      console.log(this.products[index])
      console.log(event.target.value)
      console.log(parseInt(event.target.value))
      
    var product = this.products[index];
    var value = event.target.value;
    var valueInt = parseInt(value);

   // Minimum quantity is 1, maximum quantity is 100, can left blank to input easily
   if (value === "") {
    product.quantity = value;
  } else if (valueInt > 0 && valueInt < 100) {
    product.quantity = valueInt;
  }
    

    this.$set(this.products, index, product);
  },
  checkQuantity: function(index, event) {
    // Update quantity to 1 if it is empty
    if (event.target.value === "") {
      var product = this.products[index];
      product.quantity = 1;
      this.$set(this.products, index, product);
    }
  },
  removeItem: function(index) {
    this.products.splice(index, 1);
  },
  checkPromoCode: function() {
    for (var i = 0; i < this.promotions.length; i++) {
      if (this.promoCode === this.promotions[i].code) {
        this.discount = parseFloat(
          this.promotions[i].discount.replace("%", "")
        );
        return;
      }
    }

    alert("Nevalidan tip korisnika!");
  }
}
});