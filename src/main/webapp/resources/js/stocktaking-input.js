$( document ).on( "pageinit", "#stocktaking-input", function() {
	console.log("Page Init Input Stocktaking");
	$("#pdCode").on("input", function(e) {
		var schTxt = $(this).val();
		console.log("schTxt "+schTxt);
		
		var url = contextPath+'/findProductByKey';
		var params = {srhKey: schTxt};
		
		$.getJSON(url, params, function (data) {
			console.log(data);
			if(data != null ){
				console.log("Product Id "+data.productId);
				console.log("Product Key "+data.productSrhKey);
				console.log("Product name "+data.productName);
				
				$("#productNameTxt").html(data.productName);
				$("#productId").val(data.productId);
				$("#productName").val(data.productName);
				$("#asiId").focus();
			}
			else{
				$("#productNameTxt").html("");
				$("#productName").val("");
				$("#productId").val("");
			}
		});
	});
	
	$("#asiId").on("input", function(e) {
		var schTxt = $(this).val();
		
		var url = contextPath+'/findASIById';
		var params = {asiId: schTxt};
		
		$.getJSON(url, params, function (data) {
			console.log(data);
			if(data != null ){
				console.log("ASI Description "+data.description);
				
				$("#asiDescTxt").html(data.description);
				$("#asiDesc").val(data.description);
				$("#countQty").focus();
			}
			else{
				$("#asiDescTxt").html("");
				$("#asiDesc").val("");
			}
		});
	});
	
	$("#saveBtn").on("click", function(e) {
		$("#action").val("save");
		/*alert($("#asiId").val());
		alert($("#productId").val());*/
		$("#piDocLineFrm").submit();
	});
	
	$("#saveNextBtn").on("click", function(e) {
		$("#action").val("save.next");
		$("#piDocLineFrm").submit();
	});
});