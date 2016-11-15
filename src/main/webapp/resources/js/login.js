$(document).ready(function() {
	eventsHandler();	
	
	// Init Component
	$("#error").hide();
 });
 
 function eventsHandler(){
	 loginBtnClicked();
 }

 function loginBtnClicked(){
	 $("#loginBtn").click(function(){
		var username = $("#userId").val();
		var pwd = $("#pwd").val();
		
		console.log("User Name "+username);
		console.log("Password "+pwd);
		
		if(username == "admin" && pwd =="1234"){
			var url = "login2.html";
			$( location ).attr("href", url);
		}
		else{
			 toastr.options = {
				"closeButton": false,
				"debug": false,
				"newestOnTop": false,
				"progressBar": false,
				"positionClass": "toast-top-full-width",
				"preventDuplicates": false,
				"onclick": null,
				"showDuration": "300",
				"hideDuration": "1000",
				"timeOut": "5000",
				"extendedTimeOut": "1000",
				"showEasing": "swing",
				"hideEasing": "linear",
				"showMethod": "fadeIn",
				"hideMethod": "fadeOut"
			}
			toastr.error('Username or Password is Invalid', 'Login Failure');
		}
	});
 }
