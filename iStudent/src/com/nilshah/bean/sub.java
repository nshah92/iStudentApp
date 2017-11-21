package com.nilshah.bean;

public class sub 
{
	public String subjectid;
	public String subjectname;
	
	public sub()
	{
		
	}
	public sub(String id, String name)
	{
		this.subjectid=id;
		this.subjectname=name;
	}
	
	public String getsubjectid()
	{
		return subjectid;
	}
	public void setsubjectid(String id)
	{
		this.subjectid=id;
	}
	
	public String getsubjectname()
	{
		return subjectname;
	}
	public void setsubjectname(String name)
	{
		this.subjectid=name;
	}
}
