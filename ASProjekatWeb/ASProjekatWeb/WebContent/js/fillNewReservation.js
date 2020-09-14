var allDates;

window.onload = function () {
	var url = document.location.href;
	var strings = url.split("?");

	var sepStrings = strings[1].split(";");

	var sepIds = sepStrings[0].split("=");
	var id = sepIds[1];


    document.getElementById('idApartment').value = id;

	
	allDates = getAllDatesForApartment(id);
	
	alert("All dates je" + allDates[0]);

}

function getAllDatesForApartment(idApartment){
		var datesToReturn = [];
		var idApartment = $("input[name=idApartment]").val();
		$.ajax({
					type: "POST",
					url: "rest/apartments/getAllDatesForApartment",
					contentType : "application/json",
					data: JSON.stringify({id: idApartment}),
					success : function(result) {
					var table = $("#allApartmentsTable");
					table.empty();
					if (result == null) {
						
					} else {
						result.forEach(function(item, index) {
							alert(item);
							datesToReturn.push(item);
							alert("Date je: " + datesToReturn[0]);
							});
						}
					},
					error: function(jqXHR, textStatus, errorThrown)  {
						toastr["error"](jqXHR.responseText);
					}
			});
			
			return datesToReturn;
	}