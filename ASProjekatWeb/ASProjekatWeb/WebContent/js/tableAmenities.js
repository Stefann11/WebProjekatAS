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
					
						var name = item["name"];				
						
						apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editAmenities.html?id=" + id + ";name=" + name  +  "\'\" value=\"Izmeni\"></td>");
	
						apartment.append("<td><button onclick=\"deleteAmenitie( " + id + ")\">Obriši</button></td>");
	
						body.append(apartment);
					}
				});
				table.append(body);
				
			}
		}
	});
}

function deleteAmenitie(id){
	$.ajax({
		type : "DELETE",
		url: "rest/amenties/delete",
		contentType : "application/json",
		data: JSON.stringify({id: id}),
		success: function(result) {
					toastr["success"]("Uspešno ste obrisali sadržaj!");
					setTimeout(function() {
						location.href = "tableAmenities.html";
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
}