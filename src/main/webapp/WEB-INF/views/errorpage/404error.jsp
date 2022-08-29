<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MAP</title>
<link rel="stylesheet" href="css/404error.css">
</head>
<body>

	<div id="wrap" >
		<div id="container" style="" >
			<h2 id="ff">페이지를 찾을 수 없습니다.(404error)</h2>
			
			<p>방문하시려는 페이지의 주소가 잘못 입력되었거나, <br>
			페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다. <br>
			입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다. 
			</p>
			<br/><br/>
			<button onclick="history.back()" id="bu">이전페이지</button>
			<button onclick="location.href='/'" id="bu">COPIN MAP HOME</button>
		</div>
	</div>
</body>
</html>