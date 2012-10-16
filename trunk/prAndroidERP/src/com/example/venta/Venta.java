package com.example.venta;

public class Venta {
	
	/*articulos estaticos de prueba, borrar al terminar pruebas*/
	private static String[] listadoArticulos={
		"Sudadera",
		"Reloj",
		"Monedero",
		"Vestido"
	};

	public static String[] getListadoArticulos() {
		return listadoArticulos;
	}

	public static void setListadoArticulos(String[] listadoArticulos) {
		Venta.listadoArticulos = listadoArticulos;
	}
	/*fin pruebas*/
}
