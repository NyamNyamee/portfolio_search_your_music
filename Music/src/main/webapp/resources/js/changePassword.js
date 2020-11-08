$(function(){
		$("#cancelBtn").click(function(){
			location.href='index';
		});
	});	
	
	function formCheck() {
	    var value = $("#cpassword").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input current PW');
			$("#cpassword").val("");
			$("#cpassword").focus();
			return false;
		}
		
	    var value = $("#password").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input new PW');
			$("#password").val("");
			$("#password").focus();
			return false;
		}
		
		var value = $("#password2").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Check your PW');
			$("#password2").val("");
			$("#password2").focus();
			return false;
		}
		// 비번과 비번확인이 일치하는지 검사
		if($("#password").val()!=$("#password2").val()){
			alert('PW mismatched');
			$("#password").val("");
			$("#password2").val("");
			$("#password").focus();	
			return false;
		}
		
		return true;
	}