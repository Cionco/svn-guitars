<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.divirad.svnguitars.auctions.server.rest.dao.ProductDao"
    import="com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <title>SVN - Add Image</title>
  <% String product = request.getParameter("sn"); %>
  <% ProductDTO p = ProductDao.instance.get_product_by_serial_number(product); %>
</head>
<body>
	<form method="post" action="Image" enctype="multipart/form-data">
		<input type="hidden" name="serial_number" value="<%= product %>">
		<input type="file" name="image">
		<button type="submit">Add image to <%= p.name %></button>
	</form>
  
</body>
</html>