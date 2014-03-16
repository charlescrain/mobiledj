package com.ece251.mobiledj;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Record_PlayBack {
	
	private String filename;
	private File file;
	
	
	public volatile boolean recording,playon;
	
	public Record_PlayBack(String name){
		filename = name;
		
	}
	
	public void record(){
 	  file = new File(Environment.getExternalStorageDirectory(), filename);  //make a new file
		try
        {		
              file.createNewFile();  //do a try to make sure making new file works
              
              //Set newly made file to a DataOutputStream
              OutputStream outputStream = new FileOutputStream(file);
              BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
              DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

              int minBufferSize = AudioRecord.getMinBufferSize(44100,
                          AudioFormat.CHANNEL_CONFIGURATION_MONO,
                          AudioFormat.ENCODING_PCM_16BIT);
              short[] audioData = new short[minBufferSize];

              AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
            		  	44100,
                          AudioFormat.CHANNEL_CONFIGURATION_MONO,
                          AudioFormat.ENCODING_PCM_16BIT,
                          minBufferSize);

              audioRecord.startRecording();

              while(recording)
              {
                    int numberOfShort = audioRecord.read(audioData, 0, minBufferSize);
                    for(int i = 0; i < numberOfShort; i++)
                    {
                          dataOutputStream.writeShort(audioData[i]);
                    }
              }
              audioRecord.stop();
              outputStream.close();
              bufferedOutputStream.close();
              dataOutputStream.close();

        }
        catch (IOException e)
        {
              e.printStackTrace();
        }
	}
	
	public void play(){
		if(filename != "Empty.pcm"){
		  file = new File(Environment.getExternalStorageDirectory(), filename);  //make a new file
		}else{
		  file = new File(Environment.getExternalStorageDirectory(), "recording1.pcm");  //make a new file
		}
		  playon = true;
          //int shortSizeInBytes = Short.SIZE/Byte.SIZE;

          //int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
          //Log.d("tahdaerhtaetaestah", "test"+bufferSizeInBytes);
          short[] audioData = new short[1];

          try {
                InputStream inputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
                AudioTrack audioTrack = new AudioTrack(
                            AudioManager.STREAM_MUSIC,
                            44100,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT,
                            16,
                            AudioTrack.MODE_STREAM);
             //   audioTrack.setNotificationMarkerPosition(bufferSizeInBytes);
             /*   audioTrack.setPlaybackPositionUpdateListener(new OnPlaybackPositionUpdateListener() {
                    @Override
                    public void onPeriodicNotification(AudioTrack track) {
                        // nothing to do
                    }
                    @Override
                    public void onMarkerReached(AudioTrack track) {
                        Log.d("Record_PlayBack", "Audio track end of file reached...");
                        hold = false;
                    }
                });  */
                audioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
               // audioTrack.setLoopPoints(0, bufferSizeInBytes, 3);
                audioTrack.play();
                while(playon)
                {
                	
                     audioData[0] = dataInputStream.readShort();
                	
                	
                	if(Deck.mixing == true){
                      //Log.d("SIGNAL",""+audioData[0]);
                      if(filename == "recording1.pcm"){
                    	  Deck.audioOne[Deck.Time] = audioData[0];
                      }
                      if(filename == "recording2.pcm"){
                    	  Deck.audioTwo[Deck.Time] = audioData[0];
                      }
                      if(filename == "recording3.pcm"){
                    	  Deck.audioThree[Deck.Time] = audioData[0];
                      }
                      if(filename == "recording4.pcm"){
                    	  Deck.audioFour[Deck.Time] = audioData[0];
                      }
                	
                      
                      switch(Deck.playing){
                      case 0:if(filename == "Empty.pcm"){
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 1:if(filename == "recording1.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 2:if(filename == "recording2.pcm"){
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 3:if(filename == "recording3.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 4:if(filename == "recording4.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 5:if(filename == "recording1.pcm"){
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 6:if(filename == "recording1.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 7:if(filename == "recording1.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 8:if(filename == "recording2.pcm"){
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 9:if(filename == "recording2.pcm"){
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 10:if(filename == "recording3.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 11:if(filename == "recording1.pcm"){
                    	  Deck.audioFour[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 12:if(filename == "recording1.pcm"){
                    	  Deck.audioThree[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 13:if(filename == "recording1.pcm"){
                    	  Deck.audioTwo[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 14:if(filename == "recording2.pcm"){
                    	  Deck.audioOne[Deck.Time] = 0;
                    	  Deck.Time++;
                      }break;
                      case 15:if(filename == "recording1.pcm"){
                    	  Deck.Time++;
                      }break;
                      }
                	}
                	
                	if(filename != "Empty.pcm"){
                      audioTrack.write(audioData, 0, 1);
                	}else{
                	  audioData[0] = 0;
                	  audioTrack.write(audioData, 0, 1);
                	}
                      if(dataInputStream.available() == 0){
                    	  inputStream.close();
                    	  bufferedInputStream.close();
                    	  dataInputStream.close();
                    	  inputStream = new FileInputStream(file);
                          bufferedInputStream = new BufferedInputStream(inputStream);
                          dataInputStream = new DataInputStream(bufferedInputStream);
                      }
                }
                audioTrack.stop();
                inputStream.close();
                bufferedInputStream.close();
                dataInputStream.close();
                
                

          } catch (FileNotFoundException e)
          {
                e.printStackTrace();
          } catch (IOException e)
          {
                e.printStackTrace();
          }
//    }else{
//    	playon = true;
//    	short[] audioData = new short[1];
//    	AudioTrack audioTrack = new AudioTrack(
//                          AudioManager.STREAM_MUSIC,
//                          44100,
//                          AudioFormat.CHANNEL_CONFIGURATION_MONO,
//                          AudioFormat.ENCODING_PCM_16BIT,
//                          16,
//                          AudioTrack.MODE_STREAM);
//           
//              audioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
//              audioTrack.play();
//              while(playon)
//              {
//                   audioData[0] = 0;
//              	
//              	if(Deck.mixing == true){
//                   
//                    if(Deck.playing == 0){
//                  	  Deck.audioOne[Deck.Time] = 0;
//                  	  Deck.audioTwo[Deck.Time] = 0;
//                  	  Deck.audioThree[Deck.Time] = 0;
//                  	  Deck.audioFour[Deck.Time] = 0;
//                  	  Deck.Time++;
//                    }
//                    audioTrack.write(audioData, 0, 1);
//                    
//              }
//    	
//    }
//    }
	}
	
	

}
