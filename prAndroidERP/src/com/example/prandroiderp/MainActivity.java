package com.example.prandroiderp;




import com.example.venta.Venta;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;

public class MainActivity extends FragmentActivity {

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
    public void btEscanearPulsado(View view){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
    	startActivityForResult(intent, 0);    	
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	   if (requestCode == 0) {
	      if (resultCode == RESULT_OK) {
	         String contents = intent.getStringExtra("SCAN_RESULT");
	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	         // Handle successful scan
	         Venta.addListadoArticulos(contents);
	         FragmentManager fm= getSupportFragmentManager();
	         CabecerasArticuloFragment fragment=(CabecerasArticuloFragment) fm.findFragmentById(R.id.fragmento_cabecera);
	         fragment.getListView().invalidateViews();
	         
	         
	         
	      } else if (resultCode == RESULT_CANCELED) {
	         // Handle cancel
	      }
	   }
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
