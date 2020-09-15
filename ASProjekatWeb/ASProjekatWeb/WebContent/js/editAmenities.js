$(document).ready(function() {
	editAmenities();
	editAmenitiesInApartment();
});

function editAmenities(){
	$('#amenitiesForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();	
		let name = $("input[name=name]").val();	
		
			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "PUT",
				url: "rest/amenties/edit",
				contentType : "application/json",
				data: JSON.stringify({id: id, name: name}),
				success: function(result) {
					toastr["success"]("Uspešno ste izmenili sadržaj!");
					setTimeout(function() {
						location.href = "tableAmenities.html";
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}

function editAmenitiesInApartment(){
	$('#amenitiesForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();	
		let name = $("input[name=name]").val();	
		
			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/apartments/editAmenitie",
				contentType : "application/json",
				data: JSON.stringify({id: id, name: name}),
				success: function(result) {
					toastr["success"]("Uspešno ste izmenili sadržaj!");
					setTimeout(function() {
						location.href = "tableAmenities.html";
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}