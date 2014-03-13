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
	
	
	public boolean recording,hold;
	
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

              int minBufferSize = AudioRecord.getMinBufferSize(11025,
                          AudioFormat.CHANNEL_CONFIGURATION_MONO,
                          AudioFormat.ENCODING_PCM_16BIT);

              short[] audioData = new short[minBufferSize];

              AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                          11025,
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
              dataOutputStream.close();

        }
        catch (IOException e)
        {
              e.printStackTrace();
        }
	}
	
	public void play(){
		  file = new File(Environment.getExternalStorageDirectory(), filename);  //make a new file
		  int x;
          int shortSizeInBytes = Short.SIZE/Byte.SIZE;

          int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
          short[] audioData = new short[bufferSizeInBytes];

          try {
                InputStream inputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

                int i = 0;
                while(dataInputStream.available() > 0)
                {
                      audioData[i] = dataInputStream.readShort();
                      i++;
                }

                dataInputStream.close();

                AudioTrack audioTrack = new AudioTrack(
                            AudioManager.STREAM_MUSIC,
                            11025,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT,
                            bufferSizeInBytes,
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
                audioTrack.write(audioData, 0, bufferSizeInBytes);
                

          } catch (FileNotFoundException e)
          {
                e.printStackTrace();
          } catch (IOException e)
          {
                e.printStackTrace();
          }
    } 
	
	

}
