Vue.component("home-admin", {

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

     <section class="section">
          <div class="container">
               <div class="row">
                    <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12"
                         data-scroll-reveal="enter left move 30px over 0.6s after 0.4s">
                         <div class="features-item">
                              <div class="features-icon">
                                   <h2>01</h2>
                                   <img src="images/icons8-users-64.png" alt="">
                                   <h4>Korisnici</h4>
                                   <p>Pregled korisnika sistema, uz mogućnosti pretraživanja, filtriranja, sortiranja,
                                        kao i dodavanja i brisanja korisnika.</p>
                                   <br><a href="adminDashboard.html#/users" type="button" class="main-btn">
                                        Pregled korisnika
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
                                   <p>Pregled restorana uz mogućnosti pretraživanja, filtriranja, sortiranja, kao i
                                        dodavanja novih i postavljanja menadžera.</p>
                                   <br><a href="adminDashboard.html#/adminsRestaurants" type="button" class="main-btn">
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
                                   <img src="images/icons8-comments-64.png" alt="">
                                   <h4>Komentari</h4>
                                   <p>Pregled svih komentara na sajtu - i prihvaćenih i odbijenih. </p><br>
                                   <br><a href="adminDashboard.html#/adminsComments" type="button" class="main-btn">
                                        Pregled komentara
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