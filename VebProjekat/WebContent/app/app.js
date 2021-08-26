
const LoginPage = { template: '<login-page></login-page>' }
const RegistrationPage = { template: '<registration-page></registration-page>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: LoginPage},
	    { path: '/registration', component: RegistrationPage }
	  ]
});

var app = new Vue({
	router,
	el: '#app'
});