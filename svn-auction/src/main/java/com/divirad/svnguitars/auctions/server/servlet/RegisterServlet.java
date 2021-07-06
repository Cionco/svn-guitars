package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divirad.svnguitars.auctions.server.rest.dao.UserDao;
import com.divirad.svnguitars.auctions.server.rest.dto.UserDTO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDTO user = new UserDTO( 
				request.getParameter("user_name"), 
				request.getParameter("password"),
				request.getParameter("email"),
				request.getParameter("first_name"), 
				request.getParameter("last_name"));
		
		if(UserDao.instance.is_username_available(user))
			if(UserDao.instance.register(user))
				response.sendRedirect("index.jsp");
			else
				response.sendRedirect("register.jsp");
		else
			response.sendRedirect("register.jsp");
				
		
	}

}
