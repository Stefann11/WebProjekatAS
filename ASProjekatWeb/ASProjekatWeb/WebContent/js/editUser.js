$(document).ready(function() {
	editUser();
	editUserInApartment();
	editUserInComment();
});	
function editUser(){
	$('#editUserForm').submit(function(e) {
		e.preventDefault();
		
		let username = $("input[name=username]").val();
		let name = $("input[name=name]").val();
		let surname = $("input[name=surname]").val();
		let password = $("input[name=password]").val();
		let repassword = $('#repassword').val();
		let gender="Male";
		var radios = document.getElementsByName('gender');
		
		var allInputs = document.querySelectorAll("#container_id input[type=text]");
		
		if (password != repassword){
			toastr["error"]("Lozinke moraju biti iste");
			allInputs[4].append("Nisu iste lozinke");
			$(allInputs[4]).css("color", "red");
			event.preventDefault();
			return;
		}
		
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
}

function editUserInApartment(){
	$('#editUserForm').submit(function(e) {
		e.preventDefault();
		
		let username = $("input[name=username]").val();
		let name = $("input[name=name]").val();
		let surname = $("input[name=surname]").val();
		let password = $("input[name=password]").val();
		let repassword = $('#repassword').val();
		let gender="Male";
		var radios = document.getElementsByName('gender');
		
		var allInputs = document.querySelectorAll("#container_id input[type=text]");
		
		if (password != repassword){
			toastr["error"]("Lozinke moraju biti iste");
			allInputs[4].append("Nisu iste lozinke");
			$(allInputs[4]).css("color", "red");
			event.preventDefault();
			return;
		}
		
		var $inputs = $('#editUserForm input:not([type="submit"])');
		var values = {};
		$inputs.each(function() {
			values[this.name] = $(this).val();
		});

		$.ajax({
			type: "PUT",
			url: "rest/apartments/editUserInApartment",
			contentType : "application/json",
			data: JSON.stringify({username: username, password: password, name: name, surname: surname}),
			success: function(data, textStatus, XmlHttpRequest) {					
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
}

function editUserInComment(){
	$('#editUserForm').submit(function(e) {
		e.preventDefault();
		
		let username = $("input[name=username]").val();
		let name = $("input[name=name]").val();
		let surname = $("input[name=surname]").val();
		let password = $("input[name=password]").val();
		let repassword = $('#repassword').val();
		let gender="Male";
		var radios = document.getElementsByName('gender');
		
		var allInputs = document.querySelectorAll("#container_id input[type=text]");
		
		if (password != repassword){
			toastr["error"]("Lozinke moraju biti iste");
			allInputs[4].append("Nisu iste lozinke");
			$(allInputs[4]).css("color", "red");
			event.preventDefault();
			return;
		}
		
		var $inputs = $('#editUserForm input:not([type="submit"])');
		var values = {};
		$inputs.each(function() {
			values[this.name] = $(this).val();
		});

		$.ajax({
			type: "PUT",
			url: "rest/comments/editUserInComment",
			contentType : "application/json",
			data: JSON.stringify({username: username, password: password, name: name, surname: surname}),
			success: function(data, textStatus, XmlHttpRequest) {					
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
}
