$(document).ready(function() {
	$("#backPage").click(function(){
		var url = "internaluse2.html";
		$( location ).attr("href", url);
	});
	
	$("#submitBtn").click(function(){
		var url = "internaluse2-1.html";
		$( location ).attr("href", url);
	});
	
	$("#pdCode").on("input", function(e) {
		var schTxt = $(this).val();

		if(schTxt == "NOP3") {
			$("#productName").html(" เหล็กรองน็อต-ชุบดีบุก")
			$("#attrSetId").focus();
		}
		else if(schTxt == "NPF1") {
			$("#productName").html(" สกรู Tapper P+4x1/4 แหลม N")
			$("#attrSetId").focus();
		}
		else if(schTxt == "NRM1") {
			$("#productName").html(" สกรู Tapper P+ 2.6x10 แหลม N")
			$("#attrSetId").focus();
		}
		else {
			$("#productName").html("");
		}
	});
	
	$("#attrSetId").on("input", function(e) {
		var schTxt = $(this).val();

		if(schTxt == "100001") {
			$("#attrSet").html("[V-11051] NOP3:SKP-Brass 1 ทองเหลือง-งอ 90 องศา (0.8mm.)")
			$("#countQty").focus();
		}
		else if(schTxt == "100002") {
			$("#attrSet").html("([V-9763/1] NOP3:MEE-ฉากขันฝา PHASE RoHS)")
			$("#countQty").focus();
		}
		else if(schTxt == "100003") {
			$("#attrSet").html("([V-10323] NOP3:SKP-Brass 1 ทองเหลือง-งอ 90 องศา (0.8mm.)")
			$("#countQty").focus();
		}
		else {
			$("#attrSet").html("");
		}
	});
	
	
 });
 