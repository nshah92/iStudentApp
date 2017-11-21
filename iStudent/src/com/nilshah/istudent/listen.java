package com.nilshah.istudent;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nilshah.database.DataAdapter;
  
public class listen extends Activity 
{  
    Button start,pause,stop; 
    Spinner spnSubjectListen,spnRecordfile;
    DataAdapter mDbHelper;
    String rec;
    int pos=0;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) 
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.listen);  
          
        start=(Button)findViewById(R.id.btnStart);  
        pause=(Button)findViewById(R.id.btnpause);  
        stop=(Button)findViewById(R.id.btnStop);  
        spnSubjectListen=(Spinner)findViewById(R.id.spnListener);
        spnRecordfile=(Spinner)findViewById(R.id.spnpart1);
		
        mDbHelper = new DataAdapter(getBaseContext()); 
        mDbHelper.createDatabase();      
        mDbHelper.open();
		List<String> listsubject=mDbHelper.getSubjectDetails();
		
		final MediaPlayer mp=new MediaPlayer();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listsubject);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSubjectListen.setAdapter(adapter);
		
		spnSubjectListen.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences("MyData", MODE_PRIVATE); 
				String sudid=mDbHelper.getSubID(spnSubjectListen.getSelectedItem().toString());
				String uname = prefs.getString("username", null);
				String stdid=mDbHelper.getStudentID(uname);
				
				List<String> listfile=mDbHelper.getRecordDetails(stdid, sudid);

				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(listen.this, android.R.layout.simple_spinner_item, listfile);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnRecordfile.setAdapter(adapter1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		spnRecordfile.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				pos=position;
				Log.i("Position",pos+"d");
				Log.i("demo",spnRecordfile.getItemAtPosition(pos).toString());
				rec=spnRecordfile.getItemAtPosition(pos).toString();
				Log.i("Position",rec);
				
				  
		        try
		        {  
		        	rec=spnRecordfile.getItemAtPosition(pos).toString();
		        	Log.i("Record",Environment.getExternalStorageDirectory().getPath()+"/AudioRecorder/"+rec+".mp4");
		        	mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/AudioRecorder/"+rec+".mp4");
		        	//mp.setDataSource("/data/data/" + this.getPackageName()+"/AudioRecorder/"+spnRecordfile.getSelectedItem().toString()+".mp4");
		        	mp.prepare();  
		        }
		        catch(Exception e){e.printStackTrace();}  
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
        //creating media player  
        
          
        start.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) 
            {
                mp.start();  
            }  
        });  
        pause.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                mp.pause();  
            }  
        });  
        stop.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                mp.stop();  
            }  
        });  
    }  
}
