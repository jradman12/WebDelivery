Vue.component("customer-restaurantView", {

    data() {
        return {
            rests: [],
            comments: []
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

                                <div class="row" v-if="restaurant.menu==null">

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
                                            <p><span class="price cursive-font">{{product.price}} RSD</span></p>
                                            <p><button style="margin-top:7px; color: #7a1a1a;" @click="addToCart(product)">Dodaj u korpu</button></p>
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
                                        <tr v-for="comment in comments2">
                                            <td>{{ comment.rating }}</td>
                                            <td>{{ comment.text }}</td>
                                            <td>{{ comment.author }}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </section>

                    </div>
                </div>

            </section>
        </div>
`,
    mounted() {

        initAutocomplete();
    },

    methods: {
        addToCart(){
            alert('go fuck yourself hun');
        }
    }



});