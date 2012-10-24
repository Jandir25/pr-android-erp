package com.example.prAccesoDrive.comandos;

import java.util.concurrent.CountDownLatch;

import com.example.prAccesoDrive.acreditacion.Acreditacion;
import com.example.prAccesoDrive.invocador.Comando;
import com.example.prAccesoDrive.servidor.*;
public class LanzarHebraServidorComando implements Comando{

	private Server servidor;
	public LanzarHebraServidorComando(Acreditacion a,CountDownLatch sg){
		servidor=new Server(Integer.parseInt(a.getPuerto()), a, sg);
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
