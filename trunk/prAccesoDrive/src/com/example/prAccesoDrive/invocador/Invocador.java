package com.example.prAccesoDrive.invocador;

import java.util.ArrayList;
import java.util.List;



public class Invocador {
	private List<Comando> lComandos;
	
	public Invocador(){
		lComandos=new ArrayList<>();
	}
	
	public void agregarComando(Comando c){
		lComandos.add(c);
	}
	
	public int numeroComandos(){
		return lComandos.size();
	}
	public void ejecutarComando(int i){
		if (lComandos.get(i)!=null){
			lComandos.get(i).execute();
		}
	}
}
