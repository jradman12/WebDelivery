const HomeAdmin = { template: "<home-admin></home-admin>" };
const AdminsRestaurants = {
  template: "<admin-restaurants></admin-restaurants>",
};
const AdminsRestaurantView = {
  template: "<admin-restaurantView></admin-restaurantView>",
};
const AdminUsers = { template: "<admin-users></admin-users>" };
const AdminsComments = { template: "<admin-comments></admin-comments>" };
const AdminsProfile = { template: "<admin-profile></admin-profile>" };
const AdminAddsNewUser = { template: "<admin-addNewUser></admin-addNewUser>" };
const AdminAddNewRestaurant = {
  template: "<admin-addNewRestaurant></admin-addNewRestaurant>",
};

const router = new VueRouter({
  mode: "hash",
  routes: [
    { path: "/", name: "home-admin", component: HomeAdmin },
    {
      path: "/adminsRestaurants",
      name: "admin-restaurants",
      component: AdminsRestaurants,
    },
    {
      path: "/adminsRestaurantView",
      name: "admin-restaurantView",
      component: AdminsRestaurantView,
    },
    { path: "/users", name: "admin-users", component: AdminUsers },
    {
      path: "/adminsComments",
      name: "admin-comments",
      component: AdminsComments,
    },
    { path: "/myProfile", name: "admin-profile", component: AdminsProfile },
    {
      path: "/adminAddsUser",
      name: "admin-addNewUser",
      component: AdminAddsNewUser,
    },
    {
      path: "/adminAddRestaurant",
      name: "admin-addNewRestaurant",
      component: AdminAddNewRestaurant,
    },
  ],
});

var app = new Vue({
  router,
  el: "#adminRouter",
  methods: {
    cmoon: function (event) {
      event.preventDefault();
      console.log("in logout");

      axios
        .get("rest/ls/logout")
        .then((response) => {
          this.message = response.data;
          window.location.assign(response.data);
        })
        .catch((err) => {
          console.log("There has been an error! Please check this out: ");
          console.log(err);
        });
      return true;
    },
  },
});
