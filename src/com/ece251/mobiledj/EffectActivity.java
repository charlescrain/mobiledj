package com.ece251.mobiledj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EffectActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.effect_activity);
		Button reverb, fastplay,reverse;
		reverb = (Button) findViewById(R.id.reverbPlay);
		fastplay = (Button) findViewById(R.id.fastPlay);
		reverse = (Button) findViewById(R.id.reversePlay);
		
		reverb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Deck.effect = 1;
				finish();
			}
		});
		fastplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Deck.effect = 2;
				finish();
			}
		});
		reverse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Deck.effect = 3;
				finish();
			}
		});
	}
}
