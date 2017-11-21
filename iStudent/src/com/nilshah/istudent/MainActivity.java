package com.nilshah.istudent;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nilshah.bean.login;
import com.nilshah.database.DataAdapter;


public class MainActivity extends ActionBarActivity
{
	EditText txtuser,txtpwd;
	DataAdapter mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DataAdapter(getBaseContext()); 
              
        mDbHelper.createDatabase();      
        mDbHelper.open();
        
        txtuser=(EditText)findViewById(R.id.txtuser);
        txtpwd=(EditText)findViewById(R.id.txtpwd);
        mDbHelper.close();
    }
    
    
    public void doLogin(View v)
    {
		//Toast.makeText(getApplicationContext(),txtuser.getText().toString() ,Toast.LENGTH_SHORT).show();
    	if(txtuser.getText().toString()=="" && txtpwd.getText().toString()=="")
    	{
    		Toast.makeText(getApplicationContext(), "Please enter login details",Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    		List<login> login =null;
    		mDbHelper.open();
    		login=mDbHelper.getloginDetails();
    		
    		for(int i=0;i<login.size();i++)
    		{
    			/*Log.i("username", login.get(i).getusername().toString());
    			Log.i("username", txtuser.getText().toString());
    			Log.i("password", login.get(i).getpassword().toString());
    			Log.i("username", txtpwd.getText().toString());
    			String a=login.get(i).getusername().toString();
    			String b=txtuser.getText().toString();*/
    			
    			if(login.get(i).getusername().toString().equalsIgnoreCase(txtuser.getText().toString()))
    			{
    				if(login.get(i).getpassword().toString().equalsIgnoreCase(txtpwd.getText().toString()))
    				{
    					SharedPreferences.Editor editor = getSharedPreferences("MyData", MODE_PRIVATE).edit();
    					 editor.putString("username", txtuser.getText().toString());
    					 editor.commit();
    					Intent intent=new Intent(MainActivity.this, main.class);
    					startActivity(intent);
    					mDbHelper.close();
    				}
    			}
    			else
    			{
    				Toast.makeText(getApplicationContext(), "Wrong Username and password", Toast.LENGTH_SHORT).show();
    			}
    		}
    		
    		
    	}
    }

}
