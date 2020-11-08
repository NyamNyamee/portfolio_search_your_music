<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberManage</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
<script src="./resources/js/memberManage.js" ></script>
<script type="text/javascript">
	function delMemBtn(idx, userid){
		// alert(idx);
		// alert(userid);
		var flag = confirm("delete permanently?");
		if(flag == true)
		{
			$.ajax({
				type: "POST",
				url: "deleteMemberOk",
				data:{"${_csrf.parameterName}" : $('.csrf').val(),
					"idx" : idx,
					"userid" : userid
					},
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
	
	function delMemManyBtn(){		
		var member_idx_array = new Array(); // 체크된 회원들의 idx가 들어갈 배열 선언
		var member_userid_array = new Array(); // 체크된 회원들의 userid가 들어갈 배열 선언
		
        $('input:checkbox[name=check]:checked').each(function() // 체크된 체크박스의 value값을 가져와서 ,로 나눈 후에 각각의 배열에 추가한다.
        { 
        	member_idx_array.push(this.value.split(',')[0]);
        	member_userid_array.push(this.value.split(',')[1]);
        	// alert(member_idx_array);
        	// alert(member_userid_array);
        });
		
		if(member_idx_array.length == 0) // 배열에 아무것도 없을 때
		{
			alert('check first');
		}
		else
		{
			var flag = confirm("delete permanently?");
			if(flag == true)
			{
	       		jQuery.ajaxSettings.traditional = true; // ajax로 배열을 넘겨줄때 필요한 설정
	       		$.ajax({
					type: "POST",
					url: "deleteMemberCheckOkAjax",
					data:{"${_csrf.parameterName}" : $('.csrf').val(),
						"member_idx_array" : member_idx_array,
						"member_userid_array" : member_userid_array	
					},
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
<link rel="stylesheet" href="./resources/css/memberManage.css" />
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		Managing Members
	</div>
	<div class="info">
		${count } members on DB
	</div>
	<form action="searchHumanOk" method="post" onsubmit="return formCheck();">
		<%-- 이부분을 반드시 숨겨서 가져가야 한다. <--%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div style="text-align: center;">
			<select name="category">
				<option value="userid" <c:if test="${category eq 'userid'}">selected="selected"</c:if>>Userid</option>
				<option value="name" <c:if test="${category eq 'name'}">selected="selected"</c:if>>Name</option>
				<option value="hp" <c:if test="${category eq 'hp'}">selected="selected"</c:if>>C.P</option>
			</select> 
			<input type="text" name="search" id="search" />
			<input type="hidden" name="firstSearch" id="firstSearch" value="yes"/>
			<input type="submit" value="Search" class="btn btn-secondary"/>
		</div>
	</form>
	<div align="right" style="width: 73%; margin: 5px;">
		<button type="button" class="btn btn-secondary" name="delMemManyBtn" onclick="delMemManyBtn()" style="width: 220px;">Delete checked members</button>
	</div>
	<table style="border: 1px solid gray; margin: auto; width: 1000px; margin-bottom: 50px;">
		<tr>
			<td class="check" align="center"><input type="button" value="++" class="btn btn-outline-secondary btn" onclick="this.value=checkAll($('input[name=check]'))" style="width: 45px; font-size: 15px;"></td>
			<td class="id">ID </td>
			<td class="name">Name</td>
			<td class="cp">C.P</td>
			<td class="sex">Sex</td>
			<td class="regDate">RegDate</td>
			<td class="deleteMember">Delete</td>
		</tr>
		<c:forEach var="vo" items="${adminIdxList }">
			<input type="hidden" class="ail" value="${vo }"/>				
		</c:forEach>
		<c:if test="${not empty allMemberList }">
			<c:forEach var="vo" items="${allMemberList }">
				<tr class="userIdx${vo.idx}">
					<td class="check" align="center"><input type="checkbox" name="check" value="${vo.idx},${vo.userid}" /><input type="hidden" class="ail2" value="${vo.idx }"/></td>
					<td class="id">${vo.userid } </td>
					<td class="name">${vo.name }</td>
					<td class="cp">${vo.hp }</td>
					<td class="sex">${vo.gender }</td>
					<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd - HH:mm:ss"/></td>
					<td class="deleteMember" align="center">
						<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
						<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button type="button" class="btn btn-outline-secondary btn" name="delMemBtn" onclick="delMemBtn(${vo.idx}, '${vo.userid }')">-</button>
					</td>	
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${category eq 'userid' }">
			<c:if test="${not empty list1 }">
				<c:forEach var="vo" items="${list1 }">
					<tr class="userIdx${vo.idx}">
					<td class="check" align="center"><input type="checkbox" name="check" value="${vo.idx},${vo.userid}" /><input type="hidden" class="ail2" value="${vo.idx }"/></td>
					<td class="id">${vo.userid }</td>
					<td class="name">${vo.name }</td>
					<td class="cp">${vo.hp }</td>
					<td class="sex">${vo.gender }</td>
					<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd - HH:mm:ss"/></td>
					<td class="deleteMember" align="center">
						<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
						<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button type="button" class="btn btn-outline-secondary btn" name="delMemBtn" onclick="delMemBtn(${vo.idx}, '${vo.userid }')">-</button>
					</td>	
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list1 }">
					<tr>
						<td colspan="7" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'name' }">
			<c:if test="${not empty list2 }">
				<c:forEach var="vo" items="${list2 }">
					<tr class="userIdx${vo.idx}">
					<td class="check" align="center"><input type="checkbox" name="check" value="${vo.idx},${vo.userid}" /><input type="hidden" class="ail2" value="${vo.idx }"/></td>
					<td class="id">${vo.userid }</td>
					<td class="name">${vo.name }</td>
					<td class="cp">${vo.hp }</td>
					<td class="sex">${vo.gender }</td>
					<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd - HH:mm:ss"/></td>
					<td class="deleteMember" align="center">
						<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
						<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button type="button" class="btn btn-outline-secondary btn" name="delMemBtn" onclick="delMemBtn(${vo.idx}, '${vo.userid }')">-</button>
					</td>	
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list2 }">
					<tr>
						<td colspan="7" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
		<c:if test="${category eq 'hp' }">
			<c:if test="${not empty list3 }">
				<c:forEach var="vo" items="${list3 }">
					<tr class="userIdx${vo.idx}">
					<td class="check" align="center"><input type="checkbox" name="check" value="${vo.idx},${vo.userid}" /><input type="hidden" class="ail2" value="${vo.idx }"/></td>
					<td class="id">${vo.userid }</td>
					<td class="name">${vo.name }</td>
					<td class="cp">${vo.hp }</td>
					<td class="sex">${vo.gender }</td>
					<td class="regDate"><fmt:formatDate value="${vo.regDate }" pattern="yy/MM/dd - HH:mm:ss"/></td>
					<td class="deleteMember" align="center">
						<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
						<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<button type="button" class="btn btn-outline-secondary btn" name="delBtn" onclick="delMemBtn(${vo.idx}, '${vo.userid }')">-</button>
					</td>	
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstSearch eq 'yes' }">
				<c:if test="${empty list3 }">
					<tr>
						<td colspan="7" style="text-align: center;">No contents</td>
					</tr>
				</c:if>
			</c:if>
		</c:if>
	</table>

</body>
</html>