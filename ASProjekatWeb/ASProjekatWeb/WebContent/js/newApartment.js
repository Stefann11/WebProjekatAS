$(document).ready(function() {
	createApartment();
	addApartmentToHost();
});

function createApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let numberOfRooms = $("input[name=numberOfRooms]").val();
		let numberOfGuests = $("input[name=numberOfGuests]").val();
		let longitude = $("input[name=longitude]").val();
		let latitude =$("input[name=latitude]").val();
		let streetAndNumber = $("input[name=streetAndNumber]").val();
		let place = $("input[name=place]").val();
		let postcode = $("input[name=postcode]").val();
		let priceForOneNight = $("input[name=priceForOneNight]").val();
		let checkInTime = $("input[name=checkInTime]").val();
		let checkOutTime = $("input[name=checkOutTime]").val();
		let status = $('input[name=status]:checked').val();			
		
		var address = {
			streetAndNumber : streetAndNumber,
			place : place,
			postcode : postcode
		}
		
		var location = {
			longitude : longitude,
			latitude : latitude,
			address : address
		}
		
		if (checkInTime<0 || checkInTime>24){
			toastr["error"]("Check In Time mora biti izmedju 0 i 24");
			alert("Check In Time mora biti izmedju 0 i 24");
			event.preventDefault();
			return;
		}
		
		if (checkOutTime<0 || checkOutTime>24){
			toastr["error"]("Check Out Time mora biti izmedju 0 i 24");
			alert("Check Out Time mora biti izmedju 0 i 24");
			event.preventDefault();
			return;
		}
		
			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/apartments/save",
				contentType : "application/json",
				data: JSON.stringify({id: id, numberOfRooms: numberOfRooms, numberOfGuests: numberOfGuests, location: location, priceForOneNight: priceForOneNight, checkInTime: checkInTime, checkOutTime: checkOutTime, status: status}),
				success: function(result) {
					toastr["success"]("Uspešno kreiranje!");
					setTimeout(function() {
						location.href = "hostIndex.html";
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}

function addApartmentToHost(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let numberOfRooms = $("input[name=numberOfRooms]").val();
		let numberOfGuests = $("input[name=numberOfGuests]").val();
		let longitude = $("input[name=longitude]").val();
		let latitude =$("input[name=latitude]").val();
		let streetAndNumber = $("input[name=streetAndNumber]").val();
		let place = $("input[name=place]").val();
		let postcode = $("input[name=postcode]").val();
		let priceForOneNight = $("input[name=priceForOneNight]").val();
		let checkInTime = $("input[name=checkInTime]").val();
		let checkOutTime = $("input[name=checkOutTime]").val();
		let status = $('input[name=status]:checked').val();			
		
		var address = {
			streetAndNumber : streetAndNumber,
			place : place,
			postcode : postcode
		}
		
		var location = {
			longitude : longitude,
			latitude : latitude,
			address : address
		}
		
		if (checkInTime<0 || checkInTime>24){
			toastr["error"]("Check In Time mora biti izmedju 0 i 24");
			alert("Check In Time mora biti izmedju 0 i 24");
			event.preventDefault();
			return;
		}
		
		if (checkOutTime<0 || checkOutTime>24){
			toastr["error"]("Check Out Time mora biti izmedju 0 i 24");
			alert("Check Out Time mora biti izmedju 0 i 24");
			event.preventDefault();
			return;
		}
		
			var $inputs = $('#apartmentForm input:not([type="submit"])');
			var values = {};
			$inputs.each(function() {
				values[this.name] = $(this).val();
			});
					
			$.ajax({
				type: "POST",
				url: "rest/users/addApartmentToHost",
				contentType : "application/json",
				data: JSON.stringify({id: id, numberOfRooms: numberOfRooms, numberOfGuests: numberOfGuests, location: location, priceForOneNight: priceForOneNight, checkInTime: checkInTime, checkOutTime: checkOutTime, status: status}),
				success: function(result) {
					setTimeout(function() {
						location.href = "hostIndex.html";
						$('#apartmentForm')[0].reset();
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
		});
	});
}