package com.nilshah.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nilshah.bean.login;
import com.nilshah.bean.sub;

public class DataAdapter 
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataHelper mDbHelper;

    public DataAdapter(Context context) 
    {
        this.mContext = context;
        mDbHelper = new DataHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException 
    {
        try 
        {
            mDbHelper.createDataBase();
        } 
        catch (IOException mIOException) 
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException 
    {
        try 
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } 
        catch (SQLException mSQLException) 
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() 
    {
        mDbHelper.close();
    }
    
    public List<login> getloginDetails()
    {
    	String query="select * from login";
    	Cursor cursor=mDb.rawQuery(query, null);
    	List<login> logindetails = new ArrayList<login>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst())
            {
            	do{
            		login emp=new login();
                    emp.setstudentid(cursor.getString(0).toString());
                    emp.setusernmae(cursor.getString(1).toString());
                    emp.setpassword(cursor.getString(2).toString());
                    logindetails.add(emp);
                    
                }while (cursor.moveToNext());
            }
        }
        return logindetails;
    }
    
    
    public List<String> getSubjectDetails()
    {
    	String query="Select subjectname from subjectdetails";
    	String sub=null;
    	Cursor cursor=mDb.rawQuery(query, null);
    	List<String> details = new ArrayList<String>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst())
            {
            	do{
                    sub=cursor.getString(0).toString();
                    details.add(sub);
                    
                }while (cursor.moveToNext());
            }
        }
        return details;
    }
    public List<String> getRecordDetails(String stdid,String sudid)
    {
    	String query="Select recordfilename from subjectinfo where studentid="+"\""+stdid+"\""
    															+" AND subjectid="+"\""+sudid+"\"";
    	Log.i("query", query);
    	String sub=null;
    	Cursor cursor=mDb.rawQuery(query, null);
    	List<String> details = new ArrayList<String>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst())
            {
            	do{
                    sub=cursor.getString(0).toString();
                    details.add(sub);
                    
                }while (cursor.moveToNext());
            }
        }
        return details;
    }
    
    public void InsertSubjectToDB(String subjectname)
    {
    	String query="Insert INTO subjectdetails (subjectname) values ("+"\""+subjectname+"\""+")";
    	Log.i("Query", query);
    	mDb.execSQL(query);
    }
    
    public void InsertData(String stdid,String subid,String notes,String rname,String rdate)
    {
    	String query="Insert INTO subjectinfo values ("+"\""+stdid+"\""+","
    												   +"\""+subid+"\""+","
    												   +"\""+notes+"\""+","
    												   +"\""+rname+"\""+","
    												   +"\""+rdate+"\""
    												   +")";
    	Log.i("Query", query);
    	mDb.execSQL(query);
    }

    public List<String> getUserNameFromDB(){
        String query = "select DISTINCT(categories) from main";
        Cursor cursor = mDb.rawQuery(query, null);
        String userName = null;
        List<String> categories = new ArrayList<String>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
        do{
                    userName = cursor.getString(0);
                    categories.add(userName);
                }while (cursor.moveToNext());
            }
        }
        return categories;
    }
    
    public List<String> getSubCategory(String cat)
    {
        String query = "select * from main where categories=\'"+ cat + "\'";
        Cursor cursor = mDb.rawQuery(query, null);
        String gcode = null;
        String gtitle = null;
        List<String> categories = new ArrayList<String>();
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
        do{
                    gcode = cursor.getString(0);
                    gtitle=cursor.getString(2);
                    
                    categories.add(gtitle);
                }while (cursor.moveToNext());
            }
        }
        return categories;
    }
    
    public String getSubID(String subjectname)
    {
        String query = "select subjectid from subjectdetails where subjectname=\'"+ subjectname + "\'";
        Cursor cursor = mDb.rawQuery(query, null);
        
        String gcode = null;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
        do{
                    gcode = cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }
        return gcode ;
    }
    
    public String getStudentID(String studentname)
    {
        String query = "select studentid from login where username=\'"+ studentname + "\'";
        Cursor cursor = mDb.rawQuery(query, null);
        
        String gcode = null;
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
        do{
                    gcode = cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }
        return gcode ;
    }
    
  /*  public List<String> getCategories()
    {
    	try
    	{
    		String query="select DISTINCT(categories) from main";
    	Cursor cursor=mDb.rawQuery(query, null);
    	
    	List<String> categories = new ArrayList<String>();;
    	
    	if(cursor.getCount()>0)
    	{
    		while(cursor.moveToNext()){
    		    String uname = cursor.getString(0);
    		    Log.i("Cat", uname);
    		    categories.add(uname);
    		}
    	}
    	return categories;
    	}
    	catch(Exception e){e.printStackTrace();}
		return null;
    	
    }*/
    
}