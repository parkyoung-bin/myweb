<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	${requestScope.vo.id }<br>
	${requestScope.vo.name }<br>
	<hr>
	<!-- request는 requestScope를 생략해서 사용 가능 -->
	${vo.id }<br>
	${vo.name }<br>
</body>
</html>