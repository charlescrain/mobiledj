package com.ece251.mobiledj;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class Deck extends Activity {
	Record_PlayBack rec1, rec2, rec3, rec4, recm;
	Button record1, record2, record3, record4; //Record Buttons
	Button play1,play2,play3,play4,Save,Playback,Send,Connect;
	Button effectSet;
	CheckBox saved;
	ToggleButton loop1,loop2,loop3,loop4,Mix,effect1,effect2,effect3,effect4;
	static short[] audioOne = new short[2400000];
	static short[] audioTwo = new short[2400000];
	static short[] audioThree = new short[2400000];
	static short[] audioFour = new short[2400000];
	static short[] effectOne = new short[2400000];
	static short[] effectTwo = new short[2400000];
	static short[] effectThree = new short[2400000];
	static short[] effectFour = new short[2400000];
	static short[] tmpOne = new short[2400000];
	static short[] tmpTwo = new short[2400000];
	static short[] tmpThree = new short[2400000];
	static short[] tmpFour = new short[2400000];
	static short[] audioMix = new short[2400000];
	static volatile int Time = 0;
	int PlaybackTime = 0;
	static int playing = 0;
	short[] audioData = new short[1];
	short temp;
	static int effect;
	static volatile boolean looping1,looping2,looping3,looping4,
							up1,up2,up3,up4,mixed,mixing,
							use_effect1,use_effect2,use_effect3,use_effect4;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		
		
		initialize_record();
		init_Button();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}

	
	private void initialize_record(){
		rec1 = new Record_PlayBack("recording1.pcm");
		rec2 = new Record_PlayBack("recording2.pcm");
		rec3 = new Record_PlayBack("recording3.pcm");
		rec4 = new Record_PlayBack("recording4.pcm");
		recm = new Record_PlayBack("Empty.pcm");
		
		//default effect is reverb
		effect = 1;
		
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
		
		loop1 = (ToggleButton) findViewById(R.id.Loops_onoff1);
		loop2 = (ToggleButton) findViewById(R.id.Loops_onoff2);
		loop3 = (ToggleButton) findViewById(R.id.Loops_onoff3);
		loop4 = (ToggleButton) findViewById(R.id.Loops_onoff4);
		effect1 = (ToggleButton) findViewById(R.id.Effects_onoff1);
		effect2 = (ToggleButton) findViewById(R.id.Effects_onoff2);
		effect3 = (ToggleButton) findViewById(R.id.Effects_onoff3);
		effect4 = (ToggleButton) findViewById(R.id.Effects_onoff4);
		
		saved = (CheckBox) findViewById(R.id.checkBox1);
		//saved.setEnabled(false);
		saved.setChecked(false);
		
		Mix = (ToggleButton) findViewById(R.id.Mix_button);
		Save = (Button) findViewById(R.id.Save_button);
		Playback = (Button) findViewById(R.id.Playback_button);
		Connect = (Button) findViewById(R.id.WiFi_Button);
		effectSet = (Button) findViewById(R.id.Effects_button);
		
		looping1 = false;
		looping2 = false;
		looping3 = false;
		looping4 = false;
		mixed = false;
		mixing = false;
		
//		File Emptyfile = new File(Environment.getExternalStorageDirectory(), "Empty.pcm");  //make a new file
//		try {
//			Emptyfile.createNewFile();
//			OutputStream outputStream = new FileOutputStream(Emptyfile);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
//            dataOutputStream.writeShort(1);
//            outputStream.close();
//            bufferedOutputStream.close();
//            dataOutputStream.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		

Connect.setOnClickListener(new Button.OnClickListener() {  
    public void onClick(View v)
        {
    	Intent wifiConnect = new Intent(Deck.this, WiFiDirectActivity.class);
		startActivity(wifiConnect);
        }
     });

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
	
		Playback.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				if(action != MotionEvent.ACTION_MOVE){
				Thread PlaybackThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(action == MotionEvent.ACTION_DOWN){
							//int shortSizeInBytes = Short.SIZE/Byte.SIZE;
							//int bufferSizeInBytes = (int)(audioMix.length/shortSizeInBytes);
					          //Log.d("tahdaerhtaetaestah", "test"+bufferSizeInBytes);
					          //short[] audioData = new short[1];

					              //  InputStream inputStream = new FileInputStream(file);
					               // BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
					               // DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

					                AudioTrack audioMixed = new AudioTrack(
					                            AudioManager.STREAM_MUSIC,
					                            44100,
					                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
					                            AudioFormat.ENCODING_PCM_16BIT,
					                            16,
					                            AudioTrack.MODE_STREAM);
					                audioMixed.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
					                audioMixed.play();
					                int i;
					                for(i = 0; i<PlaybackTime; i++){
					                	audioData[0] = audioMix[i];
						                audioMixed.write(audioData, 0, 1);
					                }
							
						}
						if(action == MotionEvent.ACTION_UP) {
							
						}
					}
				});
				PlaybackThread.start();
				}
				
				return false;
			}
		});
		
		Save.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				if(action != MotionEvent.ACTION_MOVE){
				Thread SaveThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(action == MotionEvent.ACTION_DOWN){
							if(mixed == true){
							int i;
							Log.d("done",""+Time);
							try
					        {
							File Finalfile = new File(Environment.getExternalStorageDirectory(), "Final.pcm");  //make a new file
							Finalfile.createNewFile();  //do a try to make sure making new file works
				              
				              //Set newly made file to a DataOutputStream
				              OutputStream outputStreamFinal = new FileOutputStream(Finalfile);
				              BufferedOutputStream bufferedOutputStreamFinal = new BufferedOutputStream(outputStreamFinal);
				              DataOutputStream dataOutputStreamFinal = new DataOutputStream(bufferedOutputStreamFinal);
							for(i = 0; i<Time; i++){
								float one = audioOne[i];
								float two = audioTwo[i];
								float three = audioThree[i];
								float four = audioFour[i];
								temp = (short)((one+two+three+four)/4);
								if(temp > 32000 ){temp = 32000;}
								if(temp < -32000){temp = -32000;}
								audioMix[i] = temp;
//								float samplef1 = audioOne[i] / 32768.0f; 
//						        float samplef2 = audioTwo[i] / 32768.0f;
//						        float samplef3 = audioThree[i] / 32768.0f; 
//						        float samplef4 = audioFour[i] / 32768.0f;
//						        float mixed    = samplef1 + samplef2 + samplef3 + samplef4;
//
//						        // reduce the volume a bit:
//						        mixed *= 0.8;
//						        // hard clipping
//						        if (mixed > 1.0f)  mixed = 1.0f;
//						        if (mixed < -1.0f) mixed = -1.0f;
//
//						        short outputSample = (short) (mixed * 32768.0f);
//						        audioMix[i]         = outputSample;
								//Log.d("One",""+audioOne[i]);
								//Log.d("Two",""+audioTwo[i]);
						        //Log.d("Mix1",""+outputSample);
						        //Log.d("Mix2",""+audioMix[i]);
								dataOutputStreamFinal.writeShort(audioMix[i]);
							}
				              dataOutputStreamFinal.close();
					        }catch (IOException e)
				              {
				                    e.printStackTrace();
				              }
							PlaybackTime = Time;
							Time = 0;
							runOnUiThread(new Runnable() {
							     @Override
							     public void run() {
								saved.setChecked(true);
							    }
							});
							Log.d("done","DONE");
							}
						}
						if(action == MotionEvent.ACTION_UP) {
							
						}
						return;
					}
				});
				SaveThread.start();
				}
				
				return false;
			}
		});
		
		play1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				if(action != MotionEvent.ACTION_MOVE){
				Thread playThread1 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 1;break;
							case 2:playing = 5;break;
							case 3:playing = 6;break;
							case 4:playing = 7;break;
							case 8:playing = 11;break;
							case 9:playing = 12;break;
							case 10:playing = 13;break;
							case 14:playing = 15;break;
							
							}
							up1 = false;
							rec1.play();
							
						}
						if(action == MotionEvent.ACTION_UP) {
							if(!looping1){
							switch(playing){
							case 1:playing = 0;break;
							case 5:playing = 2;break;
							case 6:playing = 3;break;
							case 7:playing = 4;break;
							case 11:playing = 8;break;
							case 12:playing = 9;break;
							case 13:playing = 10;break;
							case 15:playing = 14;break;
							}
							
							rec1.playon = false;
							}else{
								up1 = true;
							}
						}
						return;
					}
				});
				playThread1.start();
				}
				
				return false;
			}
		});
	
		play2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int action = event.getAction();
				Thread playThread2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 2;break;
							case 1:playing = 5;break;
							case 3:playing = 8;break;
							case 4:playing = 9;break;
							case 6:playing = 11;break;
							case 7:playing = 12;break;
							case 10:playing = 14;break;
							case 13:playing = 15;break;
							}
							up2 = false;
							rec2.play();
						}
						if(action == MotionEvent.ACTION_UP) {
							if(!looping2){
							switch(playing){
							case 2:playing = 0;break;
							case 5:playing = 1;break;
							case 8:playing = 3;break;
							case 9:playing = 4;break;
							case 11:playing = 6;break;
							case 12:playing = 7;break;
							case 14:playing = 10;break;
							case 15:playing = 13;break;
							}
							rec2.playon = false;
							}else{
								up2 = true;
							}
						}
						return;
					}
				});
				playThread2.start();
				return false;
			}
		});
		
		play3.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				final int action = event.getAction();
				if(action != MotionEvent.ACTION_MOVE){
				Thread playThread3 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
							Log.d("PLay", "Calling PLay");
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 3;break;
							case 1:playing = 6;break;
							case 2:playing = 8;break;
							case 4:playing = 10;break;
							case 5:playing = 11;break;
							case 7:playing = 13;break;
							case 9:playing = 14;break;
							case 12:playing = 15;break;
							}
							up3 = false;
							rec3.play();
						}
						if(action == MotionEvent.ACTION_UP) {
							if(!looping3){
							switch(playing){
							case 3:playing = 0;break;
							case 6:playing = 1;break;
							case 8:playing = 2;break;
							case 10:playing = 4;break;
							case 11:playing = 5;break;
							case 13:playing = 7;break;
							case 14:playing = 9;break;
							case 15:playing = 12;break;
							}
							
							rec3.playon = false;
							}else{
								up3 = true;
							}
						}
						return;
					}
				});
				playThread3.start();
				}
				
				return false;
			}
		});
	
		play4.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int action = event.getAction();
				Thread playThread4 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						if(action == MotionEvent.ACTION_DOWN){
							switch(playing){
							case 0:playing = 4;break;
							case 1:playing = 7;break;
							case 2:playing = 9;break;
							case 3:playing = 10;break;
							case 5:playing = 12;break;
							case 6:playing = 13;break;
							case 8:playing = 14;break;
							case 11:playing = 15;break;
							}
							up4 = false;
							rec4.play();
						}
						if(action == MotionEvent.ACTION_UP) {
							if(!looping4){
							switch(playing){
							case 4:playing = 0;break;
							case 7:playing = 1;break;
							case 9:playing = 2;break;
							case 10:playing = 3;break;
							case 12:playing = 5;break;
							case 13:playing = 6;break;
							case 14:playing = 8;break;
							case 15:playing = 11;break;
							}
							rec4.playon = false;
							}else{
								up4 = true;
							}
						}
						return;
					}
				});
				playThread4.start();
				return false;
			}
		});
		
		loop1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	looping1 = true;// The toggle is enabled
		        } else {
		        	looping1 = false;// The toggle is disabled
		        	if(up1 == true){
						switch(playing){
						case 1:playing = 0;break;
						case 5:playing = 2;break;
						case 6:playing = 3;break;
						case 7:playing = 4;break;
						}
						
						rec1.playon = false;
					}
		        }
		    }
		});
		loop2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	looping2 = true;// The toggle is enabled
		        } else {
		        	looping2 = false;// The toggle is disabled
		        	if(up2 == true){
						switch(playing){
						case 1:playing = 0;break;
						case 5:playing = 2;break;
						case 6:playing = 3;break;
						case 7:playing = 4;break;
						}
						
						rec2.playon = false;
					}
		        }
		    }
		});
		loop3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	looping3 = true;// The toggle is enabled
		        } else {
		        	looping3 = false;// The toggle is disabled
		        	if(up3 == true){
						switch(playing){
						case 1:playing = 0;break;
						case 5:playing = 2;break;
						case 6:playing = 3;break;
						case 7:playing = 4;break;
						}
						
						rec3.playon = false;
					}
		        }
		    }
		});
		loop4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	looping4 = true;// The toggle is enabled
		        } else {
		        	looping4 = false;// The toggle is disabled
		        	if(up4 == true){
						switch(playing){
						case 1:playing = 0;break;
						case 5:playing = 2;break;
						case 6:playing = 3;break;
						case 7:playing = 4;break;
						}
						
						rec4.playon = false;
					}
		        }
		    }
		});
		Mix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				final boolean Checked = isChecked;
				Thread MixThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						 if (Checked) {
							 runOnUiThread(new Runnable() {
							     @Override
							     public void run() {
								saved.setChecked(false);
							    }
							});
							 mixed = false;
							 mixing = true;
							 recm.play();
					        } else {
					         rec1.playon = false;
					         mixing = false;
					         mixed = true;
					        }
							return;
					}	
				});
				MixThread.start();
			}
		});
		effectSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(),EffectActivity.class);
				startActivity(intent);
				//reset effect arrays
				
				
			}
		});
		effect1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
	        		use_effect1=true;
	        		for(int i=0;i<2400000;i++){
						effectOne[i]=0;
						
					}
					switch(effect){
					case 1: effectFast(1);break;
					case 2: effectReverb(1);break;
					case 3: effectReverse(1);break;
					}
		        } else {
					use_effect1 =false;
		        }
		    }
		});
		effect2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
	        		use_effect2=true;
	        		for(int i=0;i<2400000;i++){
						effectTwo[i]=0;
						
					}
					switch(effect){
					case 1: effectFast(2);break;
					case 2: effectReverb(2);break;
					case 3: effectReverse(2);break;
					}
		        } else {
					use_effect2 =false;
		        }
		    }
		});
		effect3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
	        		use_effect3=true;
	        		for(int i=0;i<2400000;i++){
						effectThree[i]=0;
						
					}
					switch(effect){
					case 1: effectFast(3);break;
					case 2: effectReverb(3);break;
					case 3: effectReverse(3);break;
					}
	        		
		        } else {
					use_effect3 =false;
		        }
		    }
		});	
		effect4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
	        		use_effect4=true;
	        		for(int i=0;i<2400000;i++){
						effectFour[i]=0;
						
					}
					switch(effect){
					case 1: effectFast(4);break;
					case 2: effectReverb(4);break;
					case 3: effectReverse(4);break;
					}
	        		
		        } else {
					use_effect4 =false;
		        }
		    }
		});
	}
	
	public void effectReverb(int input){
    	int delayMilliseconds = 250; // half a second
    	int delaySamples = 
    	    (int)((float)delayMilliseconds * 44.1f); // assumes 44100 Hz sample rate
    	float decay = 0.5f;
    	switch(input){
    	case 1:
    	//Reverb for audioOne
    	for(int i=0;i<rec1.i;i++){
    		effectOne[i] = tmpOne[i];
    	}
    	for (int i = 0; i < rec1.i - delaySamples; i++)
    	{
    	    // WARNING: overflow potential
    	    effectOne[i + delaySamples] += (short)((float)effectOne[i] * decay);
    	}break;
    	//Reverb for audioTwo
    	case 2:
    	for(int i=0;i<rec2.i;i++){
    		effectTwo[i] = tmpTwo[i];
    	}
    	for (int i = 0; i < rec2.i - delaySamples; i++)
    	{
    	    // WARNING: overflow potential
    	    effectTwo[i + delaySamples] += (short)((float)effectTwo[i] * decay);
    	}break;
    	//Reverb for audioThree
    	case 3:
    	for(int i=0;i<rec3.i;i++){
    		effectThree[i] = tmpThree[i];
    	}
    	for (int i = 0; i < rec3.i - delaySamples; i++)
    	{
    	    // WARNING: overflow potential
    	    effectThree[i + delaySamples] += (short)((float)effectThree[i] * decay);
    	}break;
    	//Reverb for audioFour
    	case 4:
    	for(int i=0;i<rec4.i;i++){
    		effectFour[i] = tmpFour[i];
    	}
    	for (int i = 0; i < rec4.i - delaySamples; i++)
    	{
    	    // WARNING: overflow potential
    	    effectFour[i + delaySamples] += (short)((float)effectFour[i] * decay);
    	}break;
    	}
    	return;
    }
    public void effectReverse(int input) {
    	int i =0;
    	switch(input){
    	//copy reverse of audioOne
    	case 1:
    	for(int j=rec1.i-1;j>=0;j--){
    		effectOne[j] = tmpOne[i];
    		i++;
    	}break;
    	//reverse of audioTwo
    	case 2:
    	for(int j=rec2.i-1;j>=0;j--){
    		effectTwo[j] = tmpTwo[i];
    		i++;
    	}break;
    	//reverse of audioThree
    	case 3:
    	for(int j=rec3.i-1;j>=0;j--){
    		effectThree[j] = tmpThree[i];
    		i++;
    	}break;
    	//reverse audioFour
    	case 4:
    	for(int j=rec4.i-1;j>=0;j--){
    		effectFour[j] = tmpFour[i];
    		i++;
    	}break;
    	}
    	return;
    }
    public void effectFast(int input) {
    	int i = 0, j=0;
    	
    	float temp;
    	
    	switch(input){
    	case 1:
    	
    	for(j=0;j<rec1.i-1;j=j+2){
    		temp = (tmpOne[j] + tmpOne[j+1])/2;
    		effectOne[i] = (short) (temp);
    		i++;
    		
    	}rec1.effectCount = j-2;
    	break;
    	case 2:
    	for(j=0;j<rec2.i-1;j=j+2){
    		temp = (tmpTwo[j] + tmpTwo[j+1])/2;
    		effectTwo[i] = (short) (temp);
    		i++;
    	}rec2.effectCount = j;
    	break;
    	case 3:
    	for(j=0;j<rec3.i-1;j=j+2){
    		temp = (tmpThree[j] + tmpThree[j+1])/2;
    		effectThree[i] = (short) (temp);
    		i++;
    	}rec3.effectCount = j;
    	break;
    	case 4:
    	for(j=0;j<rec4.i-1;j=j+2){
    		temp = (tmpFour[j] + tmpFour[j+1])/2;
    		effectFour[i] = (short) (temp);
    		i++;
    	}rec4.effectCount = j;
    	break;
    	}
    	return;
    }
}
