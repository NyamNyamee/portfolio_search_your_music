<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>enroll</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="./resources/js/register.js"></script>
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
	<form action="${pageContext.request.contextPath }/registerOk" method="post" style="margin-top: 50px;" onsubmit="return formCheck();">
		<div style="width: 80%; padding: 20px; margin: auto; border: 3px solid silver;">
			<div style="text-align: center; font-size: 15pt; ">
				Enroll
			</div>
			<div class="form-group row">
				<%-- 이부분을 히든으로 가져가야 함 --%>
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
				<label for="userid" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;ID(E-mail)</label> 
				<input type="text"	name="userid" id="userid" class="form-control col-sm-3" aria-describedby="emailHelp">
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
				<input type="text" name="name" id="name" class="form-control col-sm-3">
				<label for="nickname" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Nickname</label> 
				<input type="text" name="nickname" id="nickname" class="form-control col-sm-3">
			</div>
			<div class="form-group row">
				<label for="hp" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;C.P</label> 
				<input type="text" name="hp" id="hp" class="form-control col-sm-3">
				<label for="birth" class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Birth</label> 
				<input type="text" name="birth" id="birth" class="form-control col-sm-3" readonly="readonly" placeholder="Click here to select">
			</div>
			<div class="form-group" >
				<label class="col-sm-2 col-form-label"  style="text-align: right;">*&nbsp;Sex</label>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender" id="gender1" value="M" checked="checked">
					<label class="form-check-label" for="gender1">Male</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender" id="gender2" value="F">
					<label class="form-check-label" for="gender2">Female</label>
				</div>
			</div>
			<div class="form-group row">
				<label for="zipcode" class="col-sm-2 col-form-label"  style="text-align: right;">ZipCode</label> 
				<input type="text" name="zipcode" id="zipcode" class="form-control col-sm-3" readonly="readonly" placeholder="Click here to search">
			</div>
			<div class="form-group row">
				<label for="addr1"  class="col-sm-2 col-form-label"  style="text-align: right;">RoadAddress</label> 
				<input type="text" name="addr1" id="addr1" class="form-control col-sm-8" readonly="readonly">
			</div>
			<div class="form-group row">
				<label for="addr2"  class="col-sm-2 col-form-label" style="text-align: right;">DetailAddress</label> 
				<input type="text" name="addr2" id="addr2" class="form-control col-sm-8">
			</div>
			<div style="text-align: center;">
				<button type="submit" class="btn btn-secondary">Confirm</button>
				<button type="button" class="btn btn-secondary" id="cancelBtn">Cancel</button>
			</div>
		</div>
	</form>
</body>
</html>