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
            console.log(this.newArticle.logo)
        },
        encodeImage(input){
            if(input){
            const reader = new FileReader()
            reader.onload=(e)=>{
                this.newArticle.logo = e.target.result}
            reader.readAsDataURL(input)
             }
        },

        addArticle: function (event) {
            event.preventDefault();
            console.log(this.newArticle.logo)
            console.log(this.newArticle.type)
            console.log(this.newArticle.name)
            console.log(this.newArticle.price)
            if(this.newArticle.name == '' || this.newArticle.price == null || this.newArticle.type == undefined || this.newArticle.logo == undefined){
                alert("Sva polja osim opisa i količine moraju biti popunjena!");
            }else {
                this.errors = [];
                if (!this.errors.length) {
                    axios
                        .post('rest/restaurants/addNewProduct', {
                    
                                    "name" : this.newArticle.name,
                                    "price" : parseFloat(this.newArticle.price),
                                    "type" : this.newArticle.type,
                                    "logo" : this.newArticle.logo,
                                    "description" : this.newArticle.description,
                                    "quantity" : parseInt(this.newArticle.quantity)
                        })
                        .then(response => {
                            this.message = response.data;
                            alert("Novi proizvod je dodat.");
                            setTimeout(function(){ location.href = "managerDashboard.html" }, 3000);
                            document.getElementById('ime').value='';
                            document.getElementById('cijena').value='';
                            document.getElementById('tip').value='';
                            document.getElementById('file').value='';
                            
                        })
                        .catch(err => {
                            if(err.toString() === "Error: Request failed with status code 400") alert("Već postoji proizvod sa unesenim nazivom,molimo Vas pokušajte drugi naziv.");
                        })

                }
                this.errors.forEach(element => {
                    console.log(element)
                });
        }

		

        }



    }
});