$(document).ready(function() {
	eventsHandler();	
 });
 
 function eventsHandler(){
	 backMenuClicked();
	 nextStepClicked();
 }

 function backMenuClicked(){
	$("#backMenu").click(function(){
		var url = "menu.html";
		$( location ).attr("href", url);
	});
 }
 

function nextStepClicked(){
	$("#nextStep").click(function(){
		var url = "stocktaking2.html";
		$( location ).attr("href", url);
	});
}
