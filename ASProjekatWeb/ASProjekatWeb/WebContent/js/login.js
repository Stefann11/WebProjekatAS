$(document).ready(function() {
	$('#loginForm').submit(function(e) {
		e.preventDefault();
		var $inputs = $('#loginForm input:not([type="submit"])');
		var values = {};
		$inputs.each(function() {
			values[this.name] = $(this).val();
		});

		$.ajax({
			type: "POST",
			url: "rest/users/login",
			contentType : "application/json",
			data: JSON.stringify(values),
			success: function(data, textStatus, XmlHttpRequest) {					
				toastr["success"]("Uspešno ste se prijavili!");
				setTimeout(function() {
					location.href = XmlHttpRequest.responseText;
					$('#loginForm')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown)  {		    		
				toastr["error"](jqXHR.responseText);
			}
		});
	});
});