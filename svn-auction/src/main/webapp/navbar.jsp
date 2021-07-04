<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
<table><tr>
<td><a href="index.jsp">Zurück zur Startseite</a> | </td>
<td><a href="products.jsp">Produkte</a> | </td>
<td><a href="LoginServlet">Logout: <%= session.getAttribute("loggedInUser") %></a></td>
</tr></table>
<% if(session.getAttribute("loggedInUser") == null) { %>
	<jsp:forward page="login.jsp">
		<jsp:param name="reqcamefrom" value="<%= request.getRequestURL() %>"/>
	</jsp:forward>
<% } %>
