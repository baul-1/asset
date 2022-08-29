function login(){
	
	var id = document.querySelector('.id');
	var pw = document.querySelector('.pw');
	const xhr = new XMLHttpRequest();
	var formData = new FormData();
	
    if(id.value.trim() == ''){
        alert('아이디를 입력하세요.');
        id.focus();
        return ;
    }

    if(pw.value.trim() == ''){
        alert('비밀번호를 입력하세요.');
        pw.focus();
        return;
    }
	
	formData.append("id", id.value);
	formData.append("pw", pw.value);
	
	xhr.open("POST", "login", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE ){
			if(xhr.status == 200){
				var response = JSON.parse(xhr.responseText);
			   	if(response.result == 0){
		            alert('정상적인 접근으로 로그인 하세요.');
		        }else if(response.result == 1){
		            alert('존재하지 않은 아이디거나, 맞지 않은 아이디입니다.');
		            id.focus();
		            pw.value = '';
		        }else if(response.result == 2){
		            alert('잘못된 비밀번호입니다.');
		            pw.focus();
		        }else if(response.result == 3){
					window.location.href = 'map';
		        }
			}else {
				alert('관리자에게 문의하세요.');
			}
		}
	}
	
	xhr.send(formData);
}



document.getElementById('buttonDiv').addEventListener('click', ()=>{
	login();
})

document.addEventListener('keydown',e=>{
	if(e.key =='Enter'){
		login();
	}
});
