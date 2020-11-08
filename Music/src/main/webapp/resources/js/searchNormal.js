$(function () {
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
