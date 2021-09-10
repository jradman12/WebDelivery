let x = new Vue({
    el: "#xxxx",
    data: {
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
    },
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
  