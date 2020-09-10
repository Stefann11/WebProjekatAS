$(document).ready(function() {
	$('#registrationForm').submit(function(e) {
		e.preventDefault();
		let username = $("input[name=username]").val();
		let name = $("input[name=name]").val();
		let surname = $("input[name=surname]").val();
		let password = $("input[name=password]").val();
		let repassword = $('#repassword').val();
		let gender = $('input[name=gender]:checked').val();
		
		
		if (password != repassword){
			toastr["error"]("Lozinke moraju biti iste");
			alert("Lozinke moraju biti iste");
			allInputs[4].append("Nisu iste lozinke");
			$(allInputs[4]).css("color", "red");
			event.preventDefault();
			return;
		}
		
			var $inputs = $('#registrationForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/users/save",
				contentType : "application/json",
				data: JSON.stringify({username: username, password: password, name: name, surname: surname, gender: gender}),
				success: function(result) {
					toastr["success"]("Uspešno ste se registrovali!");
					setTimeout(function() {
						location.href = "login.html";
						$('#registrationForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
	
	/*$('#password').focusout(function() {
		//var lozinka = $('#lozinka');
		var password = $('#password');
		var repassword = $('#repassword');
		
		var passwordVal = password.val();
		var repasswordVal = repassword.val();
		
		console.log(passwordVal);
		console.log(repasswordVal);
		
		if ((passwordVal.localeCompare(repasswordVal))!=0){
			toastr["error"]("Lozinke moraju biti iste");
		}
		
	});*/
	
});

/*function validate(forma){
	var passwordEl = document.getElementByName('password')[0];
	var repasswordEl = document.getElementByName('password')[1];
	var password = passwordEl.value;
	var repassword = repasswordEl.value;
	
	if (password!==repassword){
		password.style.backgroud = 'red';
		repassword.style.backgroud = 'red';
		return false;
	}
	return true;
}*/