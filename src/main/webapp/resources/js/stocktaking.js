$( document ).on( "pageinit", "#stocktaking", function() {
	$( "#stocktakingDetail" ).on( "click", function ( e, data ) {
		console.log("Submit Form");
		$("#piDocFrm").submit();
	});
});