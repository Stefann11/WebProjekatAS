window.onload = function () {
	var url = document.location.href;
	var strings = url.split("?");

	var sepStrings = strings[1].split(";");

	var sepIds = sepStrings[0].split("=");
	var id = sepIds[1];

	var sepnumberOfRooms = sepStrings[1].split("=");
	var numberOfRooms = sepnumberOfRooms[1];

	var sepnumberOfGuests = sepStrings[2].split("=");
	var numberOfGuests = sepnumberOfGuests[1];

	var sepLongitude = sepStrings[3].split("=");
	var longitude = sepLongitude[1];

	var sepLatitude = sepStrings[4].split("=");
	var latitude = sepLatitude[1];

	var sepstreetAndNumber = sepStrings[5].split("=");
	var streetAndNumber = sepstreetAndNumber[1];

	var sepplace = sepStrings[6].split("=");
	var place = sepplace[1];

	var seppostcode = sepStrings[7].split("=");
	var postcode = seppostcode[1];

	var seppriceForOneNight = sepStrings[8].split("=");
	var priceForOneNight = seppriceForOneNight[1];

	var sepcheckInTime = sepStrings[9].split("=");
	var checkInTime = sepcheckInTime[1];

	var sepsepcheckOutTime = sepStrings[10].split("=");
	var checkOutTime = sepsepcheckOutTime[1];

	var sepstatus = sepStrings[11].split("=");
	var status = sepstatus[1];

    document.getElementById('id').value = id;
	document.getElementById('numberOfRooms').value = numberOfRooms;
	document.getElementById('numberOfGuests').value = numberOfGuests;
	document.getElementById('longitude').value = longitude;
	document.getElementById('latitude').value = latitude;
	document.getElementById('streetAndNumber').value = streetAndNumber;
	document.getElementById('place').value = place;
	document.getElementById('postcode').value = postcode;
	document.getElementById('priceForOneNight').value = priceForOneNight;
	document.getElementById('checkInTime').value = checkInTime;
	document.getElementById('checkOutTime').value = checkOutTime;

} 