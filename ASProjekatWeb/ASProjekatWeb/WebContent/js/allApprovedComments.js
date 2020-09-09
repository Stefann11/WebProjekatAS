$(document).ready(function() {
	allComments();
});


function allComments() {
	$.ajax({
		type : "GET",
		url : "rest/comments/allApproved",
		success : function(result) {
			var table = $("#allApprovedCommentsTable");
			table.empty();
			if (result == null) {
				$('#allApprovedCommentsTable').hide();
			} else {
				$('#allApprovedCommentsTable').show();
				table.append("<thead><tr><th>Id</th><th>Gost</th><th>Apartman</th><th>Sadržaj</th><th>Ocena</th></thead></tr>");
				var body = $("<tbody></tbody>");
				result.forEach(function(item, index) {
					var comment = $("<tr></tr>");

					comment.append("<td>" + item["id"] + "</td>");			
					
					var guest = item["guest"];	
					var guestStr = JSON.stringify(guest);
					
					var str = printHost(guest);
					
					
					
					comment.append("<td>" + str + "</td>");
					
					var apartment = item["apartment"];
					var apartmentStr = JSON.stringify(apartment);
					
					var pos = apartmentStr.indexOf("type");
					
					var res = apartmentStr.substring(6, pos-2);
					
					var str = "Id apartmana: " + res;
					
					comment.append("<td>" + str + "</td>");
					
					comment.append("<td>" + item["text"] + "</td>");
					
					comment.append("<td>" + item["grade"] + "</td>");

					body.append(comment);
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