Vue.component("admin-restaurants", {
  data() {
    return {
      rests: [],
      availableManagers: [],
      newRestaurant: {},
      file: {},
      errors: [],
      managers: [],
      currentSort: "name",
      currentSortDir: "asc",
      filter: "",
      typeFilter: "",
      openFilter: "",
      sortFilter: "",
      restId: "",
      mapLocation: "",
    };
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
                                                <a href="#"><img class="rest-img"
                                                        v-bind:src="rest.logo" alt=""></a>
                                            </div>
                                            <div class="right-content align-self-center">
                                                <a href="#">
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
        <div class="r-gap"></div>
        <a href="adminDashboard.html#/adminAddRestaurant" class="section-btn">Dodaj novi restoran</a>
        </div>
    </section>
    <div>
    
	</div>
`,
  mounted() {
    axios
      .get("rest/restaurants/getAllRestaurants")
      .then((response) => (this.rests = response.data));
  },

  methods: {
    setRest: function (rest, event) {
      event.preventDefault();
      console.log(
        "usao u set rest iz rests.html, parametar koji je proslijedjen je " +
          rest
      );

      axios.post("rest/restaurants/setCurrentRestaurant", rest);

      location.href = "adminDashboard.html#/adminsRestaurantView";
    },
    sort: function () {
      console.log(this.sortFilter);
      if (this.sortFilter.includes("name")) {
        this.currentSort = "name";
      } else if (this.sortFilter.includes("type")) {
        this.currentSort = "typeOfRestaurant";
      } else {
        this.currentSort = "averageRating";
      }
      this.currentSortDir = this.sortFilter.includes("desc") ? "desc" : "asc";
      console.log(this.currentSortDir);
    },
  },
  computed: {
    filteredRests: function () {
      return this.rests.filter((c) => {
        if (this.filter == "") return true;
        return (
          c.location.address.city
            .toLowerCase()
            .includes(this.filter.toLowerCase()) ||
          c.name.toLowerCase().includes(this.filter.toLowerCase()) ||
          c.averageRating === parseFloat(this.filter)
        );
      });
    },
    filterType() {
      if (!this.openFilter) {
        return this.filteredRests.filter((x) => {
          if (!this.typeFilter) return true;
          return x.typeOfRestaurant === this.typeFilter;
        });
      } else {
        return this.filterOpen.filter((x) => {
          if (!this.typeFilter) return true;
          return x.typeOfRestaurant === this.typeFilter;
        });
      }
    },
    filterOpen() {
      if (!this.typeFilter) {
        return this.filteredRests.filter((x) => {
          if (!this.openFilter) return true;
          return x.status === this.openFilter;
        });
      } else {
        return this.filterType.filter((x) => {
          if (!this.openFilter) return true;
          return x.status === this.openFilter;
        });
      }
    },
    sortedRests: function () {
      let usss = !this.typeFilter ? this.filterOpen : this.filterType;
      return usss.sort((a, b) => {
        let modifier = 1;
        if (this.currentSortDir === "desc") modifier = -1;
        if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
        if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
        return 0;
      });
    },
  },
});
