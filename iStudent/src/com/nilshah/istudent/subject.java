package com.nilshah.istudent;

import com.nilshah.database.DataAdapter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class subject extends ActionBarActivity 
{	
		EditText txtsubjectname;
		DataAdapter mDbHelper;

	 	@Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	 		super.onCreate(savedInstanceState);
			setContentView(R.layout.subject);
			
			txtsubjectname=(EditText)findViewById(R.id.txtSubject);
			
			mDbHelper = new DataAdapter(getBaseContext()); 
            
	        mDbHelper.createDatabase();
	    }
	 	
	 	public void AddSubject(View v)
	 	{
	 		mDbHelper.open();
	 		if((txtsubjectname.getText().toString().isEmpty()))
	 		{
	 			Toast.makeText(getApplicationContext(), "Please proved", Toast.LENGTH_SHORT).show();
	 		}
	 		else
	 		{
	 			mDbHelper.InsertSubjectToDB(txtsubjectname.getText().toString());
	 			Toast.makeText(getApplicationContext(), "Subject Added", Toast.LENGTH_SHORT).show();
	 			txtsubjectname.setText("");
	 		}
	 	}
	 	public void Cancel(View v)
	 	{
	 		finish();
	 	}

}
