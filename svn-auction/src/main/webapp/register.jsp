<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SVN - Register</title>
</head>
<body>

  <form method="post" action="RegisterServlet">
    <input type=text name="user_name" placeholder="User Name"><br>
    <input type="email" name="email" placeholder="Email Address"><br>
    <input type=text name="first_name" placeholder="First Name"><br>
    <input type=text name="last_name" placeholder="Last Name"><br>
    <input type=password name="password" placeholder="Password"><br>
    <button type="submit">Submit</button>
  </form>

</body>
</html>