$(document).ready(function() {
	$('#amenitiesForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let name = $("input[name=name]").val();
			
			var $inputs = $('#amenitiesForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/amenties/save",
				contentType : "application/json",
				data: JSON.stringify({id: id, name: name}),
				success: function(data, textStatus, XmlHttpRequest) {					
				toastr["success"]("Uspešno ste dodali novi sadržaj!");
				setTimeout(function() {
					window.location.assign( XmlHttpRequest.responseText);
					$('#amenitiesForm')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown)  {		    		
				toastr["error"](jqXHR.responseText);
			}
		});
	});
	
	
});
