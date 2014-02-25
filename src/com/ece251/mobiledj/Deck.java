package com.ece251.mobiledj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Deck extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
	}
	//TEST whats uup motha fucka

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deck, menu);
		return true;
	}

	//TEST
	
	
}
