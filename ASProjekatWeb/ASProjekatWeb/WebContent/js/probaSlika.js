$(document).ready(function() {
	proba();
	});
function proba(){
	$('#imageForm').submit(function(e) {
		e.preventDefault();
/*		var totalFiles = document.getElementById('files').files.length;
*/		var files = $("#files")[0].files;
		for (var i = 0; i < files.length; i++)
		{
		var imgStr = "images/" + files[i].name;
 		alert(imgStr);
		}
		/*var totalFiles2 = $("input[name=files]").val();
		console.log(totalFiles2);
		alert(JSON.stringify(totalFiles2));
		for(var i=0; i< totalFiles; i++){
			
			alert(JSON.stringify(document.getElementById('files').files[i]));
		}
		let username = $("input[name=id]").val();
		var files = $("input[name=img]").val();
		var filesStr = JSON.stringify(files);
		alert(filesStr);
		var pos1 = filesStr.lastIndexOf("\\");
		var fileStr19 = filesStr.substring(pos1+1, filesStr.length-1);
		
		
		
		var imgStr = "images/" + fileStr19;
		alert(imgStr);*/
		
		
		
	/*	if (password != repassword){
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
				success: function(data, textStatus, XmlHttpRequest) {					
				toastr["success"]("Uspešno ste se registrovali!");
				setTimeout(function() {
					window.location.assign( XmlHttpRequest.responseText);
					$('#registrationForm')[0].reset();
				}, 1000);
			},
			error: function(jqXHR, textStatus, errorThrown)  {		    		
				toastr["error"](jqXHR.responseText);
			}
		});*/
	});
	
}
