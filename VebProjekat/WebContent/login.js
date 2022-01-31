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

let login = new Vue({
  el: "#log",

  data: {
    user: {},
    errors: [],
    message: null,
  },

  methods: {
    checkLogin: function (event) {
	
      event.preventDefault();
      axios
        .post("rest/login", {
          username: this.user.username,
          password: this.user.password,
        })
        .then((response) => {
          this.message = response.data;

		document.getElementById("pass").value = "";
      	document.getElementById("usernamee").value = "";

          window.location.assign(response.data);
        })
        .catch((err) => {
          if (err.toString() === "Error: Request failed with status code 403")
            toastr["error"]("Nije moguće pristupiti nalogu jer je blokiran.");
          else if (
            err.toString() === "Error: Request failed with status code 401"
          )
            toastr["error"]("Neispravna lozinka. Molimo pokušajte ponovo.");
          else if (
            err.toString() === "Error: Request failed with status code 404"
          )
            toastr["error"](
              "Vaš nalog je uklonjen. Molimo kontaktirajte administratora za više informacija."
            );
        });
      
      return true;
    },
  },
});
