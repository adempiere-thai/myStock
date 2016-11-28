$( document ).on( "pageinit", "#stocktaking-detail", function() {
	
	$('#items-list').on('click', '.items-in-order-item', function(event) {
		$('#items-list .items-in-order-item').removeClass('focus');
		$(this).addClass('focus');
	});
	
	$('#items-list').on('click', '.edit-price-btn', function(event) {
		$('#editQty').popup('open');
	});
	
	$('#items-list').on('click', '.delete-btn', function(event) {
		$('#deleteDialog').popup('open');
	});
	
 });
 
 