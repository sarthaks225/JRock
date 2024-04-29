package com.hr.servlets;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.*;
public class ServletThree extends HttpServlet
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}catch(IOException ioe)
		{
			//do nothing
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			BufferedReader br=request.getReader();
			StringBuffer b=new StringBuffer();
			String d;
			while(true)
			{
				d=br.readLine();
				if(d==null) break;
				b.append(d);
			}
			System.out.println(b);
			
			String rawData=b.toString();
			Gson gson=new Gson();
			Customer c=gson.fromJson(rawData,Customer.class);
			
			
			PrintWriter pw=response.getWriter();
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			pw.print(gson.toJson(c));	
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