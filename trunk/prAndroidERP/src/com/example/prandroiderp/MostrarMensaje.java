package com.example.prandroiderp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarMensaje extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent intent = this.getIntent();
		String mensaje= intent.getStringExtra(MainActivity.EXTRA_MENSAJE);
		TextView tv= new TextView(this);
		tv.setTextSize(40);
		tv.setText(mensaje);
		setContentView(tv);
			
	}
}
