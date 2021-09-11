const HomeDeliverer = { template: '<home-deliverer></home-deliverer>' }
const AllOrders = { template: '<all-orders></all-orders>' }
const DeliverersOrders = { template: '<deliverer-orders></deliverer-orders>' }
const DeliverersProfile = { template: '<deliverer-profile></deliverer-profile>' }
const DeliverersRequests = { template: '<deliverer-requests></deliverer-requests>' }


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-deliverer', component: HomeDeliverer},
	    { path: '/showAllOrders', name: 'all-orders', component: AllOrders },
		 { path: '/deliverersOrders', name: 'deliverer-orders', component: DeliverersOrders },
		 { path: '/deliverersProfile', name: 'deliverer-profile', component: DeliverersProfile },
         { path: '/deliverersRequests', name: 'deliverer-requests', component: DeliverersRequests }
		
	  ]
});

var app = new Vue({
	router,
	el: '#ruterDostavljac',
	methods : {
		cmoon : function(event){
			 event.preventDefault();
			 console.log('in logout')
			 
		axios
			 .get('rest/ls/logout')
			 .then(response => {
				  this.message = response.data;
				  window.location.assign(response.data)
			 })
			 .catch(err => {
				  console.log("There has been an error! Please check this out: ");
				  console.log(err);
			 })
			 return true;
   }
	}
});