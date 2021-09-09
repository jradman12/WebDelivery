Vue.component("customer-home", {

    template: ` 
   <div id="dash">
   <section  class="admin-dash" data-stellar-background-ratio="0.5">
         <div class="container">
              <div class="row">

                   <div class="col-md-8 col-ms-12">
                        <div class="section-title wow fadeInUp" data-wow-delay="0.1s">
                             <h1>Dobrodošli! </h1>
                             <h5></h5>
                        </div>
                   </div>
              </div>
         </div>
    </section>

    <section class="section">
         <div class="container">
              <div class="row">
                   <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
                        data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                        <div class="features-item">
                             <div class="features-icon">
                                  <h2>01</h2>
                                  <img src="images/icons8-order-history-64.png" alt="">
                                  <h4>Porudžbine</h4>
                                  <p>Pregled svih vaših porudžbina, uz pregled nedostavljenih porudžbina.</p>
                                  <br><a href="customerDashboard.html#/orders" type="button" class="main-btn">
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
                                  <img src="images/icons8-restaurant-64.png" alt="">
                                  <h4>Restorani</h4>
                                  <p>Pregled restorana u ponudi i poručivanje.</p>
                                  <br><a href="customerDashboard.html#/restaurants" type="button" class="main-btn">
                                       Pregled restorana
                                  </a>
                             </div>
                        </div>
                   </div>
                   <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
                        data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                        <div class="features-item">
                             <div class="features-icon">
                                  <h2>03</h2>
                                  <img src="images/icons8-shopping-cart-64.png" alt="">
                                  <h4>Korpa</h4>
                                  <p>Pregled vaše korpe. </p><br>
                                  <br><a href="customerDashboard.html#/cart" type="button" class="main-btn">
                                       Pregled korpe
                                  </a>
                             </div>
                        </div>
                   </div>
              </div>
         </div>
    </section>
    </div>
`
});