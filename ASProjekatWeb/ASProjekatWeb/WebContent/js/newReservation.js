$(document).ready(function() {
	createApartment();
	addDatesToApartment();
});s




function createApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let idApartment = $("input[name=idApartment]").val();
		let startDateString = $("input[name=datepicker]").val();
		let numberOfOvernights = $("input[name=numberOfOvernights]").val();
		let message = $("input[name=message]").val();		
		
		var apartment = {
			id: idApartment
		}
		
		var dateParts = startDateString.split("-");

		
		var startDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
		
		/*var flag = 0;
		
		for (var i=0; i<listDates.length;i++){
			var dateParts2 = listDates[i].split("-");
			var thisDate = new Date(+dateParts2[2], dateParts2[1] - 1, +dateParts2[0]);
			
			if (thisDate.getTime()===startDate.getTime()){
				flag = 1;
				break;
			} 
		}
		
		if (flag == 0){*/

		
				var $inputs = $('#apartmentForm input:not([type="submit"])');
				var values = {};
				$inputs.each(function() {
					values[this.name] = $(this).val();
				});
						
				$.ajax({
					type: "POST",
					url: "rest/reservations/save",
					contentType : "application/json",
					accept: "application/json",
					data: JSON.stringify({id: id, apartment: apartment, startDate: startDate, numberOfOvernights: numberOfOvernights, message: message}),
					success: function(data, textStatus, XmlHttpRequest){
						toastr["success"]("Uspešno kreiranje!");
						setTimeout(function() {
							window.location.assign( XmlHttpRequest.responseText);
							$('#apartmentForm')[0].reset();
						}, 1000);
					},
					error: function(jqXHR, textStatus, errorThrown)  {
						toastr["error"](jqXHR.responseText);
					}
		});
		/*} else {
			alert("Zauzeti datumi");
		}*/
	});
}

function addDatesToApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let idApartment = $("input[name=idApartment]").val();
		let startDateString = $("input[name=datepicker]").val();
		let numberOfOvernights = $("input[name=numberOfOvernights]").val();
		let message = $("input[name=message]").val();		
		
		var apartment = {
			id: idApartment
		}
		
		var dateParts = startDateString.split("-");

		
		var startDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/apartments/addDatesToApartment",
				contentType : "application/json",
				accept: "application/json",
				data: JSON.stringify({id: id, apartment: apartment, startDate: startDate, numberOfOvernights: numberOfOvernights, message: message}),
				success: function(data, textStatus, XmlHttpRequest){
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}