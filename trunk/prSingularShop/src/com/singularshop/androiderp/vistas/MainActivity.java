package com.singularshop.androiderp.vistas;

import com.singularshop.androiderp.R;
import com.singularshop.androiderp.vistas.fragmentos.FragmentoNuevaVentaPrincipal;


import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private String[] mTitulosFunciones;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitulosFunciones= getResources().getStringArray(R.array.array_titulos_navDrawer);
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,mTitulosFunciones));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
	}
	private class DrawerItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			selectItem(position);
		}
		
	}
	private void selectItem(int position){
		
		switch (position){
			case 0:
				FragmentoNuevaVentaPrincipal frag= new FragmentoNuevaVentaPrincipal();
				FragmentManager fm = getFragmentManager();
				fm.beginTransaction().replace(R.id.content_frame, frag).commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
