$(document).ready(function() {
	allAmenities();
});

function allAmenities() {
	$.ajax({
		type : "GET",
		url : "rest/amenties/",
		success : function(result) {
			var table = $("#tableAmenities");
			table.empty();
			if (result == null) {
				$('#tableAmenities').hide();
			} else {
				$('#tableAmenities').show();
				table.append("<thead><tr><th>Naziv</th><th>Izmena</th><th>Brisanje</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					
					var deleted = item["deleted"];
					
					var id = item["id"];
					
					if (deleted == false){
						var apartment = $("<tr></tr>");
	
						apartment.append("<td>" + item["name"] + "</td>");
					
						nameString = item["name"];				
						
						apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editAmenities.html?id=" + id + ";name=" + nameString  +  "\'\" value=\"Izmeni\"></td>");
	
						apartment.append("<td><button onclick=\"deleteAmenitieInApartment( " + id + ")\">Obriši</button></td>");
	
						body.append(apartment);
					}
				});
				table.append(body);
				
			}
		}
	});
}

function callback(id){
	deleteAmenitie(id);
}
function deleteAmenitie(id){
	$.ajax({
		type : "DELETE",
		url: "rest/amenties/delete",
		contentType : "application/json",
		data: JSON.stringify({id: id}),
		success: function(data, textStatus, XmlHttpRequest){
					toastr["success"]("Uspešno brisanje!");
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
}

function deleteAmenitieInApartment(id){
	$.ajax({
		type : "POST",
		url: "rest/apartments/deleteAmenitie",
		contentType : "application/json",
		data: JSON.stringify({id: id}),
		success: function(result) {
					callback(id);
					setTimeout(function() {
						
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
}