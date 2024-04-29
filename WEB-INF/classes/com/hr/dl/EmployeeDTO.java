package com.hr.dl;
import java.math.*;
import java.util.*;

public class EmployeeDTO implements java.io.Serializable,Comparable<EmployeeDTO>
{
private int employeeId;
private String name;
private int code;
private String title;
private Date dateOfBirth;
private String gender;
private boolean isIndian;
private BigDecimal basicSalary;
private String panNumber;
private String aadharCardNumber;

public EmployeeDTO()
{
this.employeeId=0;
this.name="";
this.code=0;
this.title="";
this.dateOfBirth=null;
this.gender="";
this.isIndian=false;
this.basicSalary=null;
this.panNumber="";
this.aadharCardNumber="";
}
public void setEmployeeId(int employeeId)
{
this.employeeId=employeeId;
}
public int getEmployeeId()
{
return this.employeeId;
}

public void setId(int employeeId)
{
this.employeeId=employeeId;
}
public int getId()
{
return this.employeeId;
}

public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(java.lang.String title)
{
this.title=title;
}
public java.lang.String getTitle()
{
return this.title;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(java.lang.String gender)
{
this.gender=gender;
}
public java.lang.String getGender()
{
return this.gender;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setBasicSalary(java.math.BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public java.math.BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setPANNumber(java.lang.String panNumber)
{
this.panNumber=panNumber;
}
public java.lang.String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(java.lang.String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public java.lang.String getAadharCardNumber()
{
return this.aadharCardNumber;
}

public boolean equals(Object object)
{

if(!(object instanceof EmployeeDTO)) return false;
return this.employeeId==((EmployeeDTO)object).getEmployeeId();

}

public int compareTo(EmployeeDTO other)
{
return this.title.compareToIgnoreCase(other.getName());
}

public int hashCode()
{
return this.employeeId;
}

}