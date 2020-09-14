$(document).ready(function() {
	createApartment();
	addDatesToApartment();
});


/*availableDates = ['24-09-2020','25-09-2020'];

function initDatePicker(){
	$("#datePicker").datepicker({
    beforeShowDay: function(d) {
        var dmy = (d.getMonth()+1); 
        if(d.getMonth()<9) 
            dmy="0"+dmy; 
        dmy+= "-"; 
        
        if(d.getDate()<10) dmy+="0"; 
            dmy+=d.getDate() + "-" + d.getFullYear(); 
        
        console.log(dmy+' : '+($.inArray(dmy, availableDates)));
        
        if ($.inArray(dmy, availableDates) != -1) {
            return [true, "","Available"]; 
        } else{
             return [false,"","unAvailable"]; 
        }
    }
    });

//$("#dateRetrait").datepicker({buttonImage: "../../../Images/boutons/btn_calendier.png"});
//$("#dateRetrait").datepicker({showButtonPanel: true });
//$("#dateRetrait").datepicker({beforeShow: function() {setTimeout(function() {$(".ui-datepicker").css("z-index", 9999999999);}, 10);}});

}*/





function createApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let idApartment = $("input[name=idApartment]").val();
		let startDateString = $("input[name=datepicker]").val();
		let numberOfOvernights = $("input[name=numberOfOvernights]").val();
		let message = $("input[name=message]").val();		
		
		var apartment = {
			id: idApartment
		}
		
		var dateParts = startDateString.split("-");

		
		var startDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

		
			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/reservations/save",
				contentType : "application/json",
				accept: "application/json",
				data: JSON.stringify({id: id, apartment: apartment, startDate: startDate, numberOfOvernights: numberOfOvernights, message: message}),
				success: function(data, textStatus, XmlHttpRequest){
					toastr["success"]("Uspešno kreiranje!");
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}

function addDatesToApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let idApartment = $("input[name=idApartment]").val();
		let startDateString = $("input[name=datepicker]").val();
		let numberOfOvernights = $("input[name=numberOfOvernights]").val();
		let message = $("input[name=message]").val();		
		
		var apartment = {
			id: idApartment
		}
		
		var dateParts = startDateString.split("-");

		
		var startDate = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 

			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/apartments/addDatesToApartment",
				contentType : "application/json",
				accept: "application/json",
				data: JSON.stringify({id: id, apartment: apartment, startDate: startDate, numberOfOvernights: numberOfOvernights, message: message}),
				success: function(data, textStatus, XmlHttpRequest){
					setTimeout(function() {
						window.location.assign( XmlHttpRequest.responseText);
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}