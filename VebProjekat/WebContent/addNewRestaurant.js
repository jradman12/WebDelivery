let newRest = new Vue({

	el : "#idk",

	data : {
        restaurant : {},
		managers: null,
        managersExist : false
	},

    mounted () 
        {
            axios
            .get('rest/restaurants/getAllRestaurants')
            .then(response => (this.rests=response.data))
        },

        methods: {
            onChangeFileUpload ($event) {
                this.file=this.$refs.file.files[0];
                this.encodeImage(this.file)
                console.log(this.newRestaurant.logo)
            },
            encodeImage(input){
                if(input){
                const reader = new FileReader()
                reader.onload=(e)=>{
                    this.newRestaurant.logo = e.target.result}
                reader.readAsDataURL(input)
            }
        },
            registerRestaurant: function (event) {
                event.preventDefault();
                
                console.log(this.newRestaurant.logo)
                
                this.errors = [];
                if (!this.errors.length) {
                    axios
                        .post('rest/restaurants/addNewRestaurant', {
                       
                                     "id" : this.newRestaurant.id,
                                     "name": this.newRestaurant.name,
                                     "logo" : this.newRestaurant.logo
                                     
                                    
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
    
        }
        
    



});