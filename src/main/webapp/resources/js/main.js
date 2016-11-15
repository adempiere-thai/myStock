$(function() {
	/* sales-order.php */
	
	$('#sales-order-list').on('click', '.sales-order-item', function(event) {
		$('#sales-order-list .sales-order-item').removeClass('focus');
		$(this).addClass('focus');
	});
	
	$('#sales-order-list').on('click', '.delete-btn', function(event) {
		$('#deleteDialog').popup('open');
	});
	
	/* items-in-order.php */
	
	$('#items-in-order-list').on('click', '.items-in-order-item', function(event) {
		$('#items-in-order-list .items-in-order-item').removeClass('focus');
		$(this).addClass('focus');
	});
	
	$('#items-in-order-list').on('click', '.edit-price-btn', function(event) {
		$('#editPriceDialog').popup('open');
	});
	
	$('#items-in-order-list').on('click', '.delete-btn', function(event) {
		$('#deleteDialog').popup('open');
	});
});