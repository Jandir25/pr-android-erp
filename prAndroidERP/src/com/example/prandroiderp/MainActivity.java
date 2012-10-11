package com.example.prandroiderp;

import android.os.Bundle;
import android.provider.CalendarContract.Colors;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	public final static String EXTRA_MENSAJE="com.example.prAndroidERP.MensajebtCancelar";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void btCancelarPulsado(View view){
    	/*Intent intent = new Intent(this,MostrarMensaje.class);
    	intent.putExtra(EXTRA_MENSAJE,"btCancelar Pulsado");
    	startActivity(intent);*/    	
    	((Button)view).setTextAppearance(this, R.style.boldText);
    /*	((Button)view).setTextAppearance(this, R.style.normalText);*/
    }
    public void btVerdePulsado(View view){
    	((Button)view).setTextAppearance(this, R.style.boldText);
    	/*((Button)view).setTextAppearance(this, R.style.normalText);*/
    }
}
