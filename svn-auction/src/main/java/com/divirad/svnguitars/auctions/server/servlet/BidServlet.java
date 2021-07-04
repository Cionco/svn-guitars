package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divirad.svnguitars.auctions.server.rest.dao.BidDao;
import com.divirad.svnguitars.auctions.server.rest.dto.BidDTO;
import com.divirad.svnguitars.auctions.server.rest.dto.UserDTO;

/**
 * Servlet implementation class BidServlet
 */
@WebServlet("/BidServlet")
public class BidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		BidDao.instance.bid(
				Integer.parseInt(request.getParameter("product")), 
				((UserDTO) request.getSession().getAttribute("loggedInUser")).user_name, 
				Double.parseDouble(request.getParameter("amount")));
		
		response.sendRedirect("product.jsp?id=" + request.getParameter("product"));
	}

}
