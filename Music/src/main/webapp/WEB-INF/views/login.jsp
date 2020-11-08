<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>Login</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="./resources/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="./resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="./resources/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="./resources/css/util.css">
	<link rel="stylesheet" type="text/css" href="./resources/css/main.css">
<!--===============================================================================================-->
</head>
<body>
	<div class="limiter">
		<div class="container-login100">
		<div class="headerIcon">
			<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
		</div>
			<div class="wrap-login100 p-b-160 p-t-50">
				<form class="login100-form validate-form" action="login" method="post">
				<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<%-- 이전 페이지의 주소를 숨겨 가지고감 --%>
				<input type="hidden" name="referer" value="${referer }" />
					<span class="login100-form-title p-b-43">
						Login
					</span>
					
					<div class="wrap-input100 rs1 validate-input" data-validate = "Username is required">
						<input class="input100" type="text" name="userid">
						<span class="label-input100">ID</span>
					</div>
					
					<div class="wrap-input100 rs2 validate-input" data-validate="Password is required">
						<input class="input100" type="password" name="password">
						<span class="label-input100">Password</span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							login
						</button>
					</div>
					
					<div class="text-center w-full p-t-23">
						<a href="${pageContext.request.contextPath }/findUserid" class="txt1">
							Forgot ID &nbsp;&nbsp;
						</a>
						<a href="${pageContext.request.contextPath }/findPassword" class="txt1">
							&nbsp;&nbsp;Forgot PW &nbsp;&nbsp;
						</a>
						<a href="${pageContext.request.contextPath }/register" class="txt1">
							&nbsp;&nbsp;Enroll
						</a>
						<br />
						<a href="/music" class="txt1">
							Home
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	

	
	
<!--===============================================================================================-->
	<script src="./resources/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="./resources/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="./resources/vendor/bootstrap/js/popper.js"></script>
	<script src="./resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="./resources/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="./resources/vendor/daterangepicker/moment.min.js"></script>
	<script src="./resources/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="./resources/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="./resources/js/main.js"></script>

</body>
</html>