$(document).ready(function() {
	$('#commentForm').submit(function(e) {
		e.preventDefault();
			var $inputs = $('#commentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
			
			let id = $("input[name=id]").val();
			let apartment = $("input[name=apartment]").val();
			let text = $("input[name=text]").val();
			let grade = $("input[name=grade]").val();
					
			$.ajax({
				type: "POST",
				url: "rest/comments/save",
				contentType : "application/json",
				data: JSON.stringify({id: id, apartment: apartment, text: text, grade: grade }),
				success: function(result) {
					toastr["success"]("Uspešno kreiranje!");
					setTimeout(function() {
						location.href = "guestIndex.html";
						$('#commentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
});