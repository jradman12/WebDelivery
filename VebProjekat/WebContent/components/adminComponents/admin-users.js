function fixData(users) {
	for (var u in users) {
		u.dateOfBirth = new Date(parseInt(u.dateOfBirth));
        console.log(u.username);
	}
	return users;
}

Vue.component("admin-users", {

    data() {
        return {
            //users : [{"username":"adam","password":"adamin","fistName":"Adam","lastName":"Martinez","gender":"OTHER","dateOfBirth":717285600000,"role":"ADMINISTRATOR","type":null,"deleted":false,"blocked":false},{"username":"ella","password":"admin","fistName":"Ella","lastName":"Williams","gender":"FEMALE","dateOfBirth":604796400000,"role":"ADMINISTRATOR","type":null,"deleted":false,"blocked":true},{"username":"sanja","password":"sanja123","fistName":"Sanja","lastName":"Suvira","gender":"FEMALE","dateOfBirth":716594400000,"role":"MANAGER","type":null,"deleted":false,"blocked":false},{"username":"xoforlife","password":"abel","fistName":"Anastasija","lastName":"Lazic","gender":"FEMALE","dateOfBirth":911433600000,"role":"DELIVERER","type":null,"deleted":false,"blocked":false},{"username":"acomatic","password":"acika","fistName":"Aleksandar","lastName":"Matic","gender":"MALE","dateOfBirth":848793600000,"role":"MANAGER","type":null,"deleted":false,"blocked":false},{"username":"mini","password":"minkica","fistName":"Minka","lastName":"Minkicaa","gender":"FEMALE","dateOfBirth":944956800000,"role":"CUSTOMER","type":{"typeName":"GOLD","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"jessie","password":"bbjess","fistName":"Jessica","lastName":"Evans","gender":"FEMALE","dateOfBirth":788659200000,"role":"CUSTOMER","type":{"typeName":"PLATINUM","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"stef","password":"stefko","fistName":"Stefan","lastName":"Zec","gender":"MALE","dateOfBirth":608166000000,"role":"CUSTOMER","type":{"typeName":"SILVER","discount":0,"points":0},"deleted":false,"blocked":false},{"username":"zenin","password":"husbando","fistName":"Toji","lastName":"Fushiguro","gender":"MALE","dateOfBirth":946598400000,"role":"CUSTOMER","type":{"typeName":"GOLD","discount":0,"points":0},"deleted":false,"blocked":false}],
            users: [],
            currentSort: 'username',
            currentSortDir: 'asc',
            filter: '',
            roleFilter: '',
            typeFilter: ''
        }
    },

    template: ` 
    <div>
	<img src="images/ce3232.png" width="100%" height="90px">

    <section class="r-section">
               <h1>Korisnici sistema</h1>

               <div class="r-gap"></div>

               <div id="usersiii">
                    <div class="tbl-header">
                         <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                              <thead>
                                   <tr>
                                        <th @click="sort('username')">Korisničko ime</th>
                                        <th @click="sort('fistName')">Ime</th>
                                        <th @click="sort('lastName')">Prezime</th>
                                        <th>Datum rođenja</th>
                                        <th>Pol</th>
                                        <th>Status</th>
                                        <th>Tip</th>
                                        <!-- <th>Tip kupca</th> -->
                                        <th>Broj poena</th>
                                        <th>Obriši korisnika</th>
                                   </tr>
                              </thead>
                         </table>
                    </div>

                    <div class="tbl-content">
                         <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                              <tbody>
                                   <tr v-for="user in sortedUsers">
                                        <td>{{user.username}}</td>
                                        <td>{{user.fistName}}</td>
                                        <td>{{user.lastName}}</td>
                                        <td>{{user.dateOfBirth | dateFormat('DD.MM.YYYY.')}}</td>
                                        <td>{{user.gender}}</td>
                                        <td><button v-if="user.blocked && user.role != 'ADMINISTRATOR'"
                                                  @click="unblock(user)"><i class="fa fa-ban"></i> Deblokiraj</button>
                                             <button v-if="!user.blocked && user.role != 'ADMINISTRATOR'"
                                                  @click="block(user)"><i class="fa fa-ban"></i> Blokiraj</button>
                                             <span v-if="user.role == 'ADMINISTRATOR'">-</span>
                                        </td>

                                        <td>{{roleFilter==='CUSTOMER' ? user.type.typeName : user.role}}</td>
                                        <td>{{user.role ==='CUSTOMER' ? user.points : '-'}}</td>
                                        <td> <button @click="deleteUser(user)">Obriši</button></td>
                                   </tr>
                              </tbody>
                         </table>
                    </div>


                    <div class="r-gap"></div>
                    <div class="r-gap"></div>

                    <div class="r-row">
                         <div class="col-6">
                              <input type="search" v-model="filter" placeholder="Pretraži korisnike..."
                                   style="width: 300px; margin-bottom: 10px;">
                              <span class="r-span"></span>
                              <span class="r-span2"></span>
                                   <span class="r-span2"></span>
                                        <span class="r-span2"></span>
                                             <span class="r-span2"></span>



                         </div>
                         <div align="right" class="col-6">
                              <select v-model="roleFilter" style="width: 160px;">
                                   <option value="">Svi korisnici</option>
                                   <option value="CUSTOMER">Kupci</option>
                                   <option value="DELIVERER">Dostavljači</option>
                                   <option value="MANAGER">Menadžeri</option>
                                   <option value="ADMINISTRATOR">Administratori</option>
                              </select>
                         </div>
                         <span class="r-span"></span>
                              <span v-show="roleFilter === 'CUSTOMER'">
                                   <div align="right" class="col-6">
                                        <select v-model="typeFilter" style="width: 160px;">
                                             <option value="">Svi kupci</option>
                                             <option value="GOLD">Zlatni</option>
                                             <option value="SILVER">Srebrni</option>
                                             <option value="PLATINUM">Platinum</option>
                                             </select>
                                             </div>
                                        </span>
          
          
                              </div>
          
                              <div class="r-gap"></div>
                              <a href="adminDashboard.html#/adminAddsUser" class="section-btn">Dodaj novog korisnika</a>
          
                         </div>

          </section>
</div>
  
`,
    created: function () {
        axios
            .get('rest/users/getAllUsers')
            .then(response => (this.users = fixData(response.data)))
    },

    methods: {
        block: function (userToBlock) {
            axios
                .put('rest/users/blockUser/' + userToBlock.username)
                .then(response => {
                    this.users = [];
                    response.data.forEach(x => {
                        this.users.push(x);
                    });
                    return this.users;
                });
        },
        unblock: function (userToUnblock) {
            axios
                .put('rest/users/unblockUser/'+ userToUnblock.username)
                .then(response => {
                    this.users = [];
                    response.data.forEach(x => {
                        this.users.push(x);
                    });
                    return this.users;
                });

        },

        sort: function (s) {
            if (s === this.currentSort) {
                this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc';
            }
            this.currentSort = s;
        },

        deleteUser : function(user){

            axios
          .put('rest/users/deleteUser/' + user.username)
          .then(response=>{
               this.users = [];
               response.data.forEach(x => {
                   this.users.push(x);
               })
            });
        }
              



        
    },
    computed: {
        filteredUsers: function () {
            return this.users.filter(c => {
                if (this.filter == '') return true;
                return (c.username.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0 || c.fistName.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0 || c.lastName.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0);
            })
        },
        filterRole() {
            return this.filteredUsers.filter(x => {
                if (!this.roleFilter) return true;
                return (x.role === this.roleFilter);
            })
        },
        filterType() {
            console.log('usao u filter type, ovde je typeFilter: ' + this.typeFilter)
            return this.filterRole.filter(x => {
                if (!this.typeFilter) return true;
                // console.log('filter type za : ' + this.x.username + ', tu je njegov tip ' + x.type.typeName)
                return (x.type.typeName === this.typeFilter);
            })
        },

        sortedUsers: function () {
            console.log('usao u sortedUsers, ovde je typeFilter: ' + this.typeFilter)
            console.log('usao u sortedUsers, ovde je roleFilter: ' + this.roleFilter)

            let usss = !this.roleFilter ? this.filteredUsers : (!this.typeFilter ? this.filterRole : this.filterType);
            return usss.sort((a, b) => {
                let modifier = 1;
                if (this.currentSortDir === 'desc') modifier = -1;
                if (a[this.currentSort] < b[this.currentSort]) return -1 * modifier;
                if (a[this.currentSort] > b[this.currentSort]) return 1 * modifier;
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