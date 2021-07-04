package com.divirad.svnguitars.auctions.server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


/**
 * Application Lifecycle Listener implementation class DbPmPlusContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static DataSource dataSource;

	/**
	 * Default constructor. 
	 */
	public ContextListener() {
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
		System.out.println("und tschuess...");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event)  { 
		System.getProperties().setProperty("Dorg.apache.cxf.stax.allowInsecureParser", "1");
		System.out.println("sys prop set");

		dataSource = getDBConnection();
	}
	
	private static DataSource getDBConnection() {
		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/AuctionDb");
			System.out.println("Database Connection created successfully");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ds;

	}

	public static DataSource getDataSource() {
		return dataSource;
	}
	
	/*public static boolean isUserAuthentic(String username, String passwordFromForm) {		
		User u = UserDao.instance.getUserByMail(username);
		if(u == null || u.token != null) return false;

		return passwordFromForm != null && PasswordUtils.doesPasswordMatch(passwordFromForm, u.pwhash, u.pwsalt);
	}*/
}
