<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.divirad.svnguitars.auctions.server.rest.dao.ProductDao"
    import="com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <title>Insert title here</title>
  <% ProductDTO p = ProductDao.instance.get_product_by_id(Integer.parseInt(request.getParameter("id"))); %>
</head>
<body>
  <jsp:include page="navbar.jsp"/>

  <div style="height: 400px; width: 400px; background-color: red;">
  	
  </div>
  <div><b><%= p.name %></b></div>
  <div><textarea disabled rows=15 cols=150><%= p.description %></textarea></div>
  <div>Up for auction until: <%= p.auction_end %></div>
  
</body>
</html>