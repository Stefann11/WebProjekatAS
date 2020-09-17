$(document).ready(function() {
	$('#hostRegistrationForm').submit(function(e) {
		e.preventDefault();
		let username = $("input[name=username]").val();
		let name = $("input[name=name]").val();
		let surname = $("input[name=surname]").val();
		let password = $("input[name=password]").val();
		let repassword = $('#repassword').val();
		let gender = $('input[name=gender]:checked').val();
		let role =  "HOST";
		
		if (password != repassword){
			toastr["error"]("Lozinke moraju biti iste");
			allInputs[4].append("Nisu iste lozinke");
			$(allInputs[4]).css("color", "red");
			event.preventDefault();
			return;
		}
		
			var $inputs = $('#hostRegistrationForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/users/saveHost",
				contentType : "application/json",
				data: JSON.stringify({username: username, password: password, name: name, surname: surname, gender: gender, role: role}),
				success: function(data, textStatus, XmlHttpRequest) {					
				toastr["success"]("Uspešno ste napravili host-a!");
				setTimeout(function() {
					window.location.assign( XmlHttpRequest.responseText);
					$('#hostRegistrationForm')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown)  {		    		
				toastr["error"](jqXHR.responseText);
			}
		});
	});
});