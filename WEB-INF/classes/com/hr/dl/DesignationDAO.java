package com.hr.dl;
import java.util.*;
import java.sql.*;

public class DesignationDAO
{

public void add(DesignationDTO designationDTO) throws DAOException
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;

try
{
c=DAOConnection.getConnection();
preparedStatement=c.prepareStatement("select title from designation where title=(?)");
preparedStatement.setString(1,designationDTO.getTitle());
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==true)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Designation "+designationDTO.getTitle()+" exists");
}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("insert into designation (title) value(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,designationDTO.getTitle());
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
designationDTO.setCode(resultSet.getInt(1));
preparedStatement.close();
resultSet.close();
c.close();

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}


public List<DesignationDTO> getAll() throws DAOException
{

LinkedList<DesignationDTO> designations;
Statement statement;
ResultSet resultSet;
Connection c;

try
{
designations=new LinkedList<>();
c=DAOConnection.getConnection();

statement=c.createStatement();

resultSet=statement.executeQuery("select * from designation");

int code;
String title;
DesignationDTO designationDTO;

while(resultSet.next())
{
code=resultSet.getInt("code");
title=resultSet.getString("title").trim(); // trim is vvv important, if the length of string !=32 then spaces will fill the length
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}

statement.close();
resultSet.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return designations;
}

public DesignationDTO getByCode(int code) throws DAOException
{

try
{
Connection c=DAOConnection.getConnection();

PreparedStatement preparedStatement=c.prepareStatement("select * from designation where code=(?)");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Invalid designaiton code : "+code);
}

DesignationDTO designation=new DesignationDTO();
designation.setCode(code);
designation.setTitle(resultSet.getString("title").trim());

c.close();
preparedStatement.close();
resultSet.close();
return designation;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}

public void update(DesignationDTO designation) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;
int code;
String title;

code=designation.getCode();
title=designation.getTitle().trim();

c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select * from designation where code=(?)");
preparedStatement.setInt(1,code);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Invalid designaiton code : "+code);
}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("select * from designation where title=(?) and code!=(?)");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException(title+" already exists in designation");

}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("update designation set title=(?) where code=(?)");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();

c.close();
preparedStatement.close();
resultSet.close();

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}





}


public void delete(int code) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;

c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select * from designation where code=(?)");
preparedStatement.setInt(1,code);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Invalid designaiton code : "+code);
}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("select employee_id from employee where designation_code=(?)");
preparedStatement.setInt(1,code);

resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Can not delete designation, designation alloted to employee");
}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("delete from designation where code=(?)");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();

c.close();
preparedStatement.close();

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}


}


public boolean codeExists(int code) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;
c=DAOConnection.getConnection();
boolean result=true;

preparedStatement=c.prepareStatement("select title from designation where code=(?)");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false) result=false;

c.close();
preparedStatement.close();
resultSet.close();
return result;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}



}