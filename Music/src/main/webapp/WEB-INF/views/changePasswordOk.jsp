<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FailedPW</title>	
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
<style type="text/css">
	body{
	  background: -webkit-linear-gradient(left, white, silver);
	}
</style>
</head>
<body>
	Failed PW changing <br />
	Current PW is not correct <br />
	<button class="btn btn-secondary" onclick="location.href='findPassword'">Find PW</button> &nbsp;&nbsp;
	<button class="btn btn-secondary" onclick="location.href='changePassword'">Try Again</button>
</body>
</html>