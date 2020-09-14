$(document).ready(function() {
	alert("Dosao");
	//alert(allDates[0]); //tu mi izbaci undefinied
  var dateToday = new Date();
  $( function() {
	    $( "#datepicker" ).datepicker({
	    dateFormat: "dd-mm-yy",
	    minDate: "0",
	    beforeShowDay : function (date) {
            var dayOfWeek = date.getDay ();
            // 0 : Sunday, 1 : Monday, ...
            if (dayOfWeek == 0 || dayOfWeek == 6) return [false];
            else return [true];
         }
	    
	    });
	  });
});