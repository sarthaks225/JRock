package com.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import com.hr.dl.*;
import java.io.*;
import com.google.gson.*;

public class ServletTwo extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}catch(IOException ioe)
		{
			//do nothing
		}
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			int code=Integer.parseInt(request.getParameter("code"));
			DesignationDAO designationDAO=new DesignationDAO();

			PrintWriter pw=response.getWriter();
			try
			{
			DesignationDTO designationDTO=designationDAO.getByCode(code);
			Gson gson=new Gson();
			String jsonString=gson.toJson(designationDTO);
			System.out.println(jsonString);
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			pw.print(jsonString);
			pw.flush();
			}catch(DAOException daoe)
			{
				pw.print("Invalid");
			}
		}catch(Exception e)
		{
			try
			{
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}catch(IOException ioe)
			{
				//do nothing
			}
			
			
		}
		
	}
	
	
	
}