const HomeAdmin = { template: '<home-admin></home-admin>' }
const AdminsRestaurants = { template: '<admin-restaurants></admin-restaurants>' }
const AdminsRestaurantView = { template: '<admin-restaurantView></admin-restaurantView>' }
const AdminUsers = { template: '<admin-users></admin-users>'}
const AdminsComments = { template: '<admin-comments></admin-comments>' }
const AdminsProfile = { template: '<admin-profile></admin-profile>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-admin', component: HomeAdmin},
	    { path: '/adminsRestaurants', name: 'admin-restaurants', component: AdminsRestaurants },
		{ path: '/adminsRestaurantView', name: 'admin-restaurantView', component: AdminsRestaurantView },
		 { path: '/users', name: 'admin-users', component: AdminUsers },
		 { path: '/adminsComments', name: 'admin-comments', component: AdminsComments },
         { path: '/myProfile', name: 'admin-profile', component: AdminsProfile }
	  ]
});

var app = new Vue({
	router,
	el: '#adminRouter'
});