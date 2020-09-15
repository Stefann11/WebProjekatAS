$(document).ready(function() {
	prikazApartmana();
});
function prikazApartmana(){
	$.ajax({
				type: "GET",
				url: "rest/apartments/",
				contentType : "application/json",
				success: function(result){
					var apartmani = "";
					result.forEach(function(item, index) {						
					apartmani +="<div class='poCetri'>";
					apartmani +="<div class='kartica ' style='max-width: 550px; max-hight: 500px;'><img src='";
					apartmani += item["images"][0];
					apartmani += "' width='250' heigth='250'>";
					apartmani +="<div class='kartica'>";
					apartmani +="<label class = 'a1'> Id: </label>";
					apartmani +="<label class = 'a1'>"+item["id"]+"</label><br><br>";					
					
					
					var location = item["location"];	
						var locationStr = JSON.stringify(location);

						

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

						var resLoc = res3Loc + ", " + res4Loc;
	
						
					
					/*apartmani +="<label class = 'a1'>"+res3Loc+","+"</label>";
					*/
					apartmani +="<label class = 'a1'>"+resLoc+"</label><br><br>";
					apartmani +="<label class = 'a1'>Cena: </label>";
					apartmani +="<label class = 'a1'>"+item["priceForOneNight"]+"</label><br>";
					apartmani +="<input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + item["id"] + "\'\" value=\"Detaljno\">";
					apartmani +="</div></div></div>";
					
					});
					$("#prikazi").append(apartmani);
					
				}
			});
}