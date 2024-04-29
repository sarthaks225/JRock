package com.hr.dl;
import java.util.*;
import java.sql.*;
import java.math.*;

public class EmployeeDAO
{



public List<EmployeeDTO> getAll() throws DAOException
{

LinkedList<EmployeeDTO> employees;
Statement statement;
ResultSet resultSet;
Connection c;

try
{
employees=new LinkedList<>();
c=DAOConnection.getConnection();

statement=c.createStatement();

resultSet=statement.executeQuery("select employee.employee_Id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_Indian,employee.basic_salary,employee.pan_Number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code");

int employeeId;
String name;
int code;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;

EmployeeDTO employeeDTO;

while(resultSet.next())
{
employeeId=resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
code=resultSet.getInt("designation_code");
title=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender").trim();
isIndian=resultSet.getBoolean("is_Indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setCode(code);
employeeDTO.setTitle(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);

}

statement.close();
resultSet.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return employees;
}

public void add(EmployeeDTO employee) throws DAOException
{
String panNumber="";
String aadharCardNumber="";

try
{

panNumber=employee.getPANNumber();

PreparedStatement preparedStatement;
ResultSet resultSet;
Connection c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select employee_id from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
c.close();
throw new DAOException("PAN Number : "+panNumber+" already exists");
}

resultSet.close();
preparedStatement.close();

preparedStatement=c.prepareStatement("select employee_id from employee where aadhar_card_number=?");
aadharCardNumber=employee.getAadharCardNumber();
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();

if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
c.close();
throw new DAOException("Aadhar Card Number : "+aadharCardNumber+" already exists");

}

resultSet.close();
preparedStatement.close();


preparedStatement=c.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_Indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getCode());

java.util.Date date=employee.getDateOfBirth();
java.sql.Date sqlDate=new java.sql.Date(date.getYear(),date.getMonth(),date.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,employee.getPANNumber());
preparedStatement.setString(8,employee.getAadharCardNumber());

preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
employee.setEmployeeId(resultSet.getInt(1));

resultSet.close();
preparedStatement.close();
c.close();

}catch(SQLException sqle)
{
System.out.println(sqle.getMessage());
}


}

public boolean panNumberExists(String panNumber) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean result=false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;

c=DAOConnection.getConnection();
preparedStatement=c.prepareStatement("select name from employee where pan_number=(?)");
preparedStatement.setString(1,panNumber);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true) result=true;

c.close();
preparedStatement.close();
resultSet.close();

return result;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}


public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean result=false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;

c=DAOConnection.getConnection();
preparedStatement=c.prepareStatement("select name from employee where aadhar_card_number=(?)");
preparedStatement.setString(1,aadharCardNumber);

resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true) result=true;
return result;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}

public void delete(int id) throws DAOException
{

try
{
Connection c;
PreparedStatement preparedStatement;
ResultSet resultSet;

c=DAOConnection.getConnection();
preparedStatement=c.prepareStatement("select gender from employee where employee_id=(?)");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Employee Id :"+id+" not exists");
}

preparedStatement.close();
resultSet.close();
preparedStatement=c.prepareStatement("delete from employee where employee_id=(?)");
preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
c.close();
preparedStatement.close();
resultSet.close();


}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

}

public EmployeeDTO getById(int id) throws DAOException
{
Connection c=null;
Statement statement=null;
ResultSet resultSet=null;
EmployeeDTO employeeDTO=null;

try
{
c=DAOConnection.getConnection();

statement=c.createStatement();

resultSet=statement.executeQuery("select employee.employee_Id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_Indian,employee.basic_salary,employee.pan_Number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where employee_id="+id);

int employeeId;
String name;
int code;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;

if(resultSet.next()==false)
{
c.close();
statement.close();
resultSet.close();
throw new DAOException("Employee id "+id+" not exists");
}

employeeId=resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
code=resultSet.getInt("designation_code");
title=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender").trim();
isIndian=resultSet.getBoolean("is_Indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setCode(code);
employeeDTO.setTitle(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);


statement.close();
resultSet.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return employeeDTO;

}

public void update(EmployeeDTO employee) throws DAOException
{

try
{

Connection c=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;

int id=employee.getEmployeeId();
String panNumber;
String aadharCardNumber;

preparedStatement=c.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();

if(resultSet.next()==false)
{
c.close();
preparedStatement.close();;
resultSet.close();
throw new DAOException("Employee id : "+id+" not exists");

}


preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
panNumber=employee.getPANNumber();
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,id);
resultSet=preparedStatement.executeQuery();


if(resultSet.next()==true)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("PAN number :"+panNumber+" exists");
}



preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
aadharCardNumber=employee.getAadharCardNumber();
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,id);
resultSet=preparedStatement.executeQuery();


if(resultSet.next()==true)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Aadhar card number :"+aadharCardNumber+" exists");
}

preparedStatement.close();
resultSet.close();

preparedStatement=c.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_Indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getCode());
java.util.Date date=employee.getDateOfBirth();
java.sql.Date dateOfBirth=new java.sql.Date(date.getYear(),date.getMonth(),date.getDate());
preparedStatement.setDate(3,dateOfBirth);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,id);

preparedStatement.executeUpdate();

c.close();
preparedStatement.close();

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}


}


public boolean employeeIdExists(int id) throws DAOException
{
Connection c=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
boolean result=false;
try
{
c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,id);


resultSet=preparedStatement.executeQuery();

result=resultSet.next();
c.close();
preparedStatement.close();
resultSet.close();

}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return result;

}



public EmployeeDTO getByPANNumber(String panNumber) throws DAOException
{
Connection c=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
EmployeeDTO employeeDTO=null;

try
{
c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select employee.employee_Id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_Indian,employee.basic_salary,employee.pan_Number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where pan_number=?");
preparedStatement.setString(1,panNumber);


resultSet=preparedStatement.executeQuery();

int employeeId;
String name;
int code;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;

if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("PAN Number "+panNumber+" not exists");
}

employeeId=resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
code=resultSet.getInt("designation_code");
title=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender").trim();
isIndian=resultSet.getBoolean("is_Indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setCode(code);
employeeDTO.setTitle(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);


preparedStatement.close();
resultSet.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return employeeDTO;

}


public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
Connection c=null;
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
EmployeeDTO employeeDTO=null;

try
{
c=DAOConnection.getConnection();

preparedStatement=c.prepareStatement("select employee.employee_Id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_Indian,employee.basic_salary,employee.pan_Number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);


resultSet=preparedStatement.executeQuery();

int employeeId;
String name;
int code;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;

if(resultSet.next()==false)
{
c.close();
preparedStatement.close();
resultSet.close();
throw new DAOException("Aadhar Card Number "+aadharCardNumber+" not exists");
}

employeeId=resultSet.getInt("employee_id");
name=resultSet.getString("name").trim();
code=resultSet.getInt("designation_code");
title=resultSet.getString("title").trim();
dateOfBirth=resultSet.getDate("date_of_birth");
gender=resultSet.getString("gender").trim();
isIndian=resultSet.getBoolean("is_Indian");
basicSalary=resultSet.getBigDecimal("basic_salary");
panNumber=resultSet.getString("pan_number").trim();
aadharCardNumber=resultSet.getString("aadhar_card_number").trim();

employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setCode(code);
employeeDTO.setTitle(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);


preparedStatement.close();
resultSet.close();
c.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}

return employeeDTO;

}





}