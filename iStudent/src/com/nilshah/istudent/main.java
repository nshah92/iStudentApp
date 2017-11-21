package com.nilshah.istudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class main extends ActionBarActivity
{
	Button btnrecord,btnlisten,btnschedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	public void callRecord(View v)
	{
		Intent intent=new Intent(main.this,record.class);
		startActivity(intent);
	}
	public void callListen(View v)
	{
		Intent intent=new Intent(main.this,listen.class);
		startActivity(intent);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
        	Intent i=new Intent(main.this,subject.class);
        	startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	

}
