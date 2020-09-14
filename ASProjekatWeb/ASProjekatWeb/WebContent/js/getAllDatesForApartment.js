var listDates = [];

$(document).ready(function() {
	getAllDatesForApartment(idOfApartment);
});

function callback(result) {
  result.forEach(function(item, index) {
							
							listDates.push(item);
							});
							initDatepicker();
}


function getAllDatesForApartment(idApartment){
		$.ajax({
					type: "POST",
					url: "rest/apartments/getAllDatesForApartment",
					contentType : "application/json",
					data: JSON.stringify({id: idApartment}),
					success : function(result) {
						callback(result);
					
					},
					error: function(jqXHR, textStatus, errorThrown)  {
						toastr["error"](jqXHR.responseText);

					}
			});
						
}
	
function initDatepicker(){
  var dateToday = new Date();
  $( function() {
	    $( "#datepicker" ).datepicker({
	    dateFormat: "dd-mm-yy",
	    minDate: "0",
	    beforeShowDay : function (date) {
      
            var string = jQuery.datepicker.formatDate('dd-mm-yy', date);
        	return [ listDates.indexOf(string) == -1 ]
			
         
         
		}
	    
	    });
	  });
}