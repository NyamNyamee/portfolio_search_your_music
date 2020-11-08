<%@page import="kr.inwoo.music.service.MemberServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Certify ID</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script>
	alert('Hi ${memberVO.name}! Your account is "${memberVO.userid}"');
	parent.window.close(); // 브라우저 강제 종료
	// location.href='${pageContext.request.contextPath}/login';
</script>
<style>
	
</style>
</head>
<body>
	
</body>
</html>