toastr.options = {
    closeButton: false,
    debug: false,
    newestOnTop: false,
    progressBar: false,
    positionClass: "toast-top-right",
    preventDuplicates: true,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "5000",
    extendedTimeOut: "1000",
    showEasing: "linear",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut",
  };

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
                toastr["error"]("Sva polja osim opisa i količine moraju biti popunjena!");
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
                            toastr["success"]("Novi proizvod je dodat.");
                            setTimeout(function(){ location.href = "managerDashboard.html" }, 3000);
                            document.getElementById('ime').value='';
                            document.getElementById('cijena').value='';
                            document.getElementById('tip').value='';
                            document.getElementById('file').value='';
                            
                        })
                        .catch(err => {
                            if(err.toString() === "Error: Request failed with status code 400") toastr["error"]("Već postoji proizvod sa unesenim nazivom,molimo Vas pokušajte drugi naziv.");
                        })

                }
                this.errors.forEach(element => {
                    console.log(element)
                });
        }

		

        }



    }
});