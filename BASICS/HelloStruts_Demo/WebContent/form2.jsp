<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/form2.action" method="post">
		Como de llamas:<input type="text" name="name"/></br>
		你的名字是：<input type="text" name="cname" /></br>
		年龄：<input type="text" name="age" /></br>
		<input type="submit" />
	</form>
	
</body>
</html>