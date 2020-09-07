$(document).ready(function() {
	allActiveApartments();
});


function allActiveApartments() {
	$.ajax({
		type : "GET",
		url : "rest/apartments/getAllActive",
		success : function(result) {
			var table = $("#allActiveApartmentsTable");
			table.empty();
			if (result == null) {
				$('#allActiveApartmentsTable').hide();
			} else {
				$('#allActiveApartmentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Tip</th><th>Broj soba</th><th>Broj gostiju</th><th>Lokacija</th><th>Datumi za izdavanje</th><th>Dostupnost po datumima</th><th>Domaćin</th><th>Komentari</th><th>Cena po noći</th><th>Vreme za prijavu</th><th>Vreme za odjavu</th><th>Status</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var apartment = $("<tr></tr>");

					apartment.append("<td>" + item["id"] + "</td>");
				
					
					
					var type = item["type"];
					var typeStr = JSON.stringify(type);
					var len = typeStr.length;
					var pos = typeStr.indexOf(":");
					var res = typeStr.substring(pos+2,len-2);
					apartment.append("<td>" + res + "</td>");
					
					apartment.append("<td>" + item["numberOfRooms"] + "</td>");
					apartment.append("<td>" + item["numberOfGuests"] + "</td>");
						
					var location = item["location"];	
					var locationStr = JSON.stringify(location);
					
					//printLocation(location, apartment);
					//console.log(loc);
					
					var lenLoc = locationStr.length;
					
					var posLoc = locationStr.indexOf("longitude");
					var pos2Loc = locationStr.indexOf("latitude");
					var pos3Loc = locationStr.indexOf("streetAndNumber");
					var pos4Loc = locationStr.indexOf("place");
					var pos5Loc = locationStr.indexOf("postcode");
					
					var res1Loc = locationStr.substring(posLoc+11,pos2Loc-2);
					var res2Loc = locationStr.substring(pos2Loc+10, pos3Loc-13);
					var res3Loc = locationStr.substring(pos3Loc+18, pos4Loc-3);
					var res4Loc = locationStr.substring(pos4Loc+8, pos5Loc-3);
					var res5Loc = locationStr.substring(pos5Loc+10, lenLoc-2); 
					
					var resLoc = "Širina: " + res1Loc + ", dužina: " + res2Loc + ", ulica i broj: " + res3Loc + ", mesto: " + res4Loc + ", poštanski broj: " + res5Loc;
					
					apartment.append("<td>" + resLoc + "</td>");
					
					getAllDates(item["releaseDates"], apartment);
					
					getAllDates(item["availableDates"], apartment);
					
					var host = item["host"];	
					var hostStr = JSON.stringify(host);
					
					var str = printHost(host);
					
					
					
					apartment.append("<td>" + str + "</td>");
					
					
					var comments = item["comments"];	
					var commentsStr = JSON.stringify(comments);
					var lenCom = commentsStr.length;
					var posCom = commentsStr.indexOf("text");
					var pos2Com = commentsStr.indexOf("grade");
					var res1Com = commentsStr.substring(posCom+7,pos2Com-3);
					var res2Com = commentsStr.substring(pos2Com+7, lenCom-2);


					var resCom = "Komentar: " + res1Com + ", ocena: " + res2Com;

					apartment.append("<td>" + resCom + "</td>");
					
					apartment.append("<td>" + item["priceForOneNight"] + "</td>");
					
					apartment.append("<td>" + item["checkInTime"] + "h" + "</td>");
					apartment.append("<td>" + item["checkOutTime"] + "h" + "</td>");
					
					apartment.append("<td>" + item["status"] + "</td>");					
					
					//user.append("<td>" + apartmentsForRentStr + "</td>");

					body.append(apartment);
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

function printLocation(obj, apartment) {
	var i = 0;
	var strloc = "";
    for(var k in obj) {
        if(obj[k] instanceof Object) {
			apartment.append("<td>" + strloc + "</td>");
            printLocation(obj[k]);
        } else {
			i++;
            strloc += obj[k];
			strloc += ", ";
			if (i==3){
				//apartment.append("<td>" + strloc + "</td>");
				console.log(strloc);
			}
        };
    }
};

function getAllDates(obj, apartment){
	var strJSON = JSON.stringify(obj);
	var strJS = strJSON.substring(1, strJSON.length-1);
	var arrayDates = strJS.split(",");
	var str = "";
	for (var i = 0; i < arrayDates.length; i++) {
		var date = new Date(parseInt(arrayDates[i]));

		var fdate =date.getDate() + '/' + (date.getMonth() + 1) +'/'+date.getFullYear()
		str += fdate;
		if (i<arrayDates.length-1){
			str += ", ";
		}
	}
	apartment.append("<td>" + str + "</td>");
}
