$(document).ready(function() {
	allApartments();
	searchApartments();
	sortiraj();
	filterStatusHostApartments();
	filterHostApartmentsType();
	
	$('#cenaOd').focusout(function() {
		var cenaOd = $('#cenaOd');
		var cenaOdVal = cenaOd.val();
		if (cenaOdVal && !(cenaOdVal.match('^[0-9]+(?:\.[0-9]+)?$'))) {
			toastr["error"]("Minimalnu cenu morate uneti kao ceo ili decimalan broj veći ili jednak 0.");
			cenaOd.val("");
		}
	});
	
	$('#cenaDo').focusout(function() {
		var cenaDo = $('#cenaDo');
		var cenaDoVal = cenaDo.val();
		if (cenaDoVal && !(cenaDoVal.match('^[0-9]+(?:\.[0-9]+)?$'))) {
			toastr["error"]("Maksimalnu cenu morate uneti kao ceo ili decimalan broj veći ili jednak 0.");
			cenaDo.val("");
		}
	});
	
	$('#brojSobaOd').focusout(function() {
		var brojSobaOd = $('#brojSobaOd');
		var brojSobaOdVal = brojSobaOd.val();
		if (!(brojSobaOdVal.match('^[0-9]*$'))) {
			toastr["error"]("Broj soba od mora biti pozitivan ceo broj.");
			brojSobaOd.val("");
		}
	});
	
	$('#brojSobaDo').focusout(function() {
		var brojSobaDo = $('#brojSobaDo');
		var brojSobaDoVal = brojSobaDo.val();
		if (!(brojSobaDoVal.match('^[0-9]*$'))) {
			toastr["error"]("Broj soba do mora biti pozitivan ceo broj.");
			brojSobaDo.val("");
		}
	});
	
	$('#brojOsobaOd').focusout(function() {
		var brojOsobaOd = $('#brojOsobaOd');
		var brojOsobaOdVal = brojOsobaOd.val();
		if (!(brojOsobaOdVal.match('^[0-9]*$'))) {
			toastr["error"]("Broj osoba od mora biti pozitivan ceo broj.");
			brojOsobaOd.val("");
		}
	});
	
	$('#brojOsobaDo').focusout(function() {
		var brojOsobaDo = $('#brojOsobaDo');
		var brojOsobaDoVal = brojOsobaDo.val();
		if (!(brojOsobaDoVal.match('^[0-9]*$'))) {
			toastr["error"]("Broj osoba do mora biti pozitivan ceo broj.");
			brojOsobaDo.val("");
		}
	});
	
});

function filterStatusHostApartments(){
	$('#filterFormStatus').submit(function(e) {
		e.preventDefault();
		let status = $('input[name=status]:checked').val();

			var $inputs = $('#filterFormStatus input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
		$.ajax({
			type : "POST",
			url : "rest/apartments/filterHostApartmentsStatus",
			contentType : "application/json",
			data: JSON.stringify({status: status}),
			success : function(result) {
			var table = $("#allApartmentsTable");
			table.empty();
			if (result == null) {
				$('#allApartmentsTable').hide();
			} else {
				$('#allApartmentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Tip</th><th>Broj soba</th><th>Broj gostiju</th><th>Lokacija</th><th>Domaćin</th><th>Cena po noći</th><th>Vreme za prijavu</th><th>Vreme za odjavu</th><th>Status</th><th>Izmena</th><th>Detaljniji prikaz</th><th>Obriši</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var apartment = $("<tr></tr>");

					apartment.append("<td>" + item["id"] + "</td>");
				
					var id = item["id"];
					
					
					var type = item["type"];
					/*var typeStr = JSON.stringify(type);
					var len = typeStr.length;
					var pos = typeStr.indexOf(":");
					var res = typeStr.substring(pos+2,len-2);
					apartment.append("<td>" + res + "</td>");*/
					apartment.append("<td>" + type + "</td>");
					
					apartment.append("<td>" + item["numberOfRooms"] + "</td>");
					apartment.append("<td>" + item["numberOfGuests"] + "</td>");
					
					var numberOfRooms = item["numberOfRooms"];
					var numberOfGuests = item["numberOfGuests"];
						
					var location = item["location"];	
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
					
					var resLoc = "Širina: " + res1Loc + ", dužina: " + res2Loc + ", ulica i broj: " + res3Loc + ", mesto: " + res4Loc + ", poštanski broj: " + res5Loc;
					
					apartment.append("<td>" + resLoc + "</td>");
					
									
					/*getAllDates(item["releaseDates"], apartment);
					
					getAllDates(item["availableDates"], apartment);*/
					
					var host = item["host"];	
					var hostStr = JSON.stringify(host);
					
					var str = printHost(host);
					
					
					
					apartment.append("<td>" + str + "</td>");
					
					
					/*var comments = item["comments"];	
					var commentsStr = JSON.stringify(comments);
					var lenCom = commentsStr.length;
					var posCom = commentsStr.indexOf("text");
					var pos2Com = commentsStr.indexOf("grade");
					var res1Com = commentsStr.substring(posCom+7,pos2Com-3);
					var res2Com = commentsStr.substring(pos2Com+7, lenCom-2);


					var resCom = "Komentar: " + res1Com + ", ocena: " + res2Com;

					apartment.append("<td>" + resCom + "</td>");*/
					
					apartment.append("<td>" + item["priceForOneNight"] + "</td>");
					
					var priceForOneNight = item["priceForOneNight"];
					
					apartment.append("<td>" + item["checkInTime"] + "h" + "</td>");
					apartment.append("<td>" + item["checkOutTime"] + "h" + "</td>");
					
					var checkInTime = item["checkInTime"];
					var checkOutTime = item["checkOutTime"];
					
					
					
					if(item["status"] == true){
						apartment.append("<td>" + "AKTIVAN" + "</td>");	
						}else{
						apartment.append("<td>" + "NEAKTIVAN" + "</td>");		
						}
					
						
									
					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editApartment.html?id=" + id + ";numberOfRooms=" + numberOfRooms + ";numberOfGuests=" + numberOfGuests + ";longitude=" + res2Loc + ";latitude=" + res1Loc + ";streetAndNumber=" + res3Loc + ";place=" + res4Loc + ";postcode=" + res5Loc + ";priceForOneNight=" + priceForOneNight + ";checkInTime=" + checkInTime + ";checkOutTime=" + checkOutTime + ";status=" + status + "\'\" value=\"Izmeni\"></td>");

					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + id + "\'\" value=\"Detaljno\"></td>");
					
					apartment.append("<td><button onclick=\"deleteApartment( " + id + ")\">Obriši</button></td>");
					
					body.append(apartment);
				});
				table.append(body);
				
			}
		}
		});
	});
}

function filterHostApartmentsType(){
	$('#filterForm').submit(function(e) {
		e.preventDefault();
		let type = $('input[name=type]:checked').val();

			var $inputs = $('#filterForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
		$.ajax({
			type : "POST",
			url : "rest/apartments/filterHostApartmentsType",
			contentType : "application/json",
			data: JSON.stringify({type: type}),
			success : function(result) {
			var table = $("#allApartmentsTable");
			table.empty();
			if (result == null) {
				$('#allApartmentsTable').hide();
			} else {
				$('#allApartmentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Tip</th><th>Broj soba</th><th>Broj gostiju</th><th>Lokacija</th><th>Domaćin</th><th>Cena po noći</th><th>Vreme za prijavu</th><th>Vreme za odjavu</th><th>Status</th><th>Izmena</th><th>Detaljniji prikaz</th><th>Obriši</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var apartment = $("<tr></tr>");

					apartment.append("<td>" + item["id"] + "</td>");
				
					var id = item["id"];
					
					
					var type = item["type"];
					/*var typeStr = JSON.stringify(type);
					var len = typeStr.length;
					var pos = typeStr.indexOf(":");
					var res = typeStr.substring(pos+2,len-2);
					apartment.append("<td>" + res + "</td>");*/
					apartment.append("<td>" + type + "</td>");
					
					apartment.append("<td>" + item["numberOfRooms"] + "</td>");
					apartment.append("<td>" + item["numberOfGuests"] + "</td>");
					
					var numberOfRooms = item["numberOfRooms"];
					var numberOfGuests = item["numberOfGuests"];
						
					var location = item["location"];	
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
					
					var resLoc = "Širina: " + res1Loc + ", dužina: " + res2Loc + ", ulica i broj: " + res3Loc + ", mesto: " + res4Loc + ", poštanski broj: " + res5Loc;
					
					apartment.append("<td>" + resLoc + "</td>");
					
									
					/*getAllDates(item["releaseDates"], apartment);
					
					getAllDates(item["availableDates"], apartment);*/
					
					var host = item["host"];	
					var hostStr = JSON.stringify(host);
					
					var str = printHost(host);
					
					
					
					apartment.append("<td>" + str + "</td>");
					
					
					/*var comments = item["comments"];	
					var commentsStr = JSON.stringify(comments);
					var lenCom = commentsStr.length;
					var posCom = commentsStr.indexOf("text");
					var pos2Com = commentsStr.indexOf("grade");
					var res1Com = commentsStr.substring(posCom+7,pos2Com-3);
					var res2Com = commentsStr.substring(pos2Com+7, lenCom-2);


					var resCom = "Komentar: " + res1Com + ", ocena: " + res2Com;

					apartment.append("<td>" + resCom + "</td>");*/
					
					apartment.append("<td>" + item["priceForOneNight"] + "</td>");
					
					var priceForOneNight = item["priceForOneNight"];
					
					apartment.append("<td>" + item["checkInTime"] + "h" + "</td>");
					apartment.append("<td>" + item["checkOutTime"] + "h" + "</td>");
					
					var checkInTime = item["checkInTime"];
					var checkOutTime = item["checkOutTime"];
					
					
					
					if(item["status"] == true){
						apartment.append("<td>" + "AKTIVAN" + "</td>");	
						}else{
						apartment.append("<td>" + "NEAKTIVAN" + "</td>");		
						}
					
						
									
					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editApartment.html?id=" + id + ";numberOfRooms=" + numberOfRooms + ";numberOfGuests=" + numberOfGuests + ";longitude=" + res2Loc + ";latitude=" + res1Loc + ";streetAndNumber=" + res3Loc + ";place=" + res4Loc + ";postcode=" + res5Loc + ";priceForOneNight=" + priceForOneNight + ";checkInTime=" + checkInTime + ";checkOutTime=" + checkOutTime + ";status=" + status + "\'\" value=\"Izmeni\"></td>");

					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + id + "\'\" value=\"Detaljno\"></td>");
					
					apartment.append("<td><button onclick=\"deleteApartment( " + id + ")\">Obriši</button></td>");
					
					body.append(apartment);
				});
				table.append(body);
				
			}
		}
		});
	});
}

function sortiraj(){
	$('#sortirajForm').submit(function(e) {
		e.preventDefault();
			
		var e1 = document.getElementById("sortiraj");
		var kojiSort = e1.options[e1.selectedIndex].value;
		if(kojiSort == "PO_CENI_RASTUCE"){
			sortCenaR();
		}
		if(kojiSort == "PO_CENI_OPADAJUCE"){
			sortCenaO();
		}
		if(kojiSort == "PO_ID_RASTUCE"){
			sortIdR();
		}
		if(kojiSort == "PO_ID_OPADAJUCE"){
			sortIdO();
		}
		if(kojiSort == "PO_BS_RASTUCE"){
			sortBsR();
		}
		if(kojiSort == "PO_BS_OPADAJUCE"){
			sortBsO();
		}
		if(kojiSort == "PO_BG_RASTUCE"){
			sortBgR();
		}
		if(kojiSort == "PO_BG_OPADAJUCE"){
			sortBgO();
		}
		if(kojiSort == "PO_TIPU_RASTUCE"){
			sortTipR();
		}
		if(kojiSort == "PO_TIPU_OPADAJUCE"){
			sortTipO();
		}
		if(kojiSort == "PO_VP_RASTUCE"){
			sortVpR();
		}
		if(kojiSort == "PO_VP_OPADAJUCE"){
			sortVpO();
		}
		if(kojiSort == "PO_VO_RASTUCE"){
			sortVoR();
		}
		if(kojiSort == "PO_VO_OPADAJUCE"){
			sortVoO();
		}
			
		});
}
function sortCenaO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[6];
		      y = rows[i + 1].getElementsByTagName("TD")[6];
		     
		      if (parseFloat(x.innerHTML)  < parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortCenaR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[6];
		      y = rows[i + 1].getElementsByTagName("TD")[6];
		     
		      if (parseFloat(x.innerHTML)  > parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortIdO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[0];
		      y = rows[i + 1].getElementsByTagName("TD")[0];
		     
		      if (parseFloat(x.innerHTML)  < parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortIdR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[0];
		      y = rows[i + 1].getElementsByTagName("TD")[0];
		     
		      if (parseFloat(x.innerHTML)  > parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortBsO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[2];
		      y = rows[i + 1].getElementsByTagName("TD")[2];
		     
		      if (parseFloat(x.innerHTML)  < parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortBsR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[2];
		      y = rows[i + 1].getElementsByTagName("TD")[2];
		     
		      if (parseFloat(x.innerHTML)  > parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortBgO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[3];
		      y = rows[i + 1].getElementsByTagName("TD")[3];
		     
		      if (parseFloat(x.innerHTML)  < parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortBgR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[3];
		      y = rows[i + 1].getElementsByTagName("TD")[3];
		     
		      if (parseFloat(x.innerHTML)  > parseFloat(y.innerHTML)) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortTipO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[1];
		      y = rows[i + 1].getElementsByTagName("TD")[1];
		     
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortTipR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[1];
		      y = rows[i + 1].getElementsByTagName("TD")[1];
		     
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortVpO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[7];
		      y = rows[i + 1].getElementsByTagName("TD")[7];
		     
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortVpR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[7];
		      y = rows[i + 1].getElementsByTagName("TD")[7];
		     
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortVoO(){		
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		    
		    switching = false;
		    rows = table.rows;
		   
		    for (i = 1; i < (rows.length - 1); i++) {
		    
		      shouldSwitch = false;
		     
		      x = rows[i].getElementsByTagName("TD")[8];
		      y = rows[i + 1].getElementsByTagName("TD")[8];
		     
		      if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		     
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function sortVoR(){	
		 var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("allApartmentsTable");
		  switching = true;
		  
		  while (switching) {
		   
		    switching = false;
		    rows = table.rows;
		  
		    for (i = 1; i < (rows.length - 1); i++) {
		     
		      shouldSwitch = false;
		      
		      x = rows[i].getElementsByTagName("TD")[8];
		      y = rows[i + 1].getElementsByTagName("TD")[8];
		     
		      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		        
		        shouldSwitch = true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		    
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }	
}
function searchApartments(){
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		let dateFrom = $("input[name=datumDolaska]").val();
		let dateTo = $("input[name=datumOdlaska]").val();
		let place = $("input[name=nazivGrada]").val();
		let priceFrom = $("input[name=cenaOd]").val();
		let priceTo = $("input[name=cenaDo]").val();
		let numberOfRoomsFrom = $("input[name=brojSobaOd]").val();
		let numberOfRoomsTo = $("input[name=brojSobaDo]").val();
		let numberOfGuestsFrom = $("input[name=brojOsobaOd]").val();
		let numberOfGuestsTo = $("input[name=brojOsobaDo]").val();

		var searchFields={
			dateFrom : dateFrom,
			dateTo : dateTo,
			place : place,
			priceFrom : priceFrom,
			priceTo : priceTo,
			numberOfRoomsFrom : numberOfRoomsFrom,
			numberOfRoomsTo : numberOfRoomsTo,
			numberOfGuestsFrom : numberOfGuestsFrom,
			numberOfGuestsTo : numberOfGuestsTo
		}

			var $inputs = $('#searchForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
			$.ajax({
			type : "POST",
			url : "rest/apartments/searchApartments",
			contentType : "application/json",
			data: JSON.stringify({dateFrom: dateFrom, dateTo: dateTo, place: place, priceFrom: priceFrom, priceTo: priceTo, numberOfRoomsFrom: numberOfRoomsFrom, numberOfRoomsTo: numberOfRoomsTo, numberOfGuestsFrom: numberOfGuestsFrom, numberOfGuestsTo: numberOfGuestsTo}),
			success : function(result) {
				var table = $("#allApartmentsTable");
				table.empty();
				if (result == null) {
					$('#allApartmentsTable').hide();
				} else {
					$('#allApartmentsTable').show();
					table.append("<thead><tr><th>Id</th><th>Tip</th><th>Broj soba</th><th>Broj gostiju</th><th>Lokacija</th><th>Domaćin</th><th>Cena po noći</th><th>Vreme za prijavu</th><th>Vreme za odjavu</th><th>Status</th><th>Izmena</th><th>Detaljniji prikaz</th><th>Obriši</th></thead></tr>");
					var body = $("<tbody></tbody>");
					result.forEach(function(item, index) {
						var apartment = $("<tr></tr>");

						apartment.append("<td>" + item["id"] + "</td>");

						var id = item["id"];

						var type = item["type"];
						/*var typeStr = JSON.stringify(type);
						var len = typeStr.length;
						var pos = typeStr.indexOf(":");
						var res = typeStr.substring(pos+2,len-2);
						apartment.append("<td>" + res + "</td>");*/
						apartment.append("<td>" + type + "</td>");

						apartment.append("<td>" + item["numberOfRooms"] + "</td>");
						apartment.append("<td>" + item["numberOfGuests"] + "</td>");

						var numberOfRooms = item["numberOfRooms"];
						var numberOfGuests = item["numberOfGuests"];

						var location = item["location"];	
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

						var resLoc = "Širina: " + res1Loc + ", dužina: " + res2Loc + ", ulica i broj: " + res3Loc + ", mesto: " + res4Loc + ", poštanski broj: " + res5Loc;

						apartment.append("<td>" + resLoc + "</td>");

						/*getAllDates(item["releaseDates"], apartment);
						getAllDates(item["availableDates"], apartment);	*/				

						var host = item["host"];	
						var hostStr = JSON.stringify(host);

						var str = printHost(host);



						apartment.append("<td>" + str + "</td>");


						/*var comments = item["comments"];	
						var commentsStr = JSON.stringify(comments);
						var lenCom = commentsStr.length;
						var posCom = commentsStr.indexOf("text");
						var pos2Com = commentsStr.indexOf("grade");
						var res1Com = commentsStr.substring(posCom+7,pos2Com-3);
						var res2Com = commentsStr.substring(pos2Com+7, lenCom-2);


						var resCom = "Komentar: " + res1Com + ", ocena: " + res2Com;

						apartment.append("<td>" + resCom + "</td>");*/

						apartment.append("<td>" + item["priceForOneNight"] + "</td>");

						var priceForOneNight = item["priceForOneNight"];


						apartment.append("<td>" + item["checkInTime"] + "h" + "</td>");
						apartment.append("<td>" + item["checkOutTime"] + "h" + "</td>");

						var checkInTime = item["checkInTime"];
						var checkOutTime = item["checkOutTime"];

						if(item["status"] == true){
						apartment.append("<td>" + "AKTIVAN" + "</td>");	
						}else{
						apartment.append("<td>" + "NEAKTIVAN" + "</td>");		
						}
						var status = item["status"];	
						
								

						apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editApartment.html?id=" + id + ";numberOfRooms=" + numberOfRooms + ";numberOfGuests=" + numberOfGuests + ";longitude=" + res2Loc + ";latitude=" + res1Loc + ";streetAndNumber=" + res3Loc + ";place=" + res4Loc + ";postcode=" + res5Loc + ";priceForOneNight=" + priceForOneNight + ";checkInTime=" + checkInTime + ";checkOutTime=" + checkOutTime + ";status=" + status + "\'\" value=\"Izmeni\"></td>");

						apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + id + "\'\" value=\"Detaljno\"></td>");
			
						apartment.append("<td><button onclick=\"deleteApartment( " + id + ")\">Obriši</button></td>");
					
						
						body.append(apartment);
					});
					table.append(body);

				}
			}
		});
	});
}

function allApartments() {
	$.ajax({
		type : "GET",
		url : "rest/apartments/getHostActive",
		success : function(result) {
			var table = $("#allApartmentsTable");
			table.empty();
			if (result == null) {
				$('#allApartmentsTable').hide();
			} else {
				$('#allApartmentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Tip</th><th>Broj soba</th><th>Broj gostiju</th><th>Lokacija</th><th>Domaćin</th><th>Cena po noći</th><th>Vreme za prijavu</th><th>Vreme za odjavu</th><th>Status</th><th>Izmena</th><th>Detaljniji prikaz</th><th>Obriši</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var apartment = $("<tr></tr>");

					apartment.append("<td>" + item["id"] + "</td>");
				
					var id = item["id"];
					
					
					var type = item["type"];
					/*var typeStr = JSON.stringify(type);
					var len = typeStr.length;
					var pos = typeStr.indexOf(":");
					var res = typeStr.substring(pos+2,len-2);
					apartment.append("<td>" + res + "</td>");*/
					apartment.append("<td>" + type + "</td>");
					
					apartment.append("<td>" + item["numberOfRooms"] + "</td>");
					apartment.append("<td>" + item["numberOfGuests"] + "</td>");
					
					var numberOfRooms = item["numberOfRooms"];
					var numberOfGuests = item["numberOfGuests"];
						
					var location = item["location"];	
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
					
					var resLoc = "Širina: " + res1Loc + ", dužina: " + res2Loc + ", ulica i broj: " + res3Loc + ", mesto: " + res4Loc + ", poštanski broj: " + res5Loc;
					
					apartment.append("<td>" + resLoc + "</td>");
					
									
					/*getAllDates(item["releaseDates"], apartment);
					
					getAllDates(item["availableDates"], apartment);*/
					
					var host = item["host"];	
					var hostStr = JSON.stringify(host);
					
					var str = printHost(host);
					
					
					
					apartment.append("<td>" + str + "</td>");
					
					
					/*var comments = item["comments"];	
					var commentsStr = JSON.stringify(comments);
					var lenCom = commentsStr.length;
					var posCom = commentsStr.indexOf("text");
					var pos2Com = commentsStr.indexOf("grade");
					var res1Com = commentsStr.substring(posCom+7,pos2Com-3);
					var res2Com = commentsStr.substring(pos2Com+7, lenCom-2);


					var resCom = "Komentar: " + res1Com + ", ocena: " + res2Com;

					apartment.append("<td>" + resCom + "</td>");*/
					
					apartment.append("<td>" + item["priceForOneNight"] + "</td>");
					
					var priceForOneNight = item["priceForOneNight"];
					
					apartment.append("<td>" + item["checkInTime"] + "h" + "</td>");
					apartment.append("<td>" + item["checkOutTime"] + "h" + "</td>");
					
					var checkInTime = item["checkInTime"];
					var checkOutTime = item["checkOutTime"];
					
					
					
					if(item["status"] == true){
						apartment.append("<td>" + "AKTIVAN" + "</td>");	
						}else{
						apartment.append("<td>" + "NEAKTIVAN" + "</td>");		
						}
					
						
									
					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/editApartment.html?id=" + id + ";numberOfRooms=" + numberOfRooms + ";numberOfGuests=" + numberOfGuests + ";longitude=" + res2Loc + ";latitude=" + res1Loc + ";streetAndNumber=" + res3Loc + ";place=" + res4Loc + ";postcode=" + res5Loc + ";priceForOneNight=" + priceForOneNight + ";checkInTime=" + checkInTime + ";checkOutTime=" + checkOutTime + ";status=" + status + "\'\" value=\"Izmeni\"></td>");

					apartment.append("<td><input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + id + "\'\" value=\"Detaljno\"></td>");
					
					apartment.append("<td><button onclick=\"deleteApartment( " + id + ")\">Obriši</button></td>");
					
					body.append(apartment);
				});
				table.append(body);
				
			}
		}
	});
}

function deleteApartment(id){
	$.ajax({
		type : "DELETE",
		url: "rest/apartments/delete",
		contentType : "application/json",
		data: JSON.stringify({idString: id}),
		success: function(data, textStatus, XmlHttpRequest){
					toastr["success"]("Uspešno brisanje!");
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
}

function printHost(obj) {
	var i = 0;
	var str = "";
    for(var k in obj) {
        if(obj[k] instanceof Object) {
            printHost(obj[k]);
        } else {
			i++;
			if (i<=2){
				continue;
			}
			if (i>=4){
				str += obj[k];
				return str;
			}
            str += obj[k];
			str += " ";
        };
    }
};

function printLocation(obj, apartment) {
	var i = 0;
	var strloc = "";
    for(var k in obj) {
        if(obj[k] instanceof Object) {
			apartment.append("<td>" + strloc + "</td>");
            printLocation(obj[k]);
        } else {
			i++;
            strloc += obj[k];
			strloc += ", ";
			if (i==3){
				//apartment.append("<td>" + strloc + "</td>");
				console.log(strloc);
			}
        };
    }
};

function getAllDates(obj, apartment){
	var strJSON = JSON.stringify(obj);
	var strJS = strJSON.substring(1, strJSON.length-1);
	var arrayDates = strJS.split(",");
	var str = "";
	for (var i = 0; i < arrayDates.length; i++) {
		var date = new Date(parseInt(arrayDates[i]));

		var fdate =date.getDate() + '/' + (date.getMonth() + 1) +'/'+date.getFullYear()
		str += fdate;
		if (i<arrayDates.length-1){
			str += ", ";
		}
	}
	apartment.append("<td>" + str + "</td>");
}