$( document ).on( "pageinit", "#internaluse-detail", function() {
	
	$('#items-list').on('click', '.items-in-order-item', function(event) {
		$('#items-list .items-in-order-item').removeClass('focus');
		$(this).addClass('focus');
	});
	
	$('#items-list').on('click', '.edit-qty-btn', function(event) {
		console.log($(this).attr("edit_no")+ " "+ $(this).attr("edit_qty"));
		
		$("#editLineNo").val($(this).attr("edit_no"));
		$("#editQty").val($(this).attr("edit_qty"));
		
		$('#editQtyDialog').popup('open');
	});
	
	$('#items-list').on('click', '.delete-btn', function(event) {
		console.log(this.del_id);
		$("#delLineNo").val($(this).attr("del_no"));
		$('#deleteDialog').popup('open');
	});
	
	$('#submitDel').on('click', function(event) {
		$("#deleteFrm").submit();
	});
	
	$("#saveDocument").on("click", function(e) {
		$('#processingImg').show();
		$('#completedMsg').hide();		
		$('#processing').popup('open');
		
		var url = '/myStock/internaluse/save';
		
		$.getJSON(url, function (data) {
			console.log(data);
			
			if(data.indexOf("err") > -1){
				// Error 
				window.location.href = "/myStock/internaluse/detail?error="+data;
			}
			else{
				$('#docNo').text(data);
				$('#processingImg').hide();
				$('#completedMsg').show();
			}
		});
		
	});
	
});
 