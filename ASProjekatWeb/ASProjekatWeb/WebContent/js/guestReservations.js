$(document).ready(function() {
	allReservations();
});

function allReservations() {
	$.ajax({
		type : "GET",
		url : "rest/reservations/userReservations",
		success : function(result) {
			var table = $("#allApartmentsTable");
			table.empty();
			if (result == null) {
				$('#allApartmentsTable').hide();
			} else {
				$('#allApartmentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Apartman</th><th>Početni datum</th><th>Broj noćenja</th><th>Ukupna cena</th><th>Poruka</th><th>Gost</th><th>Status</th><th>Odustanak</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var apartment = $("<tr></tr>");

					apartment.append("<td>" + item["id"] + "</td>");
				
					var id = item["id"];
					
					var apartment2 = item["apartment"];
					var apartmentStr = JSON.stringify(apartment2);
					
					var pos = apartmentStr.indexOf("type");
					
					var res = apartmentStr.substring(6, pos-2);
					
					var str = "Id apartmana: " + res;
					
					apartment.append("<td>" + str + "</td>");
					
					getOneDate(item["startDate"], apartment);	
					
					apartment.append("<td>" + item["numberOfOvernights"] + "</td>");
					
					apartment.append("<td>" + item["totalPrice"] + "</td>");
					
					apartment.append("<td>" + item["message"] + "</td>");							
					
					var host = item["guest"];	
					var hostStr = JSON.stringify(host);
					
					var prnt = printHost(host);
								
					apartment.append("<td>" + prnt + "</td>");
					
					
					apartment.append("<td>" + item["status"] + "</td>");	
					
					var status = item["status"];
					
					if (status == "CREATED" || status == "ACCEPTED"){
						apartment.append("<td><button onclick=\"whithdrawal( " + id + ")\">Odustani</button></td>");
					} else {
						apartment.append("<td></td>");
					}
					
					body.append(apartment);
				});
				table.append(body);
				
			}
		}
	});
}

function whithdrawal(id){
	$.ajax({
		type : "POST",
		url: "rest/reservations/whithdrawal",
		contentType : "application/json",
		data: JSON.stringify({id: id}),
		success: function(result) {
					toastr["success"]("Uspešno ste izmenili rezervaciju!");
					setTimeout(function() {
						location.href = "guestReservations.html";
					}, 1000);
				},
				error: function(jqXHR, textStatus, errorThrown)  {
					toastr["error"](jqXHR.responseText);
				}
	});
}

function getOneDate(obj, apartment){
	var strJSON = JSON.stringify(obj);

	var str = "";
	
	var date = new Date(parseInt(strJSON));

	var fdate =date.getDate() + '/' + (date.getMonth() + 1) +'/'+date.getFullYear()
	str += fdate;
	
		
	
	apartment.append("<td>" + str + "</td>");
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