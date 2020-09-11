window.onload = function () {
	var url = document.location.href;
	var strings = url.split("?");

	var sepStrings = strings[1].split(";");

	var sepIds = sepStrings[0].split("=");
	var id = sepIds[1];
	
	var sepNames = sepStrings[1].split("=");
	var name = sepNames[1];

	
	document.getElementById('id').value = id;
    document.getElementById('name').value = name;
	

} 