Vue.component("home-deliverer", {

    data() {
        return {
            loggedInUser : {}
        }
    },

	
	template: ` 
    <div id="dash">
    <section  class="admin-dash" data-stellar-background-ratio="0.5">
          <div class="container">
               <div class="row">

                    <div class="col-md-8 col-ms-12">
                         <div class="section-title wow fadeInUp" data-wow-delay="0.1s">
                              <h1>Dobrodošli {{loggedInUser.fistName}}! </h1>
                              <h5></h5>
                             
                             
                    
                         </div>
                    </div>
               </div>
          </div>
     </section>
     <section class="section" >
        <div class="container">
            <div class="row">
               <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
               data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                    <div class="features-item">
                        <div class="features-icon">
                            <h2>01</h2>
                            <img src="images/order.png" alt="">
                            <h4>Porudžbine</h4>
                            <p>Pregled svih porudžbina koje čekaju dostavljača, uz mogućnosti pretraživanja,filtriranja i sortiranja.</p>
                            <br><br><br><br><a href="delivererDashboard.html#/showAllOrders" type="button"  class="main-btn">
                              Pregled porudžbina
                            </a>
                         
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
                data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                <div class="features-item">
                    <div class="features-icon">
                        <h2>02</h2>
                        <img src="images/order.png" alt="">
                        <h4>Moje porudžbine</h4>
                        <p>Pregled vaših porudžbina, uključujući i pregled nedostavljenih porudžbina.</p>
                        <br><br><br><br><a href="delivererDashboard.html#/deliverersOrders" type="button"  class="main-btn">
                          Pregled neisporučenih porudžbina
                        </a>
                     
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
            data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                    <div class="features-item">
                        <div class="features-icon">
                            <h2>03</h2>
                            <img src="images/icons8-restaurant-64.png" alt="">
                            <h4>Zahtjevi za dostavu</h4>
                            <p>Pregled zahtjeva za dostavu.</p>
                            <br><br><br><br><br><a href="delivererDashboard.html#/deliverersRequests" type="button"  class="main-btn">
                              Pregled zahtjeva
                            </a>
                        </div>
                    </div>
                </div>
               
               
            </div>
        </div>
    </section>
     </div>
`,

mounted : function() {
    axios
   .get('rest/users/getLoggedUser')
   .then(response => (this.loggedInUser = response.data))
}
});