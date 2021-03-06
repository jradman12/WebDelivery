Vue.component("manager-comments", {

    data() {
        return {
            comments : []
        }
    },
	
	template: ` 
    <div id="container">
    <img src="images/ce3232.png" width="100%" height="90px">
    
</section>
    <section class="r-section" v-if="comments.length!=0">
    <h1></h1>

    <div class="r-gap"></div>

    <div id="coms">
         <div class="tbl-header">
              <table  class="r-table" cellpadding="0" cellspacing="0" border="0">
                   <thead>
                        <tr>
                             <th>Kupac</th>
                             <th>Komentar</th>
                             <th>Ocjena</th>
                             <th>Status</th>
                             <th></th>
                        </tr>
                   </thead>
              </table>
         </div>
         <div class="tbl-content">
              <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                   <tbody>
                        <tr v-for="comm in comments">
                             <td> {{ comm.author }} </td>
                             <td> {{ comm.text }} </td>
                             <td> {{ comm.rating }} </td>
                            <td v-if="comm.status=='WAITING'">Čeka na odobravanje</td>
                            <td v-else-if="comm.status=='REJECTED'">Odbijen</td>
                            <td v-else>Odobren</td>
                            <td><button v-if="comm.status=='WAITING'" @click="approveComment(comm)">Odobri</button>
                            <button v-if="comm.status=='WAITING'" @click="declineComment(comm)" >Odbij</button>
                            <span v-else>-</span></td>
               
                        </tr>
                   </tbody>
              </table>
         </div>
    </div>


    <div class="r-gap"></div>

</section>
<section class="r-section" v-else>
<div class="gtco-section">
<div class="gtco-container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2 text-center gtco-heading">
            <h1 class="cursive-font primary-color">Trenutno nema komentara.</h1>
            
        </div>
    </div>
    </div>
</div>
</section >
</div>
    
    
`,
mounted : function() {
    axios
   .get('rest/comments/getAllCommentsForRestaurant')
   .then(response => (this.comments = response.data))
},

methods:{
     approveComment : function(comment){
          axios
          .put('rest/comments/approveComment/' + comment.id)
          .then(response=>{
               this.comments = [];
               response.data.forEach(x => {
                   this.comments.push(x);
               });
               return this.comments;
          }
          )
     },
     declineComment : function(comment){
          axios
          .put('rest/comments/rejectComment/' + comment.id)
          .then(response=>{
               this.comments = [];
               response.data.forEach(x => {
                   this.comments.push(x);
               });
               return this.comments;
          }
          )
     }


}

});