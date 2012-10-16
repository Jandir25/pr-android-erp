package com.example.venta;

public class Articulo {
	private String cod_barras;
	private String nombre;
	private String talla;
	private String color;
	private String etiqueta;	
	private int existencias;
	private int PVP;
	private int PVM;
	
	
	public Articulo(String cod,String nom,String tl,String etq,int e,int p_venta,int p_mayor){
		cod_barras=cod;
		nombre=nom;
		talla=tl;
		etiqueta=etq;
		existencias=e;
		PVP=p_venta;
		PVM=p_mayor;
	}
	
	
}
