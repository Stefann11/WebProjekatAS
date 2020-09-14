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
					apartmani +="<div class='column'>";
					apartmani +="<div class='card bg-white ' style='max-width: 420px; max-hight: 480px;'><img src='";
					apartmani += item["images"][0];
					apartmani += "' width='350' heigth='350'>";
					apartmani +="<div class='card body'><h5 >";
					apartmani +="<label class = 'a1'> Id: </label>";
					apartmani +="<label class = 'a1'>"+item["id"]+"</label>";
					apartmani +="</h5><label class = 'a1'> Tip: </label>";
					apartmani +="<label class = 'a1'>"+item["type"]+"</label><br>";
					apartmani +="<label class = 'a1'> Broj soba: </label>";
					apartmani +="<label class = 'a1'>"+item["numberOfRooms"]+"</label><br>";
					apartmani +="<input type=button onClick=\"location.href=\'http://localhost:8080/ASProjekatWeb/oneApartman.html?id=" + item["id"] + "\'\" value=\"Detaljno\">";
					apartmani +="</div></div></div>";
					
					});
					$("#prikazi").append(apartmani);
					
				}
			});
}
