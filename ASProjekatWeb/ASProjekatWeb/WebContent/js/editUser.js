$(document).ready(function() {
	$('#editUserForm').submit(function(e) {
		e.preventDefault();
		var $inputs = $('#editUserForm input:not([type="submit"])');
		var values = {};
		$inputs.each(function() {
			values[this.name] = $(this).val();
		});

		$.ajax({
			type: "PUT",
			url: "rest/users/edit",
			contentType : "application/json",
			data: JSON.stringify(values),
			success: function(data, textStatus, XmlHttpRequest) {					
				toastr["success"]("Uspešno ste promenili korisnika!");
				setTimeout(function() {
					location.href = XmlHttpRequest.responseText;
					$('#editUserForm')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown)  {		    		
				toastr["error"](jqXHR.responseText);
			}
		});
	});
});