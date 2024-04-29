package com.hr.dl;

public class AdministratorDTO implements java.io.Serializable
{

private String userName;
private String password;

public AdministratorDTO()
{
this.userName="";
this.password="";
}

public void setUserName(String userName)
{
this.userName=userName;
}

public void setPassword(String password)
{
this.password=password;
}

public String getUserName()
{
return this.userName;
}

public String getPassword()
{
return this.password;
}


}

