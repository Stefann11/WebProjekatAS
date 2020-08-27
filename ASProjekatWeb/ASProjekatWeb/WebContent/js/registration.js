$(document).ready(function() {
	$('#registrationForm').submit(function(e) {
		e.preventDefault();
			var $inputs = $('#registrationForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/users/save",
				contentType : "application/json",
				data: JSON.stringify(values),
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
	
	$('#password').focusout(function() {
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
		
		/*var lozinkaVal = lozinka.val();
		if(!lozinkaVal) {
			toastr["error"]("Morate uneti lozinku.");
		} else if (!lozinkaVal.match("^[a-zA-Z0-9]+$")) {
			toastr["error"]("Lozinka može sadržati samo slova i brojeve.");
			lozinka.val("");
		} else if(lozinkaVal.length < 4) {
			toastr["error"]("Lozinka mora sadržati minimalno 4 karaktera.");
		}	*/	
	});
	
});

function validate(forma){
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
}