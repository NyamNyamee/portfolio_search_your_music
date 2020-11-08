$(function(){
	let names = document.querySelectorAll('.name');
	for(let i = 0; i < names.length; i++){
		if(names[i].innerHTML.length > 30){
			names[i].title = names[i].innerHTML;
			names[i].innerHTML = names[i].innerHTML.slice(0, 30) + '...';
		}		
	}
	
	let imgs = document.querySelectorAll('.album a img');
	for(let i = 0; i < imgs.length; i++){
			imgs[i].addEventListener('mouseover', () => {
			imgs[i].classList.add('hovered');
		});
			imgs[i].addEventListener('mouseout', () => {
			imgs[i].classList.remove('hovered');
		});
	}
});
	
	var checkAllFlag = 'false'; // false면 전체선택, true이면 전체해제
	function checkAll(check) 
	{
		if(check.val() == null)
		{
			alert('Add first');
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
	
	