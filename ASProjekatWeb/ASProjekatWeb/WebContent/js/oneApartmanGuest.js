window.onload = function () {
	var url = document.location.href;
	var strings = url.split("?");

	var sepStrings = strings[1].split(";");

	var sepIds = sepStrings[0].split("=");
	var id = sepIds[1];

	
	
	$.ajax({
		type : "POST",
		url: "rest/apartments/findOne",
		contentType : "application/json",
		data: JSON.stringify({id: id}),
		success: function(result) {
					toastr["success"]("Uspešno!");
		if (result == null) {
				$('#allApartmentsTable').hide();
			} else {
				
					var table = $("#rezervisiTable");
					table.empty();
					var apartment = $("<tr></tr>");
				    apartment.append("<td style=\"background-color: #ffffff00\">" + "Rezerviši:" + "</td>");
					apartment.append("<td style=\"background-color: #ffffff00\"><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/newReservation.html?id=" + id + "\'\" value=\"Rezerviši\"></td>");
					table.append(apartment);
				
					var location = result["location"];	
					var locationStr = JSON.stringify(location);
					
					//printLocation(location, apartment);
					//console.log(loc);
					
					var lenLoc = locationStr.length;
					
					var posLoc = locationStr.indexOf("longitude");
					var pos2Loc = locationStr.indexOf("latitude");
					var pos3Loc = locationStr.indexOf("streetAndNumber");
					var pos4Loc = locationStr.indexOf("place");
					var pos5Loc = locationStr.indexOf("postcode");
					
					var res1Loc = locationStr.substring(posLoc+11,pos2Loc-2);
					var res2Loc = locationStr.substring(pos2Loc+10, pos3Loc-13);
					var res3Loc = locationStr.substring(pos3Loc+18, pos4Loc-3);
					var res4Loc = locationStr.substring(pos4Loc+8, pos5Loc-3);
					var res5Loc = locationStr.substring(pos5Loc+10, lenLoc-2); 
				
				
				
					document.getElementById('id').innerHTML = result["id"];
					document.getElementById('type').innerHTML = result["type"];
					document.getElementById('numberOfRooms').innerHTML = result["numberOfRooms"];
					document.getElementById('numberOfGuests').innerHTML = result["numberOfGuests"];
					document.getElementById('longitude').innerHTML = res1Loc;
					document.getElementById('latitude').innerHTML = res2Loc;
					document.getElementById('streetAndNumber').innerHTML = pos3Loc;
					document.getElementById('place').innerHTML = res4Loc;
					document.getElementById('postcode').innerHTML = res5Loc;
					document.getElementById('priceForOneNight').innerHTML = result["priceForOneNight"];
					document.getElementById('checkInTime').innerHTML = result["checkInTime"];
					document.getElementById('checkOutTime').innerHTML = result["checkOutTime"];
					if(result["status"] == true){
						document.getElementById('status').innerHTML = "aktivan";
					}
					else{
						document.getElementById('status').innerHTML = "neaktivan";
					}
					
					
					
					
					var stringHelp="";
			
				
				
				
				
				if ((result["listOfAmenities"] != null)&&(result["listOfAmenities"].length != 0)){
				result["listOfAmenities"].forEach(function(item, index) {
					
					
					stringHelp += item["name"] + ", ";
					
					
				});
				var stringHelp2 = stringHelp.substring(0,stringHelp.length-2);
				document.getElementById('amenities').innerHTML = stringHelp2;
				}
				else{
					document.getElementById('amenities').innerHTML = "Nema sadržaja u ovom apartmanu!";
				}
			
				var slikePrikaz = "";
				for(var i = 0; i<result["images"].length; i++){
					slikePrikaz +="<div class='poCetri'>";
					slikePrikaz +="<div class='kartica ' style='max-width: 450px; max-hight: 450px;'><img src='";
					slikePrikaz += result["images"][i];
					slikePrikaz += "' width='350' heigth='350'>";
					slikePrikaz +="</div></div>";
				}
				$("#prikazi").append(slikePrikaz);
				
				}	
				
				
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
	

   

}

