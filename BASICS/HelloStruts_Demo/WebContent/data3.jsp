<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/data3.action" method="post">
		No1:<input type="text" name="user.no1" /><br/>
		No2:<input type="text" name="user.no2" /><br/>
		No3:<input type="text" name="user.no3" /><br/>
		<input type="submit" name="submit" />
	</form>
</body>
</html>