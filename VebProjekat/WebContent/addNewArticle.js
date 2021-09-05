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
            this.errors = [];
            if (!this.errors.length) {
                axios
                    .post('rest/manager/addNewArticle', {
                   
                                 "name" : this.newArticle.name,
                                 "price" : parseFloat(this.newArticle.price),
                                 "type" : this.newArticle.type,
                                 "logo" : this.newArticle.logo,
                                 "description" : this.newArticle.description,
                                 "quantity" : parseInt(this.newArticle.quantity,10)

                                 
                                
                    })
                    .then(response => {
                        this.message = response.data;
                        window.location.assign(response.data)
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