<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.divirad.svnguitars.auctions.server.rest.dao.ProductDao"
    import="com.divirad.svnguitars.auctions.server.rest.dao.BidDao"
    import="com.divirad.svnguitars.auctions.server.rest.dao.ImgDao"
    import="com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  <% String product = request.getParameter("sn"); %>
  <% ProductDTO p = ProductDao.instance.get_product_by_serial_number(product); %>
  <% if(p ==  null) request.getRequestDispatcher("products.jsp").forward(request, response);%>
  <title>SVN - <%= p.name %></title>
</head>
<body>
  <jsp:include page="navbar.jsp"/>

  <div style="height: 400px; width: 400px;">
  	<img style="height: inherit;" src="Image/<%= p.serial_number %>"/>
  </div>
  <div><b><%= p.name %></b></div>
  <div><textarea disabled rows=15 cols=150><%= p.description %></textarea></div>
  <div>Up for auction until: <%= p.auction_end %></div>
  
  <hr>
  
  <div>
  	<div>Count of bids: <%= BidDao.instance.get_bid_count_for_product(product) %></div>
  	<div>Currently Highest Bid: <%= BidDao.instance.get_highest_bid_for_product(product) %></div>
  	<div><form method="post" action="BidServlet"><input type="number" name="amount"><input type="hidden" name="product" value="<%= product %>"><button type="submit">Place Bid</button></form></div>
  </div>
  
  <hr>
  
  <div>
  	<% for(int i = 1; i < ImgDao.instance.get_image_count_for_product(product); i++) { %>
  	  <div style="height: 200px; width: 200px; margin: 10px;">
  		<img style="height: inherit;" src="Image/<%= p.serial_number %>/<%= i %>"/>
  	  </div>
  	<% } %>
  </div>
  
</body>
</html>