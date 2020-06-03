<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<form action ="../MultiUploadServlet" enctype ="multipart/form-data" method="post">
	파일1:<input type = "file" name ="file01" > <Br>
	파일2:<input type = "file" name ="file02" > <Br>
	파일3:<input type = "file" name ="file03" > <Br>
	
	<input type = "submit" value ="업로드" >
	
	</form>
</body>
</html>