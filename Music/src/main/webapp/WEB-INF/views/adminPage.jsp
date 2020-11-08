<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Page</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script>
	$(function () {
		$("#ManagingMembers").click(function() {
			location.href='memberManage';
		});
		$("#ManagingMusics").click(function() {
			location.href='musicManage';
		});
	});
</script>
<link rel="stylesheet" href="./resources/css/adminPage.css" />
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		Admin Page
	</div>
	<div class="title">
		<c:if test="${not empty user }">
			Hello ${user == 'anonymousUser' ? "Guest" : sessionScope.memberVO.name }(${user == 'anonymousUser' ? "^~^" : sessionScope.memberVO.nickname })!
		</c:if>
	</div>
		<div class="manage">
			<button type="button" class="btn btn-secondary" id="ManagingMembers">Managing Members</button>
		</div>
		<div class="manage">
			<button type="button" class="btn btn-secondary" id="ManagingMusics">Managing Musics</button>
		</div>
	<footer>
		<div>
			Creater &nbsp; LEE INWOO
		</div>
		<div>
			Contact &nbsp; jopelee2@gmail.com, 010-3710-2579
		</div>
	</footer>
</body>
</html>