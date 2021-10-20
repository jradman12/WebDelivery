Vue.component("home-manager", {

     data() {
          return {
              loggedInUser : {}
          }
      },
	
	template: ` 
    	<div>
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
    
    <section class="section">
    <div class="container">
         <div class="row">
              <div class="col-lg-2.5 col-md-6 col-sm-12 col-xs-12"
                   data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                   <div class="features-item">
                        <div class="features-icon">
                             <h2>01</h2>
                             <img src="images/restaurant.png" alt="">
                             <h4>Restoran</h4>
                             <p>Pregled restorana za koji ste zaduženi.</p>
                             <br><a href="managerDashboard.html#/showManagerRestaurant" type="button" class="main-btn">
                                  Pregled restorana
                             </a>
                        </div>
                   </div>
              </div>
              <div class="col-lg-2.5 col-md-6 col-sm-12 col-xs-12"
              data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
              <div class="features-item">
                   <div class="features-icon">
                        <h2>02</h2>
                        <img src="images/order-food.png" alt="">
                        <h4>Porudžbine</h4>
                        <p>Pregled porudžina uz mogućnosti pretraživanja, filtriranja i sortiranja.</p>
                        <br><a href="managerDashboard.html#/managersOrders" type="button" class="main-btn">
                             Pregled porudžbina
                        </a>
                   </div>
              </div>
         </div>
              <div class="col-lg-2.5 col-md-6 col-sm-12 col-xs-12"
                   data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                   <div class="features-item">
                        <div class="features-icon">
                             <h2>03</h2>
                             <img src="images/icons8-restaurant-64.png" alt="">
                             <h4>Restorani</h4>
                             <p>Pregled restorana uz mogućnosti pretraživanja, filtriranja i sortiranja.</p>
                             <br><br>
                             <br><a href="managerDashboard.html#/managersRestaurants" type="button" class="main-btn">
                                  Pregled restorana
                             </a>
                        </div>
                   </div>
              </div>
            
              <div class="col-lg-2.5 col-md-6 col-sm-12 col-xs-12"
                   data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                   <div class="features-item">
                        <div class="features-icon">
                             <h2>04</h2>
                             <img src="images/icons8-comments-64.png" alt="">
                             <h4>Komentari</h4>
                             <p>Pregled svih komentara za dodjeljeni restoran i njihovo odbijanje ili odobravanje. </p><br>
                             <br><a href="managerDashboard.html#/managersComments" type="button" class="main-btn">
                                  Pregled komentara
                             </a>
                        </div>
                   </div>
              </div>

              <div class="col-lg-2.5 col-md-6 col-sm-12 col-xs-12"
                   data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                   <div class="features-item">
                        <div class="features-icon">
                             <h2>05</h2>
                             <img src="images/question.png" alt="">
                             <h4>Zahtjevi</h4>
                             <p>Pregled svih zahtjeva za dostavu i njihovo odbijanje ili odobravanje.  </p><br>
                             <br><a href="managerDashboard.html#/managersRequests" type="button" class="main-btn">
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