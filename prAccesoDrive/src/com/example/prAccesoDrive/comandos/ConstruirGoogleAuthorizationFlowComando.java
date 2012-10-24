package com.example.prAccesoDrive.comandos;
import java.io.IOException;

import com.example.prAccesoDrive.invocador.Comando;
import com.example.prAccesoDrive.acreditacion.*;
public class ConstruirGoogleAuthorizationFlowComando implements Comando{
	
	private static final String email="erpisha@gmail.com";
	
	private Acreditacion acreditacion;
	
	
	public ConstruirGoogleAuthorizationFlowComando(Acreditacion a){
		acreditacion=a;
		
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		try {
			String urlConsentimiento=Acreditacion.getAuthorizationUrl(email, null);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
