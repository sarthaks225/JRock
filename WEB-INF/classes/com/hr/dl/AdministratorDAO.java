package com.hr.dl;
import java.sql.*;

public class AdministratorDAO 
{

public AdministratorDTO getByUserName(String userName) throws DAOException
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;
AdministratorDTO administratorDTO;
try
{
userName=userName.trim();
c=DAOConnection.getConnection();
preparedStatement=c.prepareStatement("select * from administrator where uname=?");
preparedStatement.setString(1,userName);
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Invalid user name : ("+userName+")");

}

administratorDTO=new AdministratorDTO();
administratorDTO.setUserName(resultSet.getString("uname").trim());
administratorDTO.setPassword(resultSet.getString("pwd").trim());

c.close();
preparedStatement.close();
resultSet.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return administratorDTO;

}


}