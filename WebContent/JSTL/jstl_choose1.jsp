<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- choose 구문으로 90이상 A, 80 B, 70 C 나머지 f
90이상 choose 구문 더 사용해서 95이상 A+ --%>
<c:choose> 
	<c:when test="${param.point >= 90 }">
	A
	<c:choose>
	<c:when test="${param.point>=95 }">A+</c:when>
	</c:choose>
	 
	</c:when>
	
	<c:when test="${param.point >=80 }">B</c:when>
	<c:when test="${param.point >=70 }">C</c:when>
	<c:otherwise>F</c:otherwise>
	



</c:choose>
</body>
</html>