$( document ).on( "pageinit", "#stocktaking-input", function() {
	console.log("Page Init Input Stocktaking");
	$("#pdCode").on("input", function(e) {
		var schTxt = $(this).val();
		console.log("schTxt "+schTxt);
		
		var url = '/myStock/findProductByKey';
		var params = {srhKey: schTxt};
		
		$.getJSON(url, params, function (data) {
			console.log(data);
			if(data != null ){
				console.log("Product Id "+data.productId);
				console.log("Product Key "+data.productSrhKey);
				console.log("Product name "+data.productName);
				
				$("#productName").html(data.productName);
				$("#productId").value(data.productId);
				$("#asiId").focus();
			}
			else{
				$("#productName").html("");
				$("#productId").value("");
			}
		});
	});
	
	$("#asiId").on("input", function(e) {
		var schTxt = $(this).val();
		
		var url = '/myStock/findASIById';
		var params = {asiId: schTxt};
		
		$.getJSON(url, params, function (data) {
			console.log(data);
			if(data != null ){
				console.log("ASI Description "+data.description);
				
				$("#asiDesc").html(data.description);
				$("#countQty").focus();
			}
			else{
				$("#asiDesc").html("");
			}
		});
	});
});