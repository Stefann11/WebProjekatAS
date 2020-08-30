$(document).ready(function() {
	getLoggedUser();
});

function getLoggedUser() {
	$.ajax({
		type : "GET",
		url : "rest/users/loggedUser",
		success : function(result) {
			var username = $("#lblUsername");
			username.empty();
			if (result == null) {
				$('#lblUsername').hide();
			} else {
				$('#lblUsername').show();
				username.append(result["username"]);
			}
			return username;
		}
	});
	
}