package com.ece251.mobiledj;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ToggleButton;

public class Deck extends Activity {
	Record_PlayBack rec1, rec2, rec3, rec4;
	Button record1, record2, record3, record4; //Record Buttons
	Button play1,play2,play3,play4;
	
	boolean loop1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		
		Log.d("For Fun", "Testing Git");
		
		initialize_record();
		init_Button();
		loop1 =true;
		
		
		
		
	}
	//TEST

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}

	//TEST TESTEST
	//test3
	
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
		
		play1 = (Button) findViewById(R.id.Play_button1);
		play2 = (Button) findViewById(R.id.Play_button2);
		play3 = (Button) findViewById(R.id.Play_button3);
		play4 = (Button) findViewById(R.id.Play_button4);
		
		record1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				if(action == MotionEvent.ACTION_DOWN){
				Thread recordThread1 = new Thread(new Runnable()
                {
                      @Override
                      public void run()
                      {
                    	 rec1.recording = true;
                    	 rec1.record();
                      }

                });

                recordThread1.start();
				}
				if(action == MotionEvent.ACTION_UP) {
					rec1.recording = false;
				}
				return false;
			}
		});
		record2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				if(action == MotionEvent.ACTION_DOWN){
					Thread recordThread2 = new Thread(new Runnable() {
						@Override
						public void run()
						{
							rec2.recording = true;
                    	 	rec2.record();
						}

                	});

                	recordThread2.start();
				}
				if(action == MotionEvent.ACTION_UP) {
					rec2.recording =false;
				}
				return false;
			}
		});
		record3.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				if(action == MotionEvent.ACTION_DOWN){
					Thread recordThread3 = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							rec3.recording = true;
							rec3.record();
						}
					});

					recordThread3.start();
				}
				if(action == MotionEvent.ACTION_UP) {
					rec3.recording =false;
				}
				return false;
			}
		});
		record4.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				if(action == MotionEvent.ACTION_DOWN){
					Thread recordThread4 = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							rec4.recording = true;
							rec4.record();
						}

					});

					recordThread4.start();
				}
				if(action == MotionEvent.ACTION_UP) {
					rec4.recording =false;
				}
				return false;
			}
		});
	
	/*	record1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rec1.recording = false;
				// TODO Auto-generated method stub
			}
		});
		record2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rec2.recording = false;
				// TODO Auto-generated method stub
			}
		});
		record3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rec3.recording = false;
				// TODO Auto-generated method stub
			}
		});
		record4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rec4.recording = false;
				// TODO Auto-generated method stub
			}
		});
		
		*/
		
		play1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!loop1)
					rec1.play();
				else{
					while(loop1){
						new Thread(new Runnable() {
					        public void run() {
					        	rec1.play();
					        }
					    }).start();

						rec1.hold = true;
						while(rec1.hold){
							;
						}
					}
				}
			}
		});
		play2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rec2.play();
			}
		});
		play3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rec3.play();
			}
		});
		play4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rec4.play();
			}
		});
	}
}
