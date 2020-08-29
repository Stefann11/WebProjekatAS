$(document).ready(function() {
	reservations();
});


function reservations() {
	$.ajax({
		type : "GET",
		url : "rest/reservations/",
		success : function(result) {
			var table = $("#reservationsTable");
			table.empty();
			if (result == null) {
				$('#reservationsTable').hide();
			} else {
				$('#reservationsTable').show();
				table.append("<thead><tr><th>Id</th><th>Apartman</th><th>Početni datum</th><th>Broj noćenja</th><th>Cena</th><th>Poruka</th><th>Gost</th><th>Status</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var reservation = $("<tr></tr>");

					reservation.append("<td>" + item["id"] + "</td>");
					
					var apartment = item["apartment"];
					var apartmentStr = JSON.stringify(apartment);
					
					var pos = apartmentStr.indexOf("type");
					
					var res = apartmentStr.substring(6, pos-2);
					
					var str = "Id apartmana: " + res;
					
					reservation.append("<td>" + str + "</td>");
					
					var datum = item["startDate"];
					var datumStr = JSON.stringify(datum);
					
					var brNoci = item["numberOfOvernights"]
					
					var cena = item["totalPrice"];
					
					var poruka = item["message"];
					
					var guest = item["guest"];	
					var guestStr = JSON.stringify(guest);
					
					var str2 = printHost(guest);
					
					var stat = item["status"];
					var status = JSON.stringify(stat);
					
					reservation.append("<td>" + datumStr + "</td>" + "<td>" + brNoci + "</td>" + "<td>" +  cena  + "</td>" + "<td>" + poruka + "</td>" + "<td>" + str2 + "</td>" + "<td>" + status + "</td>");
					
					body.append(reservation);
					});
				table.append(body);
				
			}
		}
	});
}
					
function printHost(obj) {
	var i = 0;
	var str = "";
    for(var k in obj) {
        if(obj[k] instanceof Object) {
            printHost(obj[k]);
        } else {
			i++;
			if (i<=2){
				continue;
			}
			if (i>=4){
				str += obj[k];
				return str;
			}
            str += obj[k];
			str += " ";
        };
    }
};
				
function getArray(parsedData){
	var arr = [];

	for(item in parsedData) {
    arr.push({
        "id": parsedData[item],
        "value": parsedData[item]
    });
};

console.log(arr);					


}