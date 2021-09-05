const NewRest = { template: '<new-rest></new-rest>' }
const NewUser = { template: '<new-user></new-user>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', name: 'new-rest', component: NewRest},
	    { path: '/newManager', name: 'new-user', component: NewUser }
	  ]
});

var app = new Vue({
	router,
	el: '#webShop'
});
