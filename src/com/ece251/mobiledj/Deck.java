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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class Deck extends Activity {
	Record_PlayBack rec1, rec2, rec3, rec4, recm;
	Button record1, record2, record3, record4; //Record Buttons
	Button play1,play2,play3,play4,Save,Playback,Send,Connect;
	Button effectSet;
	ToggleButton loop1,loop2,loop3,loop4,Mix;
	static short[] audioOne = new short[2400000];
	static short[] audioTwo = new short[2400000];
	static short[] audioThree = new short[2400000];
	static short[] audioFour = new short[2400000];
	static short[] audioMix = new short[2400000];
	static volatile int Time = 0;
	int PlaybackTime = 0;
	static int playing = 0;
	short[] audioData = new short[1];
	short temp;
	static short effect;
	static volatile boolean looping1,looping2,looping3,looping4,up1,up2,up3,up4,mixed,mixing;
	

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
		recm = new Record_PlayBack("Empty.pcm");
		
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
		
		Mix = (ToggleButton) findViewById(R.id.Mix_button);
		Save = (Button) findViewById(R.id.Save_button);
		Playback = (Button) findViewById(R.id.Playback_button);
		Send = (Button) findViewById(R.id.Send_button);
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


Send.setOnClickListener(new Button.OnClickListener() {  
    public void onClick(View v)
    {
    	String filename = "FINALMIXED";
    	boolean fileExists = false;
    	
    	//GET THE FINAL MIX FILE
    	File file = new File(Environment.getExternalStorageDirectory(), filename);
    	fileExists = file.exists();
    	if(fileExists){
    		URI uri = file.toURI();	
    	}
    	
    	Intent serviceIntent = new Intent(Deck.this, FileTransferService.class);
    	serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
    	
    	
    	/*
    	 * INFO IS IN DEVICE DETAIL FRAGMENT, will BE HARD TO CALL FROM THE DECK.java FILE
    	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                info.groupOwnerAddress.getHostAddress());
    	
    	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
                info.groupOwnerAddress.getHostAddress());
    	
    	serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
    	
    	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
        startService(serviceIntent);
        
        */
    }
 });


/*
//ADD A SEND BUTTON TO SEND THE MIXED SAMPLE
//ONCLICK FOR THE SEND BUTTON
 *TAKES YOU TO THE FILE TRANSFER SERVICE
String filename = "what Omri named the final mix file";
                @Override
                public void onClick(View v) {

                	
                	File file = new File(Environment.getExternalStorageDirectory(), filename);
                	
                	// if file exists boolean 
                	 * bool = file.exists()
                	if (bool){
                		Uri uri = file.toUri();
                	}
                	
                	//Make an intent
                	Intent serviceIntent - new Intent (getActivity(), FileTransferService.class);
                	serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
                	
                	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
        	                info.groupOwnerAddress.getHostAddress());
                	
                	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
        	                info.groupOwnerAddress.getHostAddress());

                	//NOT SURE WHAT EXTRAS FILE PATH IS, may be a key for the value uri.toString()
                	serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
                	
                	serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);
        	        getActivity().startService(serviceIntent);
                }
		
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
							File Finalfile = new File(Environment.getExternalStorageDirectory(), "recording1.pcm");  //make a new file
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
				              outputStreamFinal.close();
				              bufferedOutputStreamFinal.close();
				              dataOutputStreamFinal.close();
					        }catch (IOException e)
				              {
				                    e.printStackTrace();
				              }
							PlaybackTime = Time;
							Time = 0;
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
				
			}
		});
	}
}
