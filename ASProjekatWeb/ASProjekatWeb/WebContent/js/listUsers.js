$(document).ready(function() {
	listUsers();
});


function listUsers() {
	$.ajax({
		type : "GET",
		url : "rest/users/",
		success : function(result) {
			var table = $("#allUsersTable");
			table.empty();
			if (result == null) {
				$('#allUsersTable').hide();
			} else {
				$('#allUsersTable').show();
				table.append("<thead><tr><th>Username</th><th>Ime</th><th>Prezime</th><th>Pol</th><th>Uloga</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var user = $("<tr></tr>");

					user.append("<td>" + item["username"] + "</td>");
					user.append("<td>" + item["name"] + "</td>");
					user.append("<td>" + item["surname"] + "</td>");
					
					var gender = item["gender"];
					var genderStr = JSON.stringify(gender);
					var len = genderStr.length;
					var pos = genderStr.indexOf(":");
					var res = genderStr.substring(pos+2,len-2);
					user.append("<td>" + res + "</td>");
						
					var role = item["role"];	
					var roleStr = JSON.stringify(role);
					var len = roleStr.length;
					var pos = roleStr.indexOf(":");
					var res = roleStr.substring(pos+2,len-2);
					user.append("<td>" + res + "</td>");
					
					var apartmentsForRent = item["apartmentsForRent"];
					var apartmentsForRentStr = JSON.stringify(apartmentsForRent);
					
					
					
					//user.append("<td>" + apartmentsForRentStr + "</td>");

					body.append(user);
				});
				table.append(body);
				
			}
		}
	});
}