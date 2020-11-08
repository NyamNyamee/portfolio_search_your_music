	$(function(){
		$("#cancelBtn").click(function(){
			location.href='index';
		});
		
		// 우편번호 검색
		$("#zipcode").click(function() {
			new daum.Postcode({
		        oncomplete: function(data) {
		            $("#zipcode").val(data.zonecode);
		            $("#addr1").val(data.roadAddress);
		            $("#addr2").focus();
		        }
		    }).open();
		});
		
		// 아이디 중복확인
		$("#userid").keyup(function(){
			var value = $(this).val();
			if(value.length >= 10){
				$.ajax("idCheck", 
				{
					data:{'userid':value},
					success: function(data)
					{
				    	if(data==0){
				    		$("#userid_msg").html("valid").css('color','blue');
				    	}else{
				    		$("#userid_msg").html("invalid").css('color','red');
				    	}
					}
				})
			}else{
				$("#userid_msg").html("").css('color','blue');
			}
		});
		
		var today = new Date();
		var year = today.getFullYear();
		var month = today.getMonth();
		var date = today.getDate();
		
		$( "#birth" ).datepicker({
			dateFormat: "yy-mm-dd", // 날짜 형식
		    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 월명
		    monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 월명 요약
		    dayNames: ['일','월','화','수','목','금','토'], // 요일명
		    dayNamesShort: ['일','월','화','수','목','금','토'], // 요일명 요약
		    dayNamesMin: ['sun','mon','tue','wed','thu','fri','sat'], // 요일명 표기
		    changeMonth: true, // 월 변경가능
		    changeYear: true, // 년도 변경가능
		    yearRange: "1900", // 1900년 이후로 선택가능
			maxDate : year + "-" + (month + 1) + "-" + date // 현재 날짜 이후로 선택할 수 없음
		});
	});

	function formCheck() {
		var value = $("#userid").val();
		if (value == null || value.trim().length == 0) {
			alert('Input ID');
			$("#userid").val("");
			$("#userid").focus();
			return false;
		}

		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		// 검증에 사용할 정규식 변수 regExp에 저장
		if (value.match(regExp) == null) {
			alert('Check your ID(E-mail)');
			$("#userid").val("");
			$("#userid").focus();
			return false;
		}

		if ($("#userid_msg").html() == 'invalid') {
			alert('invalid ID');
			$("#userid").val("");
			$("#userid").val();
			return false;
		}

		var value = $("#password").val();
		if (value == null || value.trim().length == 0) {
			alert('Input PW');
			$("#password").val("");
			$("#password").focus();
			return false;
		}

		var value = $("#password2").val();
		if (value == null || value.trim().length == 0) {
			alert('Input PW2');
			$("#password2").val("");
			$("#password2").focus();
			return false;
		}
		// 비번과 비번확인이 일치하는지 검사
		if ($("#password").val() != $("#password2").val()) {
			alert('PW mismatched');
			$("#password").val("");
			$("#password2").val("");
			$("#password").focus();
			return false;
		}

		var value = $("#name").val();
		if (value == null || value.trim().length == 0) {
			alert('Input Name');
			$("#name").val("");
			$("#name").focus();
			return false;
		}

		var value = $("#nickname").val();
		if (value == null || value.trim().length == 0) {
			alert('Input Nickname');
			$("#nickname").val("");
			$("#nickname").focus();
			return false;
		}

		var value = $("#hp").val();
		if (value == null || value.trim().length == 0) {
			alert('Input C.P');
			$("#hp").val("");
			$("#hp").focus();
			return false;
		}

		var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
		// 검증에 사용할 정규식 변수 regExp에 저장
		if (value.match(regExp) == null) {
			alert('C.P Input Ex)010-1234-5678');
			$("#hp").focus();
			return false;
		}
		
		var value = $("#birth").val();
		if (value == null || value.trim().length == 0) {
			alert('Input Birth');
			$("#birth").val("");
			$("#birth").focus();
			return false;
		}
		
		return true;
	}