let comments = new Vue({

	el : "#coms",

	data : {
        comments  : []
	},

    mounted : function() {
         axios
        .get('rest/comments/getAllComments')
        .then(response => (this.comments = response.data))
    }

});