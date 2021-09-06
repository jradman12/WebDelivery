const HomeManager = { template: '<home-manager></home-manager>' }
const ManagerRestaurant = { template: '<manager-restaurant></manager-restaurant>' }
const ManagersProfile = { template: '<manager-profile></manager-profile>' }
const ManagersComments = { template: '<manager-comments></manager-comments>' }
const ManagersOrders = { template: '<manager-orders></manager-orders>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-manager', component: HomeManager},
	    { path: '/showManagerRestaurant', name: 'manager-restaurant', component: ManagerRestaurant },
		 { path: '/managersProfile', name: 'manager-profile', component: ManagersProfile },
		 { path: '/managersComments', name: 'manager-comments', component: ManagersComments },
		 { path: '/managersOrders', name: 'manager-orders', component: ManagersOrders }
	  ]
});

var app = new Vue({
	router,
	el: '#ruterMenadzer'
});