Vue.component("admin-comments", {

    data() {
        return {
            comments  : []
        }
    },

    template: ` 
    <div>
		<img src="images/ce3232.png" width="100%" height="90px">


    <section class="r-section">
         <h1>Pregled komentara</h1>

         <div class="r-gap"></div>

         <div id="coms">
              <div class="tbl-header">
                   <table  class="r-table" cellpadding="0" cellspacing="0" border="0">
                        <thead>
                             <tr>
                                  <th>Kupac</th>
                                  <th>Restoran</th>
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
                                  <td> {{ comm.restaurant.name }} </td>
                                  <td> {{ comm.text }} </td>
                                  <td> {{ comm.rating }} </td>
                                  <td> {{ comm.approved ? "Odobren" : "Odbijen" }} </td>
                    
                             </tr>
                        </tbody>
                   </table>
              </div>
         </div>


         <div class="r-gap"></div>

    </section>
	</div>
`,

mounted : function() {
    axios
   .get('rest/comments/getAllComments')
   .then(response => (this.comments = response.data))
}
});