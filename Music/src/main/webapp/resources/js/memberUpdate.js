$(function(){
		$("#password").focus();
		
		$("#cancelBtn").click(function(){
			location.href='index';
		});
		
		// 우편번호 검색
		$("#zipcode").click(function(){
			new daum.Postcode({
		        oncomplete: function(data) {
		            $("#zipcode").val(data.zonecode);
		            $("#addr1").val(data.roadAddress);
		            $("#addr2").focus();
		        }
		    }).open();
		});
		
	});
	
	function formCheck() {
	    var value = $("#password").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input PW');
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
		
		var value = $("#name").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input Name');
			$("#name").val("");
			$("#name").focus();
			return false;
		}
		
		var value = $("#nickname").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input Nickname');
			$("#nickname").val("");
			$("#nickname").focus();
			return false;
		}
		
		var value = $("#hp").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Input C.P');
			$("#hp").val("");
			$("#hp").focus();
			return false;
		}
		
		var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
		// 검증에 사용할 정규식 변수 regExp에 저장
	    if(value.match(regExp) == null) 
	    {
	    	alert('C.P Input Ex)010-1234-5678');
			$("#hp").focus();
			return false;
		}
		
		return true;
	}