<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SVN - New Product</title>
</head>
<body>

	<form method="post" action="NewProductServlet">
	  <div><input type="text" name="name" placeholder="Name"></div>
	  <div><textarea name="description" placeholder="Description"></div>
	  <div></div>
	  <div><input type="date" name="auction_start"></div>
	  <div><input type="date" name="auction_end"></div>
	  <div><button type="submit">Create</button></div>
	</form>

</body>
</html>