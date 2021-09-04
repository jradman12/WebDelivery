Vue.component("manager-comments", {

    data() {
        return {
            comments : []
        }
    },
	
	template: ` 
    <div id="container">
   
    <section class="r-section" v-if="comments.length!=0">
    <h1>Pregled komentara</h1>

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
                        </tr>
                   </thead>
              </table>
         </div>
         <div class="tbl-content">
              <table class="r-table" cellpadding="0" cellspacing="0" border="0">
                   <tbody>
                        <tr v-for="comm in comments">
                             <td> {{ comm.author.username }} </td>
                             <td> {{ comm.text }} </td>
                             <td> {{ comm.rating }} </td>
                             <td v-if="comm.approved=='APPROVED'"> Odobren </td>
                             <td v-else-if="comm.approved=='REJECTED'"> Odbijen</td>
                             <td v-else>Čeka na odobravanje</td>
               
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
}});