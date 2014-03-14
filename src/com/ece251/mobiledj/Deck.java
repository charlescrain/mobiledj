package com.ece251.mobiledj;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
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
	Button play1,play2,play3,play4,Mix;
	static short[] audioOne = new short[600000];
	static short[] audioTwo = new short[600000];
	static short[] audioThree = new short[600000];
	static short[] audioFour = new short[600000];
	static volatile int Time = 0;
	static int playing = 0;
	
	static  boolean playon1,playon2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		
		
		initialize_record();
		init_Button();
		
		
		
		
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
		
		Mix = (Button) findViewById(R.id.Mix_button);
		
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
		
		Mix.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				if(action != MotionEvent.ACTION_MOVE){
				Thread play1 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(action == MotionEvent.ACTION_DOWN){
							//int shortSizeInBytes = Short.SIZE/Byte.SIZE;
							//int bufferSizeInBytes = (int)(audioOne.length()/shortSizeInBytes);
					          //Log.d("tahdaerhtaetaestah", "test"+bufferSizeInBytes);
					          //short[] audioData = new short[1];

					              //  InputStream inputStream = new FileInputStream(file);
					               // BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
					               // DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

					                AudioTrack audioMix = new AudioTrack(
					                            AudioManager.STREAM_MUSIC,
					                            11025,
					                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
					                            AudioFormat.ENCODING_PCM_16BIT,
					                            Time,
					                            AudioTrack.MODE_STREAM);
							audioMix.play();
					        audioMix.write(audioOne, 0, Time);
							
						}
						if(action == MotionEvent.ACTION_UP) {
							switch(playing){
							case 1:playing = 0;break;
							case 3:playing = 2;break;
							}
							
							rec1.playon = false;
							Log.d("Motion Up", ""+rec1.playon);
						}
					}
				});
				play1.start();
				}
				
				return false;
			}
		});
		
		play1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				Log.d("ACtion", "Action is:" + action);
				if(action != MotionEvent.ACTION_MOVE){
				Thread play1 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
							Log.d("PLay", "Calling PLay");
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 1;break;
							case 2:playing = 3;break;
							}
							rec1.play();
						}
						if(action == MotionEvent.ACTION_UP) {
							switch(playing){
							case 1:playing = 0;break;
							case 3:playing = 2;break;
							}
							
							rec1.playon = false;
							Log.d("Motion Up", ""+rec1.playon);
						}
					}
				});
				play1.start();
				}
				
				return false;
			}
		});
	
		play2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int action = event.getAction();
				Thread play2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 2;break;
							case 1:playing = 3;break;
							}
							rec2.play();
						}
						if(action == MotionEvent.ACTION_UP) {
							switch(playing){
							case 2:playing = 0;break;
							case 3:playing = 1;break;
							}
							rec2.playon = false;
						}
					}
				});
				play2.start();
				return false;
			}
		});
			
		play3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				switch(playing){
//				case 1:playing = ;break;
//				case 2:playing = ;break;
//				case 3:playing = ;break;
//				case 4:playing = ;break;
//				case 5:playing = ;break;
//				case 6:playing = ;break;
//				case 7:playing = ;break;
//				case 8:playing = ;break;
//				case 9:playing = ;break;
//				case 10:playing = ;break;
//				}
				rec3.play();
			}
		});
		play4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				switch(playing){
//				case 1:playing = ;break;
//				case 2:playing = ;break;
//				case 3:playing = ;break;
//				case 4:playing = ;break;
//				case 5:playing = ;break;
//				case 6:playing = ;break;
//				case 7:playing = ;break;
//				case 8:playing = ;break;
//				case 9:playing = ;break;
//				case 10:playing = ;break;
//				}
				rec4.play();
			}
		});
	}
}
