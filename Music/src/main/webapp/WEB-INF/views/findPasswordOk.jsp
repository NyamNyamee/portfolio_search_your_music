<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SYM</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>
	body{
	  background: -webkit-linear-gradient(left, white, silver);
	}
</style>
</head>
<body>
	<c:if test="${empty userid }">
		<script type="text/javascript">
			alert('There is no such information.\nPlease try again.');
			location.href='findPassword';
		</script>
	</c:if>
	Temporary password has been sent.<br />
	Please check your e-mail and login. <br />
	<button class="btn btn-secondary" onclick="location.href='login'">Go to login</button>
</body>
</html>