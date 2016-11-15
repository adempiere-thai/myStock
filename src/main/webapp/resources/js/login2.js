$(document).ready(function() {
	eventsHandler();	
 });
 
 function eventsHandler(){
	 loginBtnClicked();
 }

 function loginBtnClicked(){
	$("#loginBtn").click(function(){
		var url = "menu.html";
		$( location ).attr("href", url);
	});
 }
