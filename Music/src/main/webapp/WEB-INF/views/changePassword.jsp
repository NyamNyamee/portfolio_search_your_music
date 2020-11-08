<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change PW</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="./resources/js/changePassword.js"></script>
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
	<form action="${pageContext.request.contextPath }/changePasswordOk" method="post" style="margin-top: 50px;" onsubmit="return formCheck();">
		<div style="width: 80%; padding: 20px; margin: auto; border: 3px solid silver;">
			<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div style="text-align: center; font-size: 15pt; ">
				Change Password
			</div>
			<div class="form-group row">
				<label for="userid" class="col-sm-2 col-form-label"  style="text-align: right;">Current PW</label> 
				<input type="password"
					name="cpassword" id="cpassword" class="form-control col-sm-3" >
				<span id="userid_msg" style="margin-left: 10px; padding-top: 6px;"></span>
			</div>
			<div class="form-group row">
				<label for="password" class="col-sm-2 col-form-label"  style="text-align: right;">New PW</label> <input type="password"
					name="password" id="password" class="form-control col-sm-3">
				<label for="password2" class="col-sm-2 col-form-label"  style="text-align: right;">New PW check</label> <input type="password"
					name="password2" id="password2" class="form-control col-sm-3">
			</div>
			<div style="text-align: center;">
				<button type="submit" class="btn btn-secondary">Confirm</button>
				<button type="button" class="btn btn-secondary" id="cancelBtn">Cancel</button>
			</div>
		</div>
	</form>
</body>
</html>