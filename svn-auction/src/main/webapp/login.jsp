<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <form method="post" action="LoginServlet">
    <div><input type="text" name="user_name" placeholder="User Name"/></div>
    <div><input type="password" name="password"/></div>
    <div><button type="submit">Login</button></div>
    <div><input type="hidden" name="reqcamefrom" value="<%= request.getParameter("reqcamefrom") %>"></div>
  </form>
  <div><p>Noch kein Account? <a href="register.jsp">Hier</a> registrieren</p></div>
</body>
</html>