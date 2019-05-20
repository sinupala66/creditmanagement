package com.rusia.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UserRegd")
public class UserRegd extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public UserRegd() 
    {
        super();
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		Connection conn = null;
		PreparedStatement ps = null;
		String message = null;
	    String qry = "insert into CUSTOMER_TAB (CUS_ID, CUS_NAME, CUS_PRODUCT, CUS_BRAND, CUS_CONTACT) values(?,?,?,?,?)";
	    String esid = request.getParameter("cus_id");
	    int cus_id = Integer.parseInt(esid);
		String cus_name = request.getParameter("cus_name");
		String cus_product = request.getParameter("cus_product");
		String cus_brand = request.getParameter("cus_brand");
		String cus_contact = request.getParameter("cus_contact");
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Class Loadded");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","root","tiger");
			System.out.println("Connection established..");
			ps = conn.prepareStatement(qry);
			ps.setInt(1, cus_id);
			ps.setString(2, cus_name);
			ps.setString(3, cus_product);
			ps.setString(4, cus_brand);
			ps.setString(5, cus_contact);
			
			int count;
			count = ps.executeUpdate();
			if(count  > 0)
			{	
				HttpSession session = request.getSession();
				session.setAttribute("cus_name", "cus_name");
				message = "registered  successfully";
			}
		}	
		catch (ClassNotFoundException | SQLException ex) 
		{
			message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
		}
		finally 
		{
			if(conn!=null)
			{
				try 
				{
					conn.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	 request.setAttribute("Message", message);
	 getServletContext().getRequestDispatcher("/message2.jsp").forward(request, response);
	}

}
