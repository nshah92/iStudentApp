package com.nilshah.bean;

import android.R.string;

public class login 
{
	public String username;
	public String password;
	public String studentid;
	
	public login()
	{
		
	}
	
	public login(String id,String uname,String pass)
	{
		this.studentid=id;
		this.username=uname;
		this.password=pass;
	}
	
	public String getstudentid()
	{
		return studentid;
	}
	public void setstudentid(String id)
	{
		this.studentid=id;
	}
	
	public String getusername()
	{
		return username;
	}
	public void setusernmae(String uname)
	{
		this.username=uname;
	}
	
	public String getpassword()
	{
		return password;
	}
	public void setpassword(String pass)
	{
		this.password=pass;
	}
	
}
