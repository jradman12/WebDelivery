let newArticle = new Vue({

    el : "#articles",

	data : {
        newArticle : {},
        file : {}
    },
    methods : {
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

        addArticle: function (event) {
            event.preventDefault();
            console.log('im in addArticle yo')
            this.errors = [];
            if (!this.errors.length) {
                axios
                    .post('rest/restaurants/addNewProduct', {
                   
                                 "name" : this.newArticle.name,
                                 "price" : parseFloat(this.newArticle.price),
                                 "type" : this.newArticle.type,
                                 "logo" : this.newArticle.logo,
                                 "description" : this.newArticle.description,
                                 "quantity" : parseInt(this.newArticle.quantity,10)
                    })
                    .then(response => {
                        this.message = response.data;
						alert(this.message)
                    })
                    .catch(err => {
                        console.log("There has been an error! Please check this out: ");
                        console.log(err);
                    })

            }
            this.errors.forEach(element => {
                console.log(element)
            });

			setTimeout(function(){ location.href = "managerDashboard.html" }, 3000);

		

        }



    }
});