$( document ).on( "pageinit", "#stocktaking-detail", function() {
	
	$('#items-list').on('click', '.items-in-order-item', function(event) {
		$('#items-list .items-in-order-item').removeClass('focus');
		$(this).addClass('focus');
	});
	
	$('#items-list').on('click', '.edit-qty-btn', function(event) {
		console.log($(this).attr("edit_id")+ " "+ $(this).attr("edit_qty"));
		
		$("#edit_lineId").val($(this).attr("edit_id"));
		$("#edit_qty").val($(this).attr("edit_qty"));
		
		$('#editQty').popup('open');
	});
	
	$('#items-list').on('click', '.delete-btn', function(event) {
		console.log(this.del_id);
		$("#del_lineId").val($(this).attr("del_id"));
		$('#deleteDialog').popup('open');
	});
	
 });
 
 