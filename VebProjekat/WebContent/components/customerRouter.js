const CustomerHome = { template: '<customer-home></customer-home>' }
const CustomerOrders = { template: '<customer-orders></customer-orders>' }
const CustomerCart = { template: '<customer-cart></customer-cart>'}
const CustomerRestaurants = { template: '<customer-restaurants></customer-restaurants>' }
const CustomerRestaurantView = { template: '<customer-restaurantView></customer-restaurantView>' }
const CustomerProfile = { template: '<customer-profile></customer-profile>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'customer-home', component: CustomerHome},
	    { path: '/orders', name: 'customer-orders', component: CustomerOrders },
		 { path: '/cart', name: 'customer-cart', component: CustomerCart },
		 { path: '/restaurants', name: 'customer-restaurants', component: CustomerRestaurants },
         { path: '/restaurantView', name: 'customer-restaurantView', component: CustomerRestaurantView },
         { path: '/profile', name: 'customer-profile', component: CustomerProfile } 
         
	  ]
});

var app = new Vue({
	router,
	el: '#customerRouter'
});