Vue.component("manager-restaurant", {

    data() {
        return {
            restaurant : {},
            products : []
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
                    <div class="col-lg-12" id="map" style="width:78%;height:500px; border-style: inset; border-color:#ce32322d;"></div>
                    </div>

                    <section class="r-section">
                        <div class="gtco-section">
                            <div class="gtco-container">
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
                                        <h2 class="cursive-font primary-color">JELOVNIK</h2>
                                        <br><br><br>
                                    </div>
                                </div>

                                <div class="row" v-if="restaurant.menu === null">

                                <h3>Jelovnik je trenutno prazan! </h3>
                            </div>
                            <div class="row" v-else>

                                <div class="col-lg-4 col-md-4 col-sm-6" v-for="product in restaurant.menu">
                                    <a v-bind:href="product.logo" class="fh5co-card-item image-popup">
                                        <figure>
                                        <div class="overlay"><i class="ti-plus"></i></div>
                                            <img v-bind:src="product.logo" alt="Image" class="img-responsive">
                                        </figure>
                                        <div class="fh5co-text">
                                            <h2>{{product.name}}</h2>
                                            <p>{{product.description}}</p>
                                            <p><span class="price cursive-font">{{product.price + "RSD" }}</span></p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                
                <div class="listing-item r-section">
                    <a href="addNewArticle.html" class="section-btn">Dodaj novi proizvod</a>
                    </div>
                    

                
            </div>
        
        </section>
        </div>
        </div>
`,
 created(){
        axios
        .get("rest/restaurants/getManagersRestaurant")
        .then(response => (this.restaurant = response.data))
}

});