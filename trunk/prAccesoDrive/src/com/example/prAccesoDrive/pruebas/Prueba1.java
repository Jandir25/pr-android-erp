package com.example.prAccesoDrive.pruebas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.example.prAccesoDrive.acreditacion.*;
import com.example.prAccesoDrive.comandos.AbrirNavegadorUrlConsentimientoComando;
import com.example.prAccesoDrive.comandos.ConstruirGoogleAuthorizationFlowComando;
import com.example.prAccesoDrive.comandos.LanzarHebraServidorComando;

import com.example.prAccesoDrive.invocador.Comando.*;
import com.example.prAccesoDrive.invocador.Invocador;
public class Prueba1 {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
			
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/*Lista Receptores*/
		/*objeto acreditacion*/
		Acreditacion ac = new Acreditacion();
		/*objeto de sincronizacion con el servidor*/
		CountDownLatch startSignal=new CountDownLatch(1);
		//FIN receptores
		//Crear el objeto invocador.
		Invocador invocador=new Invocador();

		//Lista comandos
		//Construir el GoogleAuthorizationCodeFlow, que devuelva el urlConsentimiento
		invocador.agregarComando(new ConstruirGoogleAuthorizationFlowComando(ac));		
		//Tarea que lance hebra Servidor en escucha del Authorization code
		invocador.agregarComando(new LanzarHebraServidorComando(ac, startSignal));
		//Tarea que despliegue el navegador apuntando a la url 
		invocador.agregarComando(new AbrirNavegadorUrlConsentimientoComando(ac));
		//*EJECUCION COMANDOS		
		//Obtener la url,NOTA:la salida se guarda en ac.getUrlConsentimiento();
		invocador.ejecutarComando(0);
		/*Iniciar hebra con Servidor de escucha, parametrizar con : -Objeto Implemente AuthorizationCodeListener,para comunicar la recepcion del codigo
		 * 													 -Objeto CountDownLatch para sincronizacion
		 * 													 -indicar puerto de escucha
		 * 									
		 */
		
		invocador.ejecutarComando(1);
		//Iniciar navegador con la url indicada
		invocador.ejecutarComando(2);
		//Bloquear hebra principal, y esperar a que el servidor nos despierte
		startSignal.await();							
		//Intercambiar el authorization code por el access token
		
		
		System.out.println("url consentimiento: "+ac.getUrlConsentimiento());
		System.out.println("Authorization code: "+ac.getAuthorizationCode());
	}



}
