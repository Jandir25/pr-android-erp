package com.singularshop.androiderp.vistas.fragmentos;


import com.singularshop.androiderp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentoNuevaVentaPrincipal extends Fragment {
	public FragmentoNuevaVentaPrincipal(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View vista=inflater.inflate(R.layout.fragmento_nuevaventa_principal, container,false);
		return vista;
	}
}
