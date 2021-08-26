Vue.component("registration-page", {
	data: function () {
		    return {
		      podaci: null
		    }
	},
	template: ` 
<div>
    <form >
    <table>
        <tr><td>Username</td><td><input type="text" name="username"></td></tr>
        <tr><td>Password</td><td><input type="password" name="password"></td></tr>
        <tr><td><input type="submit" value="Login"></td></tr>
    </table>
    <p id="error" hidden="true"></p>
    <p id="success" hidden="true"></p>
    </form>
	
</div>		  
`});