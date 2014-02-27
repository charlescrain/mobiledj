package com.ece251.mobiledj;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ToggleButton;

public class Deck extends Activity {
	Record_PlayBack rec1, rec2, rec3, rec4;
	Button record1;
	Button record2;
	Button record3;
	Button record4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		
		Log.d("For Fun", "Testing Git");
		
		initialize_record();
		
		
		
		
		
	}
	//TEST

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}

	//TEST TESTEST
	
	private void initialize_record(){
		rec1 = new Record_PlayBack("recording1.pcm");
		rec2 = new Record_PlayBack("recording2.pcm");
		rec3 = new Record_PlayBack("recording3.pcm");
		rec4 = new Record_PlayBack("recording4.pcm");
		
	}
	
	private void init_Button(){
		record1  = (Button) findViewById(R.id.Record_button1);
		record2  = (Button) findViewById(R.id.Record_button2);
		record3  = (Button) findViewById(R.id.Record_button3);
		record4  = (Button) findViewById(R.id.Record_button4);
		
		
		
		
	}
}
