package com.example.venta;

import java.util.ArrayList;

public class Venta {
	
	/*articulos estaticos de prueba, borrar al terminar pruebas*/
	private static ArrayList<String> listadoArticulos=new ArrayList();
	

	public static ArrayList<String> getListadoArticulos() {
		return listadoArticulos;
	}

	public static void setListadoArticulos(ArrayList<String> listadoArticulos) {
		Venta.listadoArticulos = listadoArticulos;
	}
	public static void addListadoArticulos(String articulo){
		Venta.listadoArticulos.add(articulo);
	}
	/*fin pruebas*/
}
