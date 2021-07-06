package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divirad.svnguitars.auctions.server.rest.dao.ProductDao;
import com.divirad.svnguitars.auctions.server.rest.dto.ProductDTO;

/**
 * Servlet implementation class NewProductServlet
 */
@WebServlet({"/NewProductServlet", "/NewProductServlet/*"})
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
}
