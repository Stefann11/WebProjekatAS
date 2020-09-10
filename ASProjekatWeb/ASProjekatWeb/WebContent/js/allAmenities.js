$(document).ready(function() {
	allAmenities();
});

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
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					
					var oneItem = $("<tr></tr>");
					var name = item["name"];
					
					var x = document.createElement("INPUT");
					x.setAttribute("type", "checkbox");
					x.setAttribute("name", name);
					x.setAttribute("id", name);
					x.setAttribute("value", name);
					
					var r = document.createElement("LABEL");
  					var t = document.createTextNode(name);
  					r.setAttribute("for", name);
  					r.appendChild(t);
			
					oneItem.append("<td>" + x.value +  "</td>");
					
					body.append(oneItem);
					
					//document.body.appendChild(x);
					//document.body.appendChild(r)
				});
				table.append(body);
				
			}
		}
	});
}