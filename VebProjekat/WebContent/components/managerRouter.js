const HomeManager = { template: '<home-manager></home-manager>' }
const ManagerRestaurant = { template: '<manager-restaurant></manager-restaurant>' }
const ManagersProfile = { template: '<manager-profile></manager-profile>' }
const ManagersComments = { template: '<manager-comments></manager-comments>' }
const ManagersOrders = { template: '<manager-orders></manager-orders>' }
const ManagersRequests={template: '<manager-requests></manager-requests>'}

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'home-manager', component: HomeManager},
	    { path: '/showManagerRestaurant', name: 'manager-restaurant', component: ManagerRestaurant },
		 { path: '/managersProfile', name: 'manager-profile', component: ManagersProfile },
		 { path: '/managersComments', name: 'manager-comments', component: ManagersComments },
		 { path: '/managersOrders', name: 'manager-orders', component: ManagersOrders },
		 { path: '/managersRequests', name: 'manager-requests', component: ManagersRequests }
	  ]
});

var app = new Vue({
	router,
	el: '#ruterMenadzer',
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