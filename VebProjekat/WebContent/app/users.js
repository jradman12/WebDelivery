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
        users : [{"username":"adam","password":"adamin","fistName":"Adam","lastName":"Martinez","gender":"OTHER","dateOfBirth":717285600000,"role":"ADMINISTRATOR","type":null,"deleted":false,"blocked":false},{"username":"ella","password":"admin","fistName":"Ella","lastName":"Williams","gender":"FEMALE","dateOfBirth":604796400000,"role":"ADMINISTRATOR","type":null,"deleted":false,"blocked":true},{"username":"sanja","password":"sanja123","fistName":"Sanja","lastName":"Suvira","gender":"FEMALE","dateOfBirth":716594400000,"role":"MANAGER","type":null,"deleted":false,"blocked":false},{"username":"xoforlife","password":"abel","fistName":"Anastasija","lastName":"Lazic","gender":"FEMALE","dateOfBirth":911433600000,"role":"DELIVERER","type":null,"deleted":false,"blocked":false},{"username":"acomatic","password":"acika","fistName":"Aleksandar","lastName":"Matic","gender":"MALE","dateOfBirth":848793600000,"role":"MANAGER","type":null,"deleted":false,"blocked":false},{"username":"mini","password":"minkica","fistName":"Minka","lastName":"Minkicaa","gender":"FEMALE","dateOfBirth":944956800000,"role":"CUSTOMER","type":{"typeName":"GOLD","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"jessie","password":"bbjess","fistName":"Jessica","lastName":"Evans","gender":"FEMALE","dateOfBirth":788659200000,"role":"CUSTOMER","type":{"typeName":"PLATINUM","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"stef","password":"stefko","fistName":"Stefan","lastName":"Zec","gender":"MALE","dateOfBirth":608166000000,"role":"CUSTOMER","type":{"typeName":"SILVER","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"zenin","password":"husbando","fistName":"Toji","lastName":"Fushiguro","gender":"MALE","dateOfBirth":946598400000,"role":"CUSTOMER","type":{"typeName":"GOLD","discount":0,"points":0},"deleted":false,"blocked":false}],
        //users : [],
        currentSort:'username',
        currentSortDir:'asc',
        filter:'',
        roleFilter : '',
        typeFilter : ''
	},

    created : function() {
         axios
        .get('rest/users/getAllUsers')
        .then(response => (this.users = fixData(response.data)))
    },

    methods : {
		block:function(userToBlock){
			axios
                .post('rest/users/blockUser', userToBlock)
                .then(response => {
                    this.users = [];
                    response.data.forEach(x => {
                        this.users.push(x);
                    });
                    return this.users;
                });
        },
		unblock:function(userToUnblock){
			axios
                .post('rest/users/unblockUser', userToUnblock)
                .then(response => {
                    this.users = [];
                    response.data.forEach(x => {
                        this.users.push(x);
                    });
                    return this.users;
                });

		},
		
        sort:function(s) {
            if(s === this.currentSort) {
              this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
            }
            this.currentSort = s;
          }
        },
        computed: {
            filteredUsers: function() {
                return this.users.filter(c => {
                  if(this.filter == '') return true;
                  return (c.username.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0 || c.fistName.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0 || c.lastName.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0);
                })
              },
            filterRole() {
              return this.filteredUsers.filter( x => {
                if(!this.roleFilter) return true;
                return (x.role === this.roleFilter);
              })
            },
            filterType(){
              console.log('usao u filter type, ovde je typeFilter: ' + this.typeFilter)
              return this.filterRole.filter( x => {
                if(!this.typeFilter) return true;
                // console.log('filter type za : ' + this.x.username + ', tu je njegov tip ' + x.type.typeName)
               return (x.type.typeName === this.typeFilter);
              })
            }, 

            sortedUsers:function() {
              console.log('usao u sortedUsers, ovde je typeFilter: ' + this.typeFilter)
              console.log('usao u sortedUsers, ovde je roleFilter: ' + this.roleFilter)

              let usss = !this.roleFilter ? this.filteredUsers : (!this.typeFilter ? this.filterRole : this.filterType);
              return usss.sort((a,b) => {
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