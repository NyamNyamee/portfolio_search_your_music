<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>searchNormal</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
<script src="./resources/js/searchNormal.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/searchNormal.css" />
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		Searching without login
	</div>
	<form action="searchNormalOk" method="post" onsubmit="return formCheck();">
	<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div style="text-align: center;">
			<select name="category">
				<option value="all" <c:if test="${category eq 'all'}">selected="selected"</c:if>>All</option>
				<option value="name" <c:if test="${category eq 'name'}">selected="selected"</c:if>>Song</option>
				<option value="artist" <c:if test="${category eq 'artist'}">selected="selected"</c:if>>Artist</option>
			</select> 
			<input type="text" name="search" id="search" />
			<input type="hidden" name="firstSearch" id="firstSearch" value="yes"/>
			<input type="submit" value="Search" class="btn btn-secondary" style="margin-bottom: 5px;"/>
		</div>
	</form>
	<table style="border: 1px solid gray; margin: auto; width: 1000px; margin-bottom: 50px;">
		<tr>
			<td class="album">Album</td>
			<td class="name">Song</td>
			<td class="artist">Artist</td>
			<td class="regDate">Registered</td>
		</tr>
		<c:if test="${category eq 'all' }">
			<c:if test="${not empty list1 }">
				<c:forEach var="vo" items="${list1 }">
					<tr>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list1 }">
					<tr>
						<td colspan="4" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'name' }">
			<c:if test="${not empty list2 }">
				<c:forEach var="vo" items="${list2 }">
					<tr>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list2 }">
					<tr>
						<td colspan="4" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'artist' }">
			<c:if test="${not empty list3 }">
				<c:forEach var="vo" items="${list3 }">
					<tr>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list3 }">
					<tr>
						<td colspan="4" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
	</table>
</body>
</html>