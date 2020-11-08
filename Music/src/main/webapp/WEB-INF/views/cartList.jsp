<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My List</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" ></script>
<script src="./resources/js/cartList.js"></script>
<script type="text/javascript">
	function removeManyBtn(){
		var cart_idx_array = new Array(); // 배열 선언
		
        $('input:checkbox[name=check]:checked').each(function() // 체크된 체크박스의 value 값을 가져와서 배열에 추가한다.
        { 
        	cart_idx_array.push(this.value);
        });
		
		if(cart_idx_array.length == 0) // 배열에 아무것도 없을 때
		{
			alert('Check something');
		}
		else
		{
			var flag = confirm("confirm delete");
			if(flag == true)
			{
	       		// alert(cart_idx_array);
	       		jQuery.ajaxSettings.traditional = true; // ajax로 배열을 넘겨줄때 필요한 설정
	       		$.ajax({
					type: "POST",
					url: "removeCartCheckOkAjax",
					data:{"${_csrf.parameterName}" : $('.csrf').val(),
						"cart_idx_array" : cart_idx_array},
					success: function () {
						// alert('added');
					},
					error: function () {
						alert('failed');
					}
				});
	       		alert('removed');
	    		window.location.reload();
			}
		}
	}
</script>
<link rel="stylesheet" href="./resources/css/cartList.css" />
</head>
<body>
	<div class="headerIcon">
		<a href="/music"><img src="./resources/images/mainMusicIcon.png" /></a>
	</div>
	<div class="header">
		My Music
	</div>
	<div align="right" style="width: 75%; margin: 5px;">
		<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
		<input type="hidden" class="csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<button type="button" class="btn btn-secondary" name="removeManyBtn" onclick="removeManyBtn()" style="width: 200px;">Remove checked musics</button>
	</div>
	<form action="removeAllCartOk" onsubmit="return confirm('clear confirm');" method="post">
		<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div align="right" style="width: 75%; margin: 5px;">
			<button class="btn btn-secondary" id="clearCart" style="width: 200px;">Clear all</button>
		</div>
	</form>
	<table style="border: 1px solid gray; margin: auto; width: 1000px; margin-bottom: 50px;">
		<tr>
			<td class="check"><input type="button" value="++" class="btn btn-outline-secondary btn" onclick="this.value=checkAll($('input[name=check]'))" style="width: 45px; font-size: 15px;"></td>
			<td class="album">Album</td>
			<td class="name">Song</td>
			<td class="artist">Artist</td>
			<td class="regDate">Registered</td>
			<td class="removeCart">Delete</td>
		</tr>
		<c:if test="${empty cartList }">
			<tr>
				<td colspan="6" style="text-align: center;"><a href="${pageContext.request.contextPath }/searchMember">Add your favorite song!</a></td>
			</tr>
		</c:if>
		<c:if test="${not empty cartList }">
			<c:forEach var="cartList" items="${cartList }" varStatus="vs">
				<tr>
					<td class="check" align="center"><input type="checkbox" name="check" value="${cartList.cart_idx}" /></td>
					<td class="album" align="center"><a href="https://www.youtube.com/results?search_query=${cartList.artist } ${cartList.name }" target="_blank"><img src="${cartList.url }" alt="${cartList.name }" title="Open in YouTube"/></a></td>
					<td class="name">${cartList.name }</td>
					<td class="artist">${cartList.artist }</td>
					<td class="regDate"><fmt:formatDate value="${cartList.regDate }" pattern="yy/MM/dd"/></td>
					<td class="removeCart" align="center"	>
						<form action="removeCartOk" method="post">
							<%-- 이부분을 반드시 숨겨서 가져가야 한다. --%>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" name="cart_idx" value="${cartList.cart_idx }"/>
							<button class="btn btn-outline-secondary btn">-</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>