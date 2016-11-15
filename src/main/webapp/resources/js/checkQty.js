$(document).ready(function() {
	eventsHandler();	
	$("#sampleData").hide();
 });
 
 function eventsHandler(){
	 backMenuClicked();
	 searchProduct();
	 productClick();
 }

 function backMenuClicked(){
	$("#backMenu").click(function(){
		var url = "menu.html";
		$( location ).attr("href", url);
	});
 }
 
 function searchProduct(){
	$("#products-search").on("input", function(e) {
		var schTxt = $(this).val();

		if(schTxt.length < 3) {
			$("#sampleData").hide();
		} else {
			$("#sampleData").show();
		}
	});
}

function productClick(){
	$(".product").click(function(){
		var url = "product-detail.html";
		$( location ).attr("href", url);
	});
}
