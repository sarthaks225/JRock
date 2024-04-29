package com.hr.servlets;
import javax.servlet.http.*;
import javax.servlet.*;
import com.hr.dl.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
public class ServletOne extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			
		}catch(Exception e)
		{
			//do nothing
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			DesignationDAO designationDAO=new DesignationDAO();
			PrintWriter pw=response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			List<DesignationDTO> designations=designationDAO.getAll();
			
			Gson gson=new Gson();
			String jsonString=gson.toJson(designations);
			pw.print(jsonString);
			pw.flush();
			System.out.println("servlet one done");
			
		}
		catch(Exception e)
		{
			try
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}catch(IOException ioe)
			{
				
				// do nothing
			}
		}
		
		
	}
	
	
	
}