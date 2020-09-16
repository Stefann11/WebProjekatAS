$(document).ready(function() {
	allAmenities();
});

function allAmenities() {
	
	const queryString2 = window.location.search;
	var queryString = queryString2.substr(1, queryString2.length);
	
	var sepStrings = queryString.split("=");
	
	var idApartment = sepStrings[1];
	
	$.ajax({
		type : "GET",
		url : "rest/amenties/",
		success : function(result) {
			var table = $("#allAmenitiesTable");
			table.empty();
			if (result == null) {
				$('#allAmenitiesTable').hide();
			} else {
				$('#allAmenitiesTable').show();
				
				var body = $("<tbody></tbody>");		
				
				var oneItem = $("<tr></tr>");
								
				oneItem.append("<td><label class = \"a1\" for=\"idApartment\"><b>Id apartmana:</b></label></td>");
				oneItem.append("<td><input type=\"text\" name=\"idApartment\" id=\"idApartment\" value=" + idApartment + "></td>");
					
				body.append(oneItem);
				result.forEach(function(item, index) {
					
					var oneItem = $("<tr></tr>");
								
					var name = item["name"];
					
					var deleted = item["deleted"];
					
					if (deleted == false){
						
						oneItem.append("<td><label class = \"a1\" for=" + name + "><b>"+ name + "</b></label></td>");
						oneItem.append("<td><input type=\"checkbox\" name=\"amenities\" id=" + name + " value=\"" + name + "\"></td>");
					
					
						
						
						body.append(oneItem);
		
						
					}					
					


				});
				
				
				var last =  $("<tr></tr>");
				
				last.append("<td colspan=\"2\"><input type = \"submit\" value=\"Posalji\"</td>");
				
				body.append(last);
				
				table.append(body);
				
				
				
				
			}
		}
	});
}