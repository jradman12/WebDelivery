const HomeManager = { template: '<home-manager></home-manager>' }
const ManagerRestaurant = { template: '<manager-restaurant></manager-restaurant>' }
const ManagersProfile = { template: '<manager-profile></manager-profile>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-manager', component: HomeManager},
	    { path: '/showManagerRestaurant', name: 'manager-restaurant', component: ManagerRestaurant },
		 { path: '/managersProfile', name: 'manager-profile', component: ManagersProfile }
	  ]
});

var app = new Vue({
	router,
	el: '#ruterMenadzer'
});