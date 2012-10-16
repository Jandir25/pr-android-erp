package com.example.prandroiderp;

import com.example.venta.Venta;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class CabecerasArticuloFragment extends ListFragment{
	
	
	/*
	 * private OnArticuloSelectedListener mCallback;
	public interface OnArticuloSelectedListener{
		public void onArticuloSelected(int pos);
	}
	*/
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Venta.getListadoArticulos()));
        
	}
	
}
