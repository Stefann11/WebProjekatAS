$(document).ready(function() {
	allAmenities();
	createAmenities();
});

function createAmenities(){
	$('#amenitiesForm').submit(function(e) {
		e.preventDefault();
		
		alert("dosao");
		
		var amenitiesArray = [];
		$("input:checkbox[name=amenities]:checked").each(function(){
    		amenitiesArray.push($(this).val());
		});
		
		alert(amenitiesArray[0]);
		
			var $inputs = $('#amenitiesForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/users/save",
				contentType : "application/json",
				data: JSON.stringify({username: username, password: password, name: name, surname: surname, gender: gender}),
				success: function(result) {
					toastr["success"]("Uspešno ste se registrovali!");
					setTimeout(function() {
						location.href = "login.html";
						$('#registrationForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}

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
				var node = document.createElement("FORM");
				
				var body = $("<tbody></tbody>");
					
				var g = document.createElement("INPUT");
				g.setAttribute("type", "text");
				g.setAttribute("name", "idApartment");
				g.setAttribute("id", "idApartment");
				g.setAttribute("value", idApartment);
				//g.setAttribute("disabled", true);
				
				var y = document.createElement("LABEL");
				var h = document.createTextNode("Id Apartment");
				y.setAttribute("for", "idApartment");
				y.setAttribute("class", "a1");
				y.appendChild(h);
				
				node.appendChild(y);
				node.appendChild(g);
			
				var br = document.createElement('br');
				
				result.forEach(function(item, index) {
					
					var oneItem = $("<tr></tr>");
					var name = item["name"];
					
					
					
					var x = document.createElement("INPUT");
					x.setAttribute("type", "checkbox");
					x.setAttribute("name", "amenities");
					x.setAttribute("id", name);
					x.setAttribute("value", name);
					
					var r = document.createElement("LABEL");
  					var t = document.createTextNode(name);
  					r.setAttribute("for", name);
					r.setAttribute("class", "a1");
  					r.appendChild(t);
			
					oneItem.append("<td>" + x + r +  "</td>");
					
					body.append(oneItem);
					
					
					
					node.appendChild(br);
					
					node.appendChild(x);
					node.appendChild(r);
					 
					
					
					
					//document.body.appendChild(x);
					//document.body.appendChild(r);

				});
				
				var submitButton = document.createElement("INPUT");
				submitButton.setAttribute("type", "submit");
				submitButton.setAttribute("value", "Posalji");	
				
				node.appendChild(br);
				
				node.setAttribute("id", "amenitiesForm");
				node.setAttribute("action", "http://localhost:8080/ASProjekatWeb/listAmenities.html");
			
				node.appendChild(submitButton);
				
				
				
				document.body.appendChild(node);
				//table.append(body);
				
			}
		}
	});
}