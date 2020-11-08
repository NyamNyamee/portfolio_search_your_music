$(function () {
		$('#search').focus();
		
		var adminIdxList = new Array(); // 관리자 idx를 저장할 배열 생성
		$('.ail').each(function() // 관리자 idx의 배열을 가져와서 adminList변수에 집어넣어준다
		{ 
			adminIdxList.push(this.value);
		});		
		// alert("adminIdxList=" + adminIdxList);
		
		var idxList = new Array(); // 회원 idx를 저장할 배열 생성
		$('.ail2').each(function() // 회원 id의 배열을 가져와서 adminList변수에 집어넣어준다
		{ 
			idxList.push(this.value);
		});	
		// alert("idxList=" + idxList);
		
		for(var i in idxList) // 회원 idx의 개수만큼 반복하면서 해당 idx가 관리자 idx에 속하는지 판단
		{
			// alert("회원들의 idx와 그것이 관리자 아이디인지 판단하는 값(-1이면 거짓)=" + idxList[i] + "," + adminIdxList.indexOf(idxList[i]));
			if(adminIdxList.indexOf(idxList[i]) >= 0) // adminIdxList에 idxList[i]가 존재하지 않는다면 -1을 리턴하므로 0 이상이면 존재한단 뜻임
			{
				$('.userIdx' + idxList[i]).css('background-color', '#DCEBFF'); // 한개만 적용되도 클래스이기 때문에 일괄적용됨. 이를 수정해야 함
			}
		}
	});
	
	var checkAllFlag = 'false'; // false면 전체선택, true이면 전체해제
	function checkAll(check) 
	{
		if(check.val() == null)
		{
			alert('search something');
			return "++";
		}
		else
		{
		    if(checkAllFlag == 'false')
		    {
		        for(i = 0; i < check.length; i++) 
		        {
		        	check[i].checked = true; // 모든 체크박스를 체크한다.
		        }
		        checkAllFlag = "true";
		        return "--"; // 버튼객체의 value속성으로 반환(this.value.list로 넘겨져왔기 때문에)
		    } 
		    else 
		    {
		        for(i = 0; i < check.length; i++)
		        {
		        	check[i].checked = false; // 모든 체크박스를 해제한다.
		        }
		        checkAllFlag = "false";
		        return "++"; // 버튼객체의 vaule속성으로 반환
		    }
		}
	}
	
	function formCheck() {
		var value = $("#search").val();
		if(value == null || value.trim().length == 0)
		{
			alert('Write something');
			$("#search").val("");
			$("#search").focus();
			return false;
		}
		return true;
	}