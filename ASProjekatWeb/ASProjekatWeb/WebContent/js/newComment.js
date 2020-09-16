$(document).ready(function() {
	createComment();
	
	$('#id').focusout(function() {
		var id = $('#id');
		var idVal = id.val();
		if (idVal && !(idVal.match('^[0-9]*$'))) {
			toastr["error"]("Id morate uneti kao ceo broj.");
			id.val("");
		}
	});
	
	$('#grade').focusout(function() {
		var grade = $('#grade');
		var gradeVal = grade.val();
		if (gradeVal && !(gradeVal.match('^[0-9]*$'))) {
			toastr["error"]("Ocenu morate uneti kao ceo broj.");
			grade.val("");
		}else if (gradeVal<1 || gradeVal>10){
			toastr["error"]("Ocena mora biti između 1 i 10.");
			grade.val("");
		}
	});
	
});
function createComment(){
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
				success: function(data, textStatus, XmlHttpRequest){
					toastr["success"]("Uspešno kreiranje!");
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
						$('#commentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}

/*function editApartment(){
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
				type: "PUT",
				url: "rest/apartments/editCommentInApartment",
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
	
}*/