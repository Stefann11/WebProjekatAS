$(document).ready(function() {
	returnUser();
});

function returnUser() {
	$.ajax({
		type : "GET",
		url : "rest/users/loggedUser",
		success : function(result) {
			var username = $("#username");
			username.empty();
			if (result == null) {
				$('#username').hide();
			} else {
				$('#username').show();
				username.append(result["username"]);
			}
			document.getElementById('username').value = result["username"];
			document.getElementById('name').value = result["name"];
			document.getElementById('surname').value = result["surname"];
			document.getElementById('password').value = result["password"];
			document.getElementById('repassword').value = result["password"];
			return result;
		}
	});
	
}