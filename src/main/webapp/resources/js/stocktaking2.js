$(document).ready(function() {
	
	// Navigator Btn Handler
	$("#prevStep").click(function(){
		var url = "stocktaking.html";
		$( location ).attr("href", url);
	});
	
	$("#save").click(function(){
		var url = "menu.html";
		$( location ).attr("href", url);
	});
	
	$("#countItem").click(function(){
		var url = "count-item.html";
		$( location ).attr("href", url);
	});
	
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
 
 