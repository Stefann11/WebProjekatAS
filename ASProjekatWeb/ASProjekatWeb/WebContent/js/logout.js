function logout() {
	$.ajax({
		type : "POST",
		url : "rest/users/logout",
		success: function(result) {
					toastr["success"]("Uspešno ste se odjavili!");
					setTimeout(function() {
						location.href = "login.html";
					}, 1000);
				},
		error: function(jqXHR, textStatus, errorThrown)  {
			toastr["error"](jqXHR.responseText);
		}
	});
	
}