Vue.component("app-header", {
	data: function () {
		    return {
		      podaci: null
		    }
	},
	template: ` 

<div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <router-link to="/" class="nav-item nav-link" exact>Features</router-link>
        <router-link to="/registration" class="nav-item nav-link" exact>Registration</router-link>
      </div>
    </div>
  </nav>
  <div>	  		  
`});