package com.nilshah.istudent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nilshah.database.DataAdapter;

public class record extends Activity
{
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";

	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,AUDIO_RECORDER_FILE_EXT_3GP };
	DataAdapter mDbHelper;
	EditText txtsubjectname,txtnotes;
	Spinner spnSubjectname;
	SharedPreferences prefs;
	Calendar c;
	SimpleDateFormat df;
	String TodayDate;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		prefs = getSharedPreferences("MyData", MODE_PRIVATE); 
		
		txtnotes=(EditText)findViewById(R.id.txtnotes);
		spnSubjectname=(Spinner)findViewById(R.id.spnSubject);
		mDbHelper = new DataAdapter(getBaseContext()); 
		
		c = Calendar.getInstance();
		df = new SimpleDateFormat("dd-MMM-yyyy");
		TodayDate = df.format(c.getTime());
        
        mDbHelper.createDatabase();      
        mDbHelper.open();
		List<String> listsubject=mDbHelper.getSubjectDetails();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listsubject);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSubjectname.setAdapter(adapter);
		
		setButtonHandlers();
		enableButtons(false);
		setFormatButtonCaption();
	}

	private void setButtonHandlers() 
	{
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
	}

	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
		enableButton(R.id.btnFormat, !isRecording);
		enableButton(R.id.btnStop, isRecording);
	}

	private void setFormatButtonCaption() {
		((Button) findViewById(R.id.btnFormat))
				.setText("Format" + " ("
						+ file_exts[currentFormat] + ")");
	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		//String filepath="/data/data/" + this.getPackageName();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + spnSubjectname.getSelectedItem().toString()+" "+TodayDate + file_exts[currentFormat]);
	}

	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();

			recorder = null;
		}
	}

	private void displayFormatDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String formats[] = { "MPEG 4", "3GPP" };

		builder.setTitle("Choose Audio Format")
				.setSingleChoiceItems(formats, currentFormat,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								currentFormat = which;
								setFormatButtonCaption();

								dialog.dismiss();
							}
						}).show();
	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(record.this,
					"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(record.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					.show();
		}
	};

	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: 
			{
				//3
				String uname = prefs.getString("username", null);
				String stdid=mDbHelper.getStudentID(uname);
				String subid= mDbHelper.getSubID(spnSubjectname.getSelectedItem().toString());
				String notes= txtnotes.getText().toString();
				String rname=spnSubjectname.getSelectedItem().toString()+" "+TodayDate;
				String rdate=TodayDate;
				
				if(uname.equals(" ") && subid.equals(" "))
				{
					Toast.makeText(record.this, "Something wrong",Toast.LENGTH_SHORT).show();
				}
				else
				{
					mDbHelper.InsertData(stdid, subid, notes, rname, rdate);
					
					Toast.makeText(record.this, "Start Recording",Toast.LENGTH_SHORT).show();

					enableButtons(true);
					startRecording();
				}
				break;
			}
			case R.id.btnStop: {
				Toast.makeText(record.this, "Stop Recording",
						Toast.LENGTH_SHORT).show();
				enableButtons(false);
				stopRecording();

				break;
			}
			case R.id.btnFormat: {
				displayFormatDialog();

				break;
			}
			}
		}
	};
}