package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Enumeration;

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
import com.divirad.svnguitars.auctions.server.rest.dto.ImgDTO;

/**
 * Servlet implementation class Image
 */
@WebServlet({"/Image", "/Image/*"})
@MultipartConfig
public class Image extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] res = request.getRequestURI().replace(request.getServletPath(), "")
				.replace(request.getServletContext().getContextPath() + "/", "").split("/");
		int order_id = 0;
		if(res.length != 1) order_id = Integer.parseInt(res[1]);
		ImgDTO i = ImgDao.instance.get_image_by_order(res[0], order_id);
		response.setContentType("image/jpg");
		ServletOutputStream out = response.getOutputStream();

		response.setContentType("image/gif");
		InputStream in = null;
		int length = 0;
		try {
			in = i.img.getBinaryStream();

			length = (int) i.img.length();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}

		in.close();
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String serial_number = request.getParameter("serial_number");
		
		Part filePart = request.getPart("image");
		if(filePart == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		InputStream fileStream = filePart.getInputStream();
		while(fileStream.available() != 0)
			try {
				ImgDao.instance.create(serial_number, new SerialBlob(fileStream.readAllBytes()));
			} catch (SQLException | IOException ex) {
				ex.printStackTrace();
			}
		
		response.sendRedirect("product.jsp?sn=" + serial_number);
	}

}
