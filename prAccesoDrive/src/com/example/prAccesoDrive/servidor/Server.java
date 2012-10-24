package com.example.prAccesoDrive.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Server extends Thread{
	
	public interface AuthorizationCodeListener{
		public void AuthorizationCodeRecibido(String codigo);
	}
	/*puerto de escucha*/
	private int port;
	private AuthorizationCodeListener mCallback;
	private CountDownLatch startsignal;
	public Server(int p,AuthorizationCodeListener l,CountDownLatch sg){
		port=p;
		mCallback=l;
		startsignal=sg;
		this.start();
	}
	
	

	public void run(){
		ServerSocket serversocket=null;
		try{
			serversocket=new ServerSocket(port);
		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}
		while (true){
			try{
				Socket connectionsocket=serversocket.accept();
				BufferedReader input=
							new BufferedReader(new InputStreamReader(connectionsocket.getInputStream()));
				System.out.println("Server: Mensaje Recibido-");
				DataOutputStream output=new DataOutputStream(connectionsocket.getOutputStream());
				http_handler(input,output);
			}catch(Exception e){
				System.out.println("error: "+e.getMessage());
			}			
			
		}
		
	}
	public void http_handler(BufferedReader in,DataOutputStream output){
		String tmp;
		try {
			tmp = in.readLine();
			if (tmp!=null){					
				String[] trozosHTTP=tmp.split("\\s+");
				switch(trozosHTTP[0]){
					case "GET":
						//comprobar si contiene el parámetro codigo:
						String parametroCode=new String("code=");
						int codeIndex=trozosHTTP[1].lastIndexOf(parametroCode);
						if (codeIndex>0){
							//existe el parámetro code
							//extraer el authorization code
							int endIndex=trozosHTTP[1].length();
							String authorizationCode=trozosHTTP[1].substring(codeIndex+parametroCode.length(), endIndex);
							System.out.println("Se ha recibido el código:"+authorizationCode);
							mCallback.AuthorizationCodeRecibido(authorizationCode);
							startsignal.countDown();
							output.writeBytes("HTTP/1.1 200 OK\r\n" +
												"Connection:Close");
							output.close();
						}
						break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
}
