package com.hr.dl;
import java.sql.*;
import com.hr.dl.*;

public class DAOConnection 
{


private DAOConnection(){}

public static Connection getConnection() throws DAOException
{
Connection c=null;
try
{

Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return c;

}

}