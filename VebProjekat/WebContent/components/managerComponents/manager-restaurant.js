Vue.component("manager-restaurant", {

    data() {
        return {
            restaurant: {}
        }
    },
	
	template: ` 
    <div>
    <img src="images/ce3232.png" width="100%" height="90px">

     <div  class="container">

     <section data-stellar-background-ratio="0.5">
        
            <div class="recent-listing">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="item">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="listing-item">
                                            <div class="left-image">
                                                <img v-bind:src="restaurant.logo" class="rest-img" alt="">
                                            </div>
                                            <div class="right-content align-self-center">
                                                <a href="restaurant.html">
                                                    <h4>{{restaurant.name}}</h4>
                                                </a>
                                                <h6>{{restaurant.status == 'OPEN' ? 'Otvoren' : 'Zatvoren'}}</h6>
                                                <p>{{restaurant.averageRating}}</p>
                                                <span>{{ (restaurant.location).address.addressName  }}</span>
                                                <span>, </span>
                                                <span>{{ (restaurant.location).address.city }}</span>
                                                <span>, </span>
                                                <span>{{ (restaurant.location).address.postalCode  }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="listing-item">
                        <div id="googleMap" style="width:80%;height:500px"></div>
                    </div>

                    <section>
                        <div class="gtco-section">
                            <div class="gtco-container">
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
                                        <h1 class="cursive-font primary-color">JELOVNIK</h1>
                                        <p>Uživajte</p>
                                    </div>
                                </div>

                                <div class="row" v-if="restaurant.menu==null">

                                    <h3>Jelovnik je trenutno prazan! </h3>
                                </div>
                                <div class="row" v-else>

                                    <div class="col-lg-4 col-md-4 col-sm-6" v-for="article in restaurant.menu">
                                        <a v-bind:href="article.product.logo" class="fh5co-card-item image-popup"></a>
                                            <figure>
                                                <div class="overlay"><i class="ti-plus"></i></div>
                                                <img v-bind:src="article.product.logo" alt="Image" class="img-responsive">
                                            </figure>
                                            <div class="fh5co-text">
                                                <h2>{{article.product.name}}</h2>
                                                <p>{{article.product.description}}</p>
                                                <p><span class="price cursive-font">{{article.product.price}}</span></p>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    

                </div>
            </div>
        
        </section>
        </div>
        </div>
`,
 mounted(){
        axios
        .get("rest/managers/getRestaurantFromLoggedManager")
        .then(response => (this.restaurant=response.data))
    }


});