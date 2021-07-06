<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.divirad.svnguitars.auctions.server.rest.dao.*"
    import="com.divirad.svnguitars.auctions.server.rest.dto.*"
    import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SVN - Products</title>
<%! SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); %>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<% for(ProductDTO p : ProductDao.instance.get_open_products()) { %>
  <a href="product.jsp?sn=<%= p.serial_number%>">
    <div class="product-container">
      <div style="height: 400px; width: 400px;">
        <img style="height: inherit;" src="Image/<%= p.serial_number %>">
      </div>
      <div><b><%= p.name %></b></div>
      <div>Auction until: <%= formatter.format(p.auction_end) %></div>
    
    </div>
  </a>
<% } %>
</body>
</html>