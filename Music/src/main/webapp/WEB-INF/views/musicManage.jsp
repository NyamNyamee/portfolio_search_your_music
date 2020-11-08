<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>musicManage</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
<script src="./resources/js/musicManage.js"></script>
<script type="text/javascript">
	function delBtn(id){		
		var flag = confirm("delete permanently?");
		if(flag == true)
		{
			$.ajax({
				type: "POST",
				url: "deleteOk",
				data:{"${_csrf.parameterName}" : $('.csrf').val(),
					"music_idx" : id},
				success: function () {
					alert('deleted');
					window.location.reload();
				},
				error: function () {
					alert('failed');
				}
			});
		}
	}
	
	function delManyBtn(){
		var music_idx_array = new Array(); // 배열 선언
		
        $('input:checkbox[name=check]:checked').each(function() // 체크된 체크박스의 value 값을 가져와서 배열에 추가한다.
        { 
        	music_idx_array.push(this.value);
        });
		
		if(music_idx_array.length == 0) // 배열에 아무것도 없을 때
		{
			alert('check first');
		}
		else
		{
			var flag = confirm("delete permanently?");
			if(flag == true)
			{
	       		// alert(music_idx_array);
	       		jQuery.ajaxSettings.traditional = true; // ajax로 배열을 넘겨줄때 필요한 설정
	       		$.ajax({
					type: "POST",
					url: "deleteMusicCheckOkAjax",
					data:{"${_csrf.parameterName}" : $('.csrf').val(),
						"music_idx_array" : music_idx_array},
					success: function () {
						// alert('added');
					},
					error: function () {
						alert('failed');
					}
				});
	       		alert('deleted');
	    		window.location.reload();
			}
		}
	}
</script>
<link rel="stylesheet" href="./resources/css/musicManage.css" />
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		Managing Musics
	</div>
	<div class="info">
		${count } songs in DB
	</div>
	<form action="searchAdminOk" method="post" onsubmit="return formCheck();">
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
			<input type="submit" value="Search" class="btn btn-secondary"/>
		</div>
	</form>
	<div align="right" style="width: 73%; margin: 5px;">
		<button type="button" class="btn btn-secondary" name="delManyBtn" onclick="delManyBtn()" style="width: 200px;">Delete checked musics</button>
	</div>
	<div align="right">
		<form action="insertSongsOk" method="post">
			<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div>Add latest
				<select name="selectAmount">
					<option value="10" <c:if test="${addAmount eq '10'}">selected="selected"</c:if>>10ea</option>
					<option value="50" <c:if test="${addAmount eq '50'}">selected="selected"</c:if>>50ea</option>
					<option value="100" <c:if test="${addAmount eq '100'}">selected="selected"</c:if>>100ea</option>
				</select> songs
				<button id="insertSongs" class="btn btn-secondary" style="margin: 5px; margin-right: 380px;">+</button>
			</div>
		</form>
	</div>
	<table style="border: 1px solid gray; margin: auto; width: 1000px; margin-bottom: 50px;">
		<tr>
			<td class="check"><input type="button" value="++" class="btn btn-outline-secondary btn" onclick="this.value=checkAll($('input[name=check]'))" style="width: 45px; font-size: 15px;"></td>
			<td class="album">Album</td>
			<td class="name">Song</td>
			<td class="artist">Artist</td>
			<td class="regDate">Registered</td>
			<td class="deleteMusic">Delete</td>
		</tr>
		<c:if test="${category eq 'all' }">
			<c:if test="${not empty list1 }">
				<c:forEach var="vo" items="${list1 }">
					<tr>
						<td class="check" align="center"><input type="checkbox" name="check" value="${vo.music_idx}" /></td>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
						<td class="deleteMusic" align="center">
							<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
							<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="button" class="btn btn-outline-secondary btn" name="delBtn" onclick="delBtn(${vo.music_idx})">-</button>
						</td>	
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list1 }">
					<tr>
						<td colspan="6" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'name' }">
			<c:if test="${not empty list2 }">
				<c:forEach var="vo" items="${list2 }">
					<tr>
						<td class="check" align="center"><input type="checkbox" name="check" value="${vo.music_idx}" /></td>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
						<td class="deleteMusic" align="center">
							<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
							<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="button" class="btn btn-outline-secondary btn" name="delBtn" onclick="delBtn(${vo.music_idx})">-</button>
						</td>	
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list2 }">
					<tr>
						<td colspan="6" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'artist' }">
			<c:if test="${not empty list3 }">
				<c:forEach var="vo" items="${list3 }">
					<tr>
						<td class="check" align="center"><input type="checkbox" name="check" value="${vo.music_idx}" /></td>
						<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${vo.artist } ${vo.name }" target="_blank"><img src="${vo.url }" alt="${vo.name }" title="Open in YouTube"/></a></td>
						<td class="name">${vo.name }</td>
						<td class="artist">${vo.artist }</td>
						<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd"/></td>
						<td class="deleteMusic" align="center">
							<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
							<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="button" class="btn btn-outline-secondary btn" name="delBtn" onclick="delBtn(${vo.music_idx})">-</button>
						</td>	
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list3 }">
					<tr>
						<td colspan="6" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
	</table>

</body>
</html>