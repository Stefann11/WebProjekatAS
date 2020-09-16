$(document).ready(function() {
	createApartment();
	addApartmentToHost();
	
	$('#longitude').focusout(function() {
		var longitude = $('#longitude');
		var longitudeVal = longitude.val();
		if (longitudeVal && !(longitudeVal.match('^[0-9]+(?:\.[0-9]+)?$'))) {
			toastr["error"]("Geografsku dužinu morate uneti kao ceo ili decimalan broj veći ili jednak 0.");
			longitude.val("");
		}
	});
	
	$('#latitude').focusout(function() {
		var latitude = $('#latitude');
		var latitudeVal = latitude.val();
		if (latitudeVal && !(latitudeVal.match('^[0-9]+(?:\.[0-9]+)?$'))) {
			toastr["error"]("Geografsku širinu morate uneti kao ceo ili decimalan broj veći ili jednak 0.");
			latitude.val("");
		}
	});
	
	$('#postcode').focusout(function() {
		var postcode = $('#postcode');
		var postcodeVal = postcode.val();
		if (postcodeVal && !(postcodeVal.match('^[0-9]*$'))) {
			toastr["error"]("Poštanski broj morate uneti kao ceo broj.");
			postcode.val("");
		}
	});
	
	$('#priceForOneNight').focusout(function() {
		var priceForOneNight = $('#priceForOneNight');
		var priceForOneNightVal = priceForOneNight.val();
		if (priceForOneNightVal && !(priceForOneNightVal.match('^[0-9]+(?:\.[0-9]+)?$'))) {
			toastr["error"]("Cena za jednu noć morate uneti kao ceo ili decimalan broj veći ili jednak 0.");
			priceForOneNight.val("");
		}
	});
	
	$('#checkInTime').focusout(function() {
		var checkInTime = $('#checkInTime');
		var checkInTimeVal = checkInTime.val();
		if (checkInTimeVal && !(checkInTimeVal.match('^[0-9]*$'))) {
			toastr["error"]("Vreme za prijavu morate uneti kao ceo broj.");
			checkInTime.val("");
		}
	});
	
	$('#checkOutTime').focusout(function() {
		var checkOutTime = $('#checkOutTime');
		var checkOutTimeVal = checkOutTime.val();
		if (checkOutTimeVal && !(checkOutTimeVal.match('^[0-9]*$'))) {
			toastr["error"]("Vreme za odjavu morate uneti kao ceo broj.");
			checkOutTime.val("");
		}
	});
	
});

function createApartment(){
	$('#apartmentForm').submit(function(e) {
		e.preventDefault();
		let id = $("input[name=id]").val();
		let type = $('input[name=type]:checked').val();
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
				data: JSON.stringify({id: id, type: type, numberOfRooms: numberOfRooms, numberOfGuests: numberOfGuests, location: location, priceForOneNight: priceForOneNight, checkInTime: checkInTime, checkOutTime: checkOutTime, status: status}),
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