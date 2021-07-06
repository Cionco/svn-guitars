<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SVN - New Product</title>
</head>
<body>

	<form method="post" action="Products" enctype="multipart/form-data">
	  <div><input type="text" name="name" placeholder="Name"></div>
	  <div><textarea name="description" placeholder="Description" rows=15 cols=150></textarea></div>
	  <div><input type="text" name="serial_number" placeholder="Serial Number"></div>
	  <div><input type="text" name="auction_start" placeholder="Start yyyy-MM-dd"></div>
	  <div><input type="text" name="auction_end" placeholder="End yyyy-MM-dd"></div>
	  <div><input type="file" name="image"></div>
	  <div><button type="submit">Create</button></div>
	</form>

</body>
</html>