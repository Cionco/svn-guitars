package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.divirad.svnguitars.auctions.server.rest.dao.BidDao;
import com.divirad.svnguitars.auctions.server.rest.dao.ImgDao;
import com.divirad.svnguitars.auctions.server.rest.dao.ProductDao;
import com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO;

/**
 * Servlet implementation class NewProductServlet
 */
@WebServlet({"/NewProductServlet", "/Products", "/Products/*"})
@MultipartConfig
public class NewProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ctx = request.getServletContext().getContextPath(); // svn-auction
		if(request.getSession().getAttribute("loggedInUser") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		String res = request.getRequestURI().replace(request.getServletPath(), "")
				.replace(ctx, "");
		if(res.startsWith("/")) res = res.substring(1);

		if(res.length() == 0) build_products_page(request, response);
		else build_product_page(res, request, response);
	}
	
	private void build_products_page(HttpServletRequest request, HttpServletResponse r) throws IOException, ServletException {
		String ctx = request.getServletContext().getContextPath(); // svn-auction
		write(r, "<html>");
		write(r, "  <head>");
		write(r, "    <meta charset=\"ISO-8859-1\">");
		write(r, "    <title>SVN - Products</title>");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		write(r, "  </head>");
		write(r, "  <body>");
		build_navbar(request, r);		
		for(ProductDTO p : ProductDao.instance.get_open_products()) {
		write(r, "    <a href=\"" + ctx + "/Products/" + p.serial_number +"\">");
		write(r, "      <div class=\"product-container\">");
		write(r, "        <div style=\"height: 400px; width: 400px;\">");
		write(r, "          <img style=\"height: inherit;\" src=\"" + ctx + "/Image/" + p.serial_number + "\">");
		write(r, "        </div>");
		write(r, "        <div><b>" + p.name + "</b></div>");
		write(r, "        <div>Auction until: " + formatter.format(p.auction_end) + "</div>");
		write(r, "      </div>");
		write(r, "    </a>");
		}
		write(r, "  </body>");
		write(r, "</html>");
		
	}
	
	private void build_product_page(String serial_number, HttpServletRequest request, HttpServletResponse r) throws ServletException, IOException {
		String ctx = request.getServletContext().getContextPath(); // svn-auction
		write(r, "<html>");
		write(r, "  <head>");
		write(r, "    <meta charset=\"ISO-8859-1\">");
		ProductDTO p = ProductDao.instance.get_product_by_serial_number(serial_number);
		write(r, "  <title>SVN - " + p.name + "</title>");
		write(r, "</head>");
		write(r, "<body>");
		build_navbar(request, r);
		write(r, "  <div style=\"height: 400px; width: 400px;\">");
		write(r, "  	<img style=\"height: inherit;\" src=\"" + ctx + "/Image/" + p.serial_number + "\"/>");
		write(r, "  </div>");
		write(r, "  <div><b>" + p.name + "</b></div>");
		write(r, "  <div><textarea disabled rows=15 cols=150>" + p.description + "</textarea></div>");
		write(r, "  <div>Up for auction until: " + p.auction_end + "</div>");
		write(r, "  <hr>");
		write(r, "  <div>");
		write(r, "  	<div>Count of bids: " + BidDao.instance.get_bid_count_for_product(serial_number) + "</div>");
		write(r, "  	<div>Currently Highest Bid: " + BidDao.instance.get_highest_bid_for_product(serial_number) + "</div>");
		write(r, "  	<div><form method=\"post\" action=\"BidServlet\"><input type=\"number\" name=\"amount\"><input type=\"hidden\" name=\"product\" value=\"" + serial_number + "\"><button type=\"submit\">Place Bid</button></form></div>");
		write(r, "  </div>");
		write(r, "  <hr>");
		write(r, "  <div>");
		for(int i = 1; i < ImgDao.instance.get_image_count_for_product(serial_number); i++) {
		write(r, "  	  <div style=\"height: 200px; width: 200px; margin: 10px;\">");
		write(r, "  		<img style=\"height: inherit;\" src=\"" + ctx + "/Image/" + p.serial_number + "/" + i + "\"/>");
		write(r, "  	  </div>");
		}
		write(r, "    </div>");
		write(r, "  </body>");
		write(r, "</html>");
	}
	
	private void build_navbar(HttpServletRequest request, HttpServletResponse r) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Object loggedInUser = session.getAttribute("loggedInUser");
		String ctx = request.getServletContext().getContextPath();
		
		write(r, "<table><tr>");
		write(r, "<td><a href=\".\">Produkte</a> | </td>");
		write(r, "<td><a href=\"" + ctx + "/LoginServlet\">Logout: " + loggedInUser + "</a></td>");
		write(r, "</tr></table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=UTF-8");
		
		
		ProductDTO p = new ProductDTO( 
				request.getParameter("serial_number"),
				request.getParameter("name"),
				request.getParameter("description"),
				Date.valueOf(request.getParameter("auction_start")),
				Date.valueOf(request.getParameter("auction_end")));
		
		ProductDao.instance.create(
				p.serial_number, 
				p.name, 
				p.description, 
				p.auction_start, 
				p.auction_end);
		
		RequestDispatcher rd = request.getRequestDispatcher("Image");
		rd.forward(request, response);
		
	}
	
	private void write(HttpServletResponse response, String html) throws IOException {
		response.getWriter().append(html);
	}
}
