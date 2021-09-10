Vue.component("customer-restaurants", {

    data() {
        return {
            rests: [],
            newRestaurant: {},
            file: {},
            errors: [],
            managers: [],
            currentSort: 'name',
            currentSortDir: 'asc',
            filter: '',
            typeFilter: '',
            openFilter: '',
            sortFilter: '',
            restId: '',
            mapLocation: ''
        }
    },

    template: ` 
    <div>
		<img src="images/ce3232.png" width="100%" height="90px">


    <section class="r-section">
        <div  class="recent-listing">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-heading">
                            <h1>Restorani u ponudi</h1>
                        </div>
                    </div>
                    <div class="r-gap"></div>
                    <div class="col-lg-12">
                        <input type="search" v-model="filter" id="pac-input" placeholder="Pretraži restorane..."
                            style="width: 300px; margin-bottom: 10px;">
                            <span class="r-span2"></span>
                            <select v-model="typeFilter" style="width: 160px;">
                                <option value="">Tip restorana</option>
                                <option value="Kineski">Kineski</option>
                                <option value="Fast food">Fast food</option>
                                <option value="Indijski">Indijski</option>
                                <option value="Italijanski">Italijanski</option>
                                <option value="Roštilj">Roštilj</option>
                                <option value="Picerija">Picerija</option>
                                <option value="Vegan">Vegan</option>
                            </select>
                            <span class="r-span"></span>
                            <select v-model="openFilter" style="width: 160px;">
                                <option value="">Svi restorani</option>
                                <option value="OPEN">Samo otvoreni</option>
                            </select>
                            <span class="r-span"></span>
                            <!-- ----- sort ----- -->
                            <select v-model="sortFilter" @change="sort()" style="width: 160px;">
                                <option value="">Sortiraj</option>
                                <option value="name, asc">Naziv, asc</option>
                                <option value="name, desc">Naziv, desc</option>
                                <option value="type, asc">Tip, asc</option>
                                <option value="type, desc">Tip, desc</option>
                                <option value="avgRate, asc">Prosječna ocjena, asc</option>
                                <option value="avgRate, desc">Prosječna ocjena, desc</option>
                            </select>
                    </div>
                    <div v-for="rest in sortedRests">
                        <div class="col-lg-12">
                            <div class="item">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="listing-item">
                                            <div class="left-image">
                                                <a href="restaurant.html"><img class="rest-img"
                                                        v-bind:src="rest.logo" alt=""></a>
                                            </div>
                                            <div class="right-content align-self-center">
                                                <a href="restaurant.html">
                                                    <h4>{{rest.name}}</h4>
                                                </a>
                                                <h6>{{rest.status == "OPEN" ? 'Otvoren' : 'Zatvoren'}}</h6>
                                                <ul class="rate">
                                                    <li><i class="fa fa-star-o"></i></li>
                                                    <li><i class="fa fa-star-o"></i></li>
                                                    <li><i class="fa fa-star-o"></i></li>
                                                    <li><i class="fa fa-star-o"></i></li>
                                                    <li><i class="fa fa-star-o"></i></li>
                                                    <li>{{rest.averageRating}}</li>
                                                </ul>
                                                <span>{{ (rest.location).address.addressName  }}</span>
                                                <span>, </span>
                                                <span>{{ (rest.location).address.city }}</span>
                                                <span>, </span>
                                                <span>{{ (rest.location).address.postalCode  }}</span>
                                            
                                                <div v-bind="typeFilter">
                                                    <span>
                                                        <h6>{{rest.typeOfRestaurant}}</h6>
                                                    </span>

                                                </div>
                                                <div class="main-white-button">
                                                    <button type="submit" @click="setRest(rest, $event)"><i class="fa fa-eye"></i>Pogledaj restoran</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>
	</div>
`,
    mounted() {

        
        axios
            .get('rest/restaurants/getAllRestaurants')
            .then(response => (this.rests = response.data))
    },

    methods: {
        setRest: function (rest, event) {
            event.preventDefault();
            axios
                .post('rest/restaurants/setCurrentRestaurant', rest)

                // set timeout done
                setTimeout(function(){ location.href = "customerDashboard.html#/restaurantView"}, 2000);

        },
        sort: function () {
            console.log(this.sortFilter);
            if (this.sortFilter.includes("name")) {
                this.currentSort = 'name';
            } else if (this.sortFilter.includes("type")) {
                this.currentSort = 'typeOfRestaurant';
            } else {
                this.currentSort = 'averageRating';
            }
            this.currentSortDir = this.sortFilter.includes("desc") ? 'desc' : 'asc';
            console.log(this.currentSortDir);
        },
        onChangeFileUpload($event) {
            this.file = this.$refs.file.files[0];
            this.encodeImage(this.file)
            console.log(this.newRestaurant.logo)
        },
        encodeImage(input) {
            if (input) {
                const reader = new FileReader()
                reader.onload = (e) => {
                    this.newRestaurant.logo = e.target.result
                }
                reader.readAsDataURL(input)
            }
        }

    },
    computed: {
        filteredRests: function () {
            return this.rests.filter(c => {
                if (this.filter == '') return true;
                let location = parts;
                console.log(location);
                let split = location.split(',');
                let addrName = split[0];
                let city = split[1];
                let postal = split[2];
                return (c.location.address.addressName.includes(addrName) || c.location.address.city.includes(city) || c.location.address.postalCode.includes(postal) || c.name.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0 || c.averageRating === parseFloat(this.filter));
            })
        },
        filterType() {
            if (!this.openFilter) {
                return this.filteredRests.filter(x => {
                    if (!this.typeFilter) return true;
                    return (x.typeOfRestaurant === this.typeFilter);
                })
            } else {
                return this.filterOpen.filter(x => {
                    if (!this.typeFilter) return true;
                    return (x.typeOfRestaurant === this.typeFilter);
                })
            }

        },
        filterOpen() {
            if (!this.typeFilter) {
                return this.filteredRests.filter(x => {
                    if (!this.openFilter) return true;
                    return (x.status === this.openFilter);
                })
            } else {
                return this.filterType.filter(x => {
                    if (!this.openFilter) return true;
                    return (x.status === this.openFilter);
                })
            }


        },
        sortedRests: function () {
            let usss = !this.typeFilter ? this.filterOpen : this.filterType;
            return usss.sort((a, b) => {
                let modifier = 1;
                if (this.currentSortDir === 'desc') modifier = -1;
                if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
                return 0;
            });
        }
    }



});