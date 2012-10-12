package com.example.prandroiderp;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @TargetApi(11)
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
    	((Button)view).setTextAppearance(this, R.style.estiloBotonPulsado);
    	((Button)view).setTextAppearance(this, R.style.estiloBotonNormal);
    }
    
    public void btConfirmarPulsado(View view){
    	((Button)view).setTextAppearance(this, R.style.estiloBotonPulsado);
    	((Button)view).setTextAppearance(this, R.style.estiloBotonNormal);
    }

}
