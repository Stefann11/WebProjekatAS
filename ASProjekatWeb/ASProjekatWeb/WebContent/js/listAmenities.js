$(document).ready(function() {
	listAmenities();
});

function listAmenities(){
	const queryString2 = window.location.search;
	var queryString = queryString2.substr(1, queryString2.length);
	
	var sepStrings = queryString.split("&");
	
	var sepStringsLength = sepStrings.length;
	
	var amenities = [];
	
	for (var i = 0; i<sepStringsLength; i++){
		var all = sepStrings[i].split("=");
		var replaced = all[1].replace("+", " ");
		amenities.push(replaced); 
	}
	
	var idApartment = "1";
	
	$.ajax({
				type: "POST",
				url: "rest/amenties/saveToApartment",
				contentType : "application/json",
				data: JSON.stringify({idApartment: idApartment, amenities: amenities}),
				success: function(result) {
					toastr["success"]("Uspešno ste dodali sadržaj!");
					setTimeout(function() {
						location.href = "hostIndex.html";
						$('#registrationForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	
	
}