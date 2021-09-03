const HomeManager = { template: '<home-manager></home-manager>' }
const ManagerRestaurant = { template: '<manager-restaurant></manager-restaurant>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-manager', component: HomeManager},
	    { path: '/showMenagerRestaurant', name: 'manager-restaurant', component: ManagerRestaurant }
	  ]
});

var app = new Vue({
	router,
	el: '#ruterMenadzer'
});