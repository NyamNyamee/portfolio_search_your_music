$(function () {
	$("#searchBtn").click(function() {
		location.href='searchNormal';
	});
	$("#searchBtn2").click(function() {
		location.href='searchMember';
	});
	$("#loginBtn").click(function() {
		location.href='login';
	});
	$("#logoutBtn").click(function() {
		// location.href='logout.jsp';
		// Ajax로 로그아웃시키고 새로고침
		$.ajax("logout", { success:function(data){window.location.reload();} });
	});
	$('#musicCart').click(function() {
		location.href='cartList'
	});
	
	getTime();
	setInterval(getTime, 1000); // 1초마다 getTime메서드 호출
});


function getTime() {
            let today = new Date();
         	let yy = today.getFullYear();
         	let MM = today.getMonth() + 1;
         	let dd = today.getDate();
         	let day = today.getDay();
         	let array = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'];
         	let engDay = array[day];
         	
            let hh = ('0' + today.getHours()).slice(-2);
            let mm = ('0' + today.getMinutes()).slice(-2);
            let ss = ('0' + today.getSeconds()).slice(-2);
            let obj = document.querySelector("#time");
            obj.innerHTML = yy + "." + MM + "." + dd + `(${engDay}) ` + hh + ":" + mm + ":" + ss;            
        }