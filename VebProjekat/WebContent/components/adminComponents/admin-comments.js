Vue.component("admin-comments", {

    data() {
        return {
            dtos  : []
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
                             <tr v-for="dto in dtos">
                                  <td> {{ dto.comment.author }} </td>
                                  <td> {{ dto.restName }} </td>
                                  <td> {{ dto.comment.text }} </td>
                                  <td> {{ dto.comment.rating }} </td>
                                  <td v-if="dto.comment.status == 'APPROVED'">Odobren </td>
                                  <td v-else-if="dto.comment.status == 'REJECTED'">Odobren </td>
                                  <td v-else>Čeka na odobravanje</td>
                    
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
   .get('rest/comments/getCommentsWithRestNames')
   .then(response => (this.dtos = response.data))
}
});