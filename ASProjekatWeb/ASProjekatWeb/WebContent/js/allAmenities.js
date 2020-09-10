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
  					r.appendChild(t);
			
					oneItem.append("<td>" + x + r +  "</td>");
					
					body.append(oneItem);
					

					
					node.appendChild(x);
					node.appendChild(r);
					 
					
					
					
					//document.body.appendChild(x);
					//document.body.appendChild(r);

				});
				
				var submitButton = document.createElement("INPUT");
				submitButton.setAttribute("type", "submit");
				submitButton.setAttribute("value", "Posalji");	
				
				node.setAttribute("id", "amenitiesForm");
				node.setAttribute("action", "http://localhost:8080/ASProjekatWeb/listAmenities.html");
			
				node.appendChild(submitButton);
				document.body.appendChild(node);
				//table.append(body);
				
			}
		}
	});
}