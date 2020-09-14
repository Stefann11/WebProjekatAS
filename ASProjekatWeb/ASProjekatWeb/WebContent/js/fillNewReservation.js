var idOfApartment = 0;

window.onload = function () {
	var url = document.location.href;
	var strings = url.split("?");

	var sepStrings = strings[1].split(";");

	var sepIds = sepStrings[0].split("=");
	var id = sepIds[1];
	
	idOfApartment = id;


    document.getElementById('idApartment').value = id;

}

