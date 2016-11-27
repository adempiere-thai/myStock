$( document ).on( "pageinit", "#product", function() {
	
	console.log("Ready to bring the awesome.");
	var productListView = $("#productList");
	
	$("#products-search").on("input", function(e) {
		//alert('2');
		if(e.handled !== true)// This will prevent event triggering more then once
        {
	        var text = $(this).val();
	        if(text.length < 2) {
	        	productListView.html("");
	        	productListView.listview("refresh");
	        } else {
	            $.getJSON('getProductList?srhKey='+encodeURIComponent(text), function(data) {
	            	var items = [];
	            	
	            	$.each( data, function ( i, val ) {
	            		items.push( "<li><p><h4>" + val.productSrhKey +" "+ val.productName + "</h4></p>" 
	    	               		  +"<p><strong>On-Hand : </strong><span class=\"errMsg\">"+numeral(val.onhandQty).format('0,0')+"</span></p>"
	    	               		  +"<p><strong>Reserved: </strong><span class=\"errMsg\">"+numeral(val.reservedQty).format('0,0')+"</span></p>"
	    	               		  +"<p><strong>PO : </strong><span class=\"errMsg\">"+numeral(val.orderQty).format('0,0')+"</span></p></li>");
	               });
	           	  
	           	   	productListView.html(items.join('')).promise().done(function () {
	           	   		productListView.listview("refresh");
	           	   	});
	          });
	        }
	        event.handled = true;
        }
    });
});
