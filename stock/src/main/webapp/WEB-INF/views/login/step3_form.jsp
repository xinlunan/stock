<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
step2<br>
<form action="${pageContext.request.contextPath}/trade/login/step4" method="post">
牛卡号:<input name="account" value="${account}"/><br>
密码:<input name="password" value="${password}" type="password"/><br>
校验码:<input name="code"/><br>
<img src="${pageContext.request.contextPath}/images/verify/verify.jpg?random=<%=System.currentTimeMillis()%>">
<input type="submit" value="确定">
</form>
<br>
<a href="${pageContext.request.contextPath}/trade/login/step1">setp1</a>
<div style="color:red">${msg}</div>
</body>
</html>