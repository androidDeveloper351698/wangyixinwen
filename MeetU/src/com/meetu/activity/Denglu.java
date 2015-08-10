package com.meetu.activity;

import com.meetu.R;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Denglu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_denglu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.denglu, menu);
		return true;
	}

}
