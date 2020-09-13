$(document).ready(function() {
	createApartment();
});

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

		// month is 0-based, that's why we need dataParts[1] - 1
		var startDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

	
		
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
	});
}