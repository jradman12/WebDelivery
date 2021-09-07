let restss = new Vue({

    el: "#usersiii",

    data: {
        rests: [],
        availableManagers: [],
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
        restId : '',
        mapLocation : ''
    },

    mounted() {
        axios
            .get('rest/restaurants/getAllRestaurants')
            .then(response => (this.rests = response.data)),

            axios
                .get('rest/managers/getAllAvailableManagers')
                .then(response => (this.availableManagers = response.data))
    },

    methods: {
        setRest: function(rest, event){
            event.preventDefault();
            console.log('usao u set rest iz rests.html, parametar koji je proslijedjen je ' + rest);

            axios
            .post('rest/restaurants/setCurrentRestaurant', rest)
            
            setTimeout(function(){ location.href = "restaurant.html"}, 2000);
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
        },
        registerRestaurant: function (event) {
            event.preventDefault();

            console.log(this.newRestaurant.logo)

            this.errors = [];
            if (!this.errors.length) {
                let aName = this.newRestaurant.location.split(",")[0];
                let aCity = this.newRestaurant.location.split(",")[1];
                let aPostCode = this.newRestaurant.location.split(",")[2];

                axios
                    .post('rest/restaurants/registerNewRestaurant', {

                        "typeOfRestaurant": this.newRestaurant.typeOfRestaurant,
                        "name": this.newRestaurant.name,
                        "logo": this.newRestaurant.logo,
                        "location" : {
                            "latitude" : 19.84,
                            "longitude" : 24.24,
                            "address" : {
								"addressName" : aName,
								"city" : aCity,
								"postalCode" : aPostCode
							}
                        }
                    })
                    .then(response => {
                        this.message = response.data;
                    })
                    .catch(err => {
                        console.log("There has been an error! Please check this out: ");
                        console.log(err);
                    })
                return true;
            }
            this.errors.forEach(element => {
                console.log(element)
            });
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