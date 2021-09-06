function fixDate(user) {

	user.dateOfBirth = new Date(parseInt(user.dateOfBirth));
	
	return user;
}

Vue.component("manager-profile", {

    data() {
        return {
            loggedUser: {},
            mode : 'BROWSE',
            message : null
        }
    },
	
	template: ` 
     <div>
     <img src="images/ce3232.png" width="100%" height="90px">

<div class="container">
<h1></h1>
<section data-stellar-background-ratio="0.5">
<form id="formForEdit" class="well form-horizontal">
            <div class="form-group">
                         <label class="col-md-4 control-label">Ime</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="input-group">
                                   <input  class="form-control" style="width: 300px;"
                                        type="text" v-model="loggedUser.fistName" v-bind:disabled="mode=='BROWSE'">
                              </div>
                         </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">Prezime</label>
                        <div class="col-md-4 inputGroupContainer">
                             <div class="input-group">
                                  <input  class="form-control" style="width: 300px;"
                                       type="text" v-model="loggedUser.lastName" v-bind:disabled="mode=='BROWSE'">
                             </div>
                        </div>
                   </div>

                    

                    <!-- Text input-->

                    <div class="form-group">
                         <label class="col-md-4 control-label">Korisničko ime</label>
                         <div class="col-md-4 inputGroupContainer">
                              <div class="input-group">
                                   <input  class="form-control" type="text" style="width: 300px;" v-model="loggedUser.username" disabled>
                              </div>
                         </div>
                    </div>
                    
                   <div class="form-group">
                    <label class="col-md-4 control-label">Datum rođenja</label>
                    <div class="col-md-4 inputGroupContainer">
                         <div class="input-group">
                            <vuejs-datepicker v-model="loggedUser.dateOfBirth" format="dd.MM.yyyy" disabled></vuejs-datepicker> 
                         </div>
                    </div>
               </div>
               <div class="form-group">
                    <label class="col-md-4 control-label">Lozinka</label>
                    <div class="col-md-4 inputGroupContainer">
                         <div class="input-group">
                              <input  class="form-control" type="password" style="width: 300px;" v-model="loggedUser.password" v-bind:disabled="mode=='BROWSE'">
                         </div>
                    </div>
               </div>
              

                    


                    <!-- Select Basic -->

                    <!-- Success message -->
                    <!---- <div class="alert alert-success" role="alert" id="success_message"> <i
                              class="glyphicon glyphicon-thumbs-up"></i> Uspješno kreiran restoran!</div>-->

                    <!-- Button -->
                    <div class="form-group" style="align-content: center;">
                        <div class="row">
                           
                            
                            <div class="col-sm">
                                <div class="col-md-6"><br>
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp 
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp &nbsp&nbsp&nbsp 
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                     <button style="text-align: center;"  
                                         class="section-btn" v-on:click="updateUser" v-bind:disabled="mode=='BROWSE'">Potvrda</button>
                               </div>
                              
                            </div>
                            <div class="col-sm">
                                <div class="col-md-6"><br>
                                     <button style="text-align: center;" 
                                         class="section-btn" v-on:click="changeMode">Izmijeni</button>
                               </div>
                            </div>
                            </div>
                            </div>
                            </form>
                            </section>
                    </div>
                    </div>
                            
    
    
`,
  mounted () {
        axios
          .get('rest/users/getLoggedUser')
          .then(response => (this.loggedUser = fixDate(response.data)))
          
    },
    methods : {
        changeMode: function(e) {
                e.preventDefault()
                this.mode='EDIT'
        },

        updateUser : function(e){
             e.preventDefault();
            axios
               .put("rest/users/updateUser/" + this.loggedUser.username, 
                    {"fistName": this.loggedUser.fistName,
                    "lastName" : this.loggedUser.lastName,
                    "dateOfBirth" : this.loggedUser.dateOfBirth,
                    "gender" : this.loggedUser.gender,
                    "username": this.loggedUser.username, 
                    "password" : this.loggedUser.password,
                    "role" : this.loggedUser.role})
               .then(response => {
                    this.message = response.data;
               })
               .catch(err => {
                    console.log("There has been an error! Please check this out: ");
                    console.log(err);
               })
          } 
    },
    components: {
      	vuejsDatepicker
    },
    filters: {
    	dateFormat: function (value, format) {
    		var parsed = moment(value);
    		return parsed.format(format);
    	}
   	}

});