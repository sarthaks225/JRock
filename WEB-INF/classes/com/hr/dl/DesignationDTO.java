package com.hr.dl;

public class DesignationDTO implements java.io.Serializable,Comparable<DesignationDTO>
{
private int code;
private String title;

public DesignationDTO()
{
this.code=0;
this.title="";
}

public void setCode(int code)
{
this.code=code;
}

public void setTitle(String title)
{
this.title=title;
}

public int getCode()
{
return code;
}

public String getTitle()
{
return title;
}

public boolean equals(Object object)
{
if(!(object instanceof DesignationDTO)) return false;
return this.code==((DesignationDTO)object).getCode();
}

public int compareTo(DesignationDTO other)
{
return this.title.compareToIgnoreCase(other.getTitle());
}

public int hashCode()
{
return this.code;
}

}