<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SYM-HOME</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="./resources/js/index.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/index.css">
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		Search Your Music
	</div>
	<div class="title">
		<c:if test="${not empty user }">
			<div id="time">HH:mm:ss</div>
			Hello ${user == 'anonymousUser' ? "Guest" : sessionScope.memberVO.name }(${user == 'anonymousUser' ? "^~^" : sessionScope.memberVO.nickname })!
		</c:if>
	</div>
	<c:if test="${empty sessionScope.memberVO }">
		<div class="search">
			<button type="button" class="btn btn-secondary" id="searchBtn">Search</button>
		</div>
	</c:if>
	
	<c:if test="${not empty sessionScope.memberVO }">
		<div class="search">
			<button type="button" class="btn btn-secondary" id="searchBtn2">Search</button>
		</div>
	</c:if>
	<c:if test="${not empty sessionScope.memberVO }">
		<div class="search">
			<button type="button" class="btn btn-secondary" id="musicCart">My Music</button>
		</div>
	</c:if>
	<c:if test="${empty sessionScope.memberVO }">
		<div class="login">
	 		<button type="button" class="btn btn-secondary" id="loginBtn">Login</button>
	 	</div>
	</c:if>
	<c:if test="${not empty sessionScope.memberVO }">
		<div class="logout">
			<button type="button" class="btn btn-secondary" id="logoutBtn">Logout</button>
		</div>
		<div class="memberInfo">
			${user eq 'anonymousUser' ? "Guest" : user }'s account <br />
		 	<a href="${pageContext.request.contextPath }/memberUpdate">Update information</a> <br />
		 	<a href="${pageContext.request.contextPath }/changePassword">Change password</a> <br />
		 	<c:if test="${roles[0]=='ROLE_ADMIN' || role[0]=='ROLE_DBA' }">
				 <a href="${pageContext.request.contextPath }/adminPage">Admin Page</a>
			</c:if>	
	 	</div>
	</c:if>
	<footer>
		<div style="text-align: center;">
			Creater &nbsp; LEE INWOO <br />
			Contact &nbsp; jopelee2@gmail.com, 010-3710-2579
		</div>
		
	</footer>
</body>
</html>