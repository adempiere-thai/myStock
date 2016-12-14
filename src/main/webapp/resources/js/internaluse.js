$( document ).on( "pageinit", "#internaluse", function() {
	$( "#internaluseDetail" ).on( "click", function ( e, data ) {
		console.log("Submit Form");
		$("#reasonFrm").submit();
	});
});