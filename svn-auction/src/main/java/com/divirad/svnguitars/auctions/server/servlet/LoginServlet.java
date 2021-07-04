package com.divirad.svnguitars.auctions.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.divirad.svnguitars.auctions.server.rest.dao.UserDao;
import com.divirad.svnguitars.auctions.server.rest.dto.UserDTO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("loggedInUser");
		session.invalidate();
		
		response.sendRedirect((request.getParameter("reqcamefrom") == null)?request.getHeader("referer"):request.getParameter("reqcamefrom"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		UserDTO u = new UserDTO(request.getParameter("user_name"), request.getParameter("password"), null, null);
		if(UserDao.instance.check_login_creds(u)) {
			session.setAttribute("loggedInUser", u);
		}
		else {
			session.removeAttribute("loggedInUser");
			
			session.invalidate();
		}
		
		response.sendRedirect((request.getParameter("reqcamefrom") == null)?request.getHeader("referer"):request.getParameter("reqcamefrom"));
	}

}
