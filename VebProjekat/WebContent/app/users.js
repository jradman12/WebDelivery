function fixData(users) {
	for (var u in users) {
		u.dateOfBirth = new Date(parseInt(u.dateOfBirth));
        console.log(u.username);
	}
	return users;
}

let getUsers = new Vue({

	el : "#usersiii",

	data : {
		//users :[{"username":"adam","password":"admin","fistName":"Adam","lastName":"Martinez","gender":"MALE","dateOfBirth":"Sep 24, 1992, 12:00:00 AM","role":"ADMINISTRATOR","isDeleted":false,"isBlocked":false}, {"username":"ella","password":"admin","fistName":"Ella","lastName":"Williams","gender":"FEMALE","dateOfBirth":"Mar 2, 1989, 12:00:00 AM","role":"ADMINISTRATOR","isDeleted":false,"isBlocked":true}, {"username":"mini","password":"minkica","fistName":"Minka","lastName":"Minkica","gender":"FEMALE","dateOfBirth":"Dec 12, 1999, 1:00:00 AM","role":"CUSTOMER","isDeleted":false,"isBlocked":false}],
        users  : [],
        currentSort:'username',
        currentSortDir:'asc'

	},
    created : function() {
        axios
          .get('rest/users/getAllUsers')
          .then(response => (this.users = fixData(response.data)))
        
          

    },

    methods : {
        sort:function(s) {
            //if s == current sort, reverse
            if(s === this.currentSort) {
              this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
            }
            this.currentSort = s;
          }
        },
        computed:{
          sortedCats:function() {
            return this.users.sort((a,b) => {
              let modifier = 1;
              if(this.currentSortDir === 'desc') modifier = -1;
              if(a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
              if(a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
              return 0;
            });
          }
        },

  filters: {
      dateFormat: function (value, format) {
          var parsed = moment(value);
          return parsed.format(format);
      }
     }
	

});