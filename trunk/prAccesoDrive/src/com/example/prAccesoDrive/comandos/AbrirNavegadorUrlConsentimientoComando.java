package com.example.prAccesoDrive.comandos;

import com.example.prAccesoDrive.acreditacion.Acreditacion;
import com.example.prAccesoDrive.invocador.Comando;

public class AbrirNavegadorUrlConsentimientoComando implements Comando{
	
	private Acreditacion ac;
	public AbrirNavegadorUrlConsentimientoComando(Acreditacion a){
		ac= a;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Acreditacion.openURI(ac.getUrlConsentimiento());		
	}

}
