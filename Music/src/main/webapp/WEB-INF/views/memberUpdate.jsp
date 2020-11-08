<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberUpdate</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="./resources/js/memberUpdate.js"></script>
<script type="text/javascript">
	function delMemBtn(idx, userid){
		// alert(idx);
		// alert(userid);		
		// 비밀번호 유효성검사
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
		if($("#password").val()!=$("#password2").val()){
			alert('PW mismatched');
			$("#password").val("");
			$("#password2").val("");
			$("#password").focus();	
		}
		// 비밀번호 유효성검사를 통과했을 때
		else{
			var flag = confirm("delete permanently?");
			if(flag)
			{
				$.ajax({
					type: "POST",
					url: "deleteMemberSelfOk",
					data:{"${_csrf.parameterName}" : $('.csrf').val(),
						"idx" : idx,
						"userid" : userid,
						"password" : $("#password").val()
						},
					success: function (data) {						
						// alert('data=' + data);
						if(data == 'true'){
							alert('Good bye! \nAll your information is gone');
							location.href="index";
						}
						else{
							alert('password is not correct');
							$("#password").val("");
							$("#password2").val("");
							$("#password").focus();
						}													
					},
					error: function () {
						alert('failed');
					}
				});
			}
		}
	}
</script>
<style type="text/css">
	body {
		background: -webkit-linear-gradient(left, white, silver);
	}
	
	.headerIcon {
		position: fixed;
		top: 10px;
		left: 10px;
	}
</style>
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<form action="memberUpdateOk" method="post" style="margin-top: 50px;" onsubmit="return formCheck();">
		<div style="width: 80%; padding: 20px; margin: auto; border: 3px solid silver;">
			<div style="text-align: center; font-size: 15pt; ">
				Update Information
				<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="idx" value="${memberVO.idx }" />
			</div>
			<div class="form-group row">
				<label for="userid" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;ID(E-mail)</label> 
				<input type="text" value="${memberVO.userid }" readonly="readonly" name="userid" id="userid" class="form-control col-sm-3" aria-describedby="emailHelp">
				<span id="userid_msg" style="margin-left: 10px; padding-top: 6px;"></span>
			</div>
			<div class="form-group row">
				<label for="password" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;PW</label> 
				<input type="password" name="password" id="password" class="form-control col-sm-3">
				<label for="password2" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;PW check</label> 
				<input type="password" name="password2" id="password2" class="form-control col-sm-3">
			</div>
			<div class="form-group row">
				<label for="name" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Name</label> 
				<input type="text" name="name" id="name" class="form-control col-sm-3" value="${memberVO.name }">
				<label for="nickname" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Nickname</label> 
				<input type="text" name="nickname" id="nickname" class="form-control col-sm-3" value="${memberVO.nickname }">
			</div>
			<div class="form-group row">
				<label for="hp" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;C.P</label> 
				<input type="text" name="hp" id="hp" class="form-control col-sm-3" value="${memberVO.hp }">
				<label for="birth" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Birth</label> 
				<input type="text" name="birth" id="birth" class="form-control col-sm-3" value="${memberVO.birth }" readonly="readonly">
			</div>
			<div class="form-group" >
				<label class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Sex</label>
				<div class="form-check form-check-inline">
					${memberVO.gender }
				</div>
			</div>
			<div class="form-group row">
				<label for="zipcode" class="col-sm-2 col-form-label"  style="text-align: right;">ZipCode</label> 
				<input type="text" name="zipcode" id="zipcode" class="form-control col-sm-3" readonly="readonly" placeholder="Click to search" value="${memberVO.zipcode }">
			</div>
			<div class="form-group row">
				<label for="addr1"  class="col-sm-2 col-form-label"  style="text-align: right;">Road Address</label> 
				<input type="text" name="addr1" id="addr1" class="form-control col-sm-8" readonly="readonly" value="${memberVO.addr1 }">
			</div>
			<div class="form-group row">
				<label for="addr2"  class="col-sm-2 col-form-label" style="text-align: right;">Detail Address</label> 
				<input type="text" name="addr2" id="addr2" class="form-control col-sm-8" value="${memberVO.addr2 }">
			</div>
			<div style="text-align: center;">
				<button type="submit" class="btn btn-secondary">Update</button>
				<button type="button" class="btn btn-secondary" id="cancelBtn">Cancel</button>
			</div>
			<div style="text-align: center; margin-top: 10px;">
				<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
				<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button type="button" class="btn btn-danger" onclick="delMemBtn(${memberVO.idx}, '${memberVO.userid }')">Delete my account</button>
			</div>
		</div>
	</form>
</body>
</html>