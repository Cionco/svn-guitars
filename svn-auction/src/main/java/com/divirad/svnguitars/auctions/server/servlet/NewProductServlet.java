package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import com.divirad.svnguitars.auctions.server.rest.dao.ImgDao;
import com.divirad.svnguitars.auctions.server.rest.dao.ProductDao;
import com.divirad.svnguitars.auctions.server.rest.dto.ImgDTO;
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
		
		Part filePart = request.getPart("image");
		if(filePart == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		InputStream fileStream = filePart.getInputStream();
		while(fileStream.available() != 0)
			try {
				ImgDao.instance.create(p.serial_number, new SerialBlob(fileStream.readAllBytes()));
			} catch (SQLException | IOException ex) {
				ex.printStackTrace();
			}
		
		response.sendRedirect("products.jsp");
		
	}
}