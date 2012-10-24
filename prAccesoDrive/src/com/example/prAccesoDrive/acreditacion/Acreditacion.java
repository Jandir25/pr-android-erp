package com.example.prAccesoDrive.acreditacion;
import java.util.concurrent.*;

import java.net.URI;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.prAccesoDrive.servidor.*;
import com.example.prAccesoDrive.servidor.Server.AuthorizationCodeListener;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

/*
 * Instrucciones: developers.google.com/drive/credencials
 * Diagrama Secuencia:developers.google.com/accounts/docs/OAuth2
 */


public class Acreditacion implements Server.AuthorizationCodeListener {
	//Ruta a client_secrets.json debe contener un fichero JSON tal como:
	//{
	//  "web":{
	//		"client_id":"[[TU_CLIENT_ID]]",
	//		"client_secret":"[[TU_CLIENT_SECRET]]",
	//		"auth_uri":"https://accounts.google.com/o/oauth2/auth",
	//		"token_uri":"https://accoiunts.google.com/o/oauth2/token"
	//	}
	//}

	private static final String CLIENTSECRETS_LOCATION= "client_secrets.json";
	
	private static final String REDIRECT_URI = "http://localhost:9013/";
	
	private static final List<String> SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/drive.file",
			"https://www.googleapis.com/auth/userinfo.email",
			"https://www.googleapis.com/auth/userinfo.profile");
	
	private static GoogleAuthorizationCodeFlow flow=null;
	
	
	private static String authorizationCode="";
	/*
	 * Excepcion lanzada cuando un error da lugar mientras se recuperan las credenciales
	 */
	public static class GetCredentialsException extends Exception{
		protected String authorizationUrl;
		
		/**
		 * Construye un GetCredentialsExcepcion.
		 * 
		 * @param authorizationUrl la url de autorizacion al que se redirecciona al usuario
		 */
		public GetCredentialsException(String authorizationUrl){
			this.authorizationUrl=authorizationUrl;
		}
		/**
		 * Setter
		 */
		public void setAuthorizationUrl(String authorizationUrl){
			this.authorizationUrl=authorizationUrl;
		}
		/**
		 * Getter
		 */
		public String getAuthorizationUrl(){
			return authorizationUrl;
		}
	}
	
	/**
	 * Excepcion que es lanzada cuando falla el code exchange.
	 */
	
	public static class CodeExchangeException extends GetCredentialsException{
		/**
		 * Construye un CodeExchangeException
		 */
		
		public CodeExchangeException(String authorizationUrl){
			super(authorizationUrl);
		}
	}
	
	/**
	 * 
	 * Excepcion lanzada cuando no se encuentre refresh token
	 */
	
	public static class NoRefreshTokenException extends Exception{
		/**
		 * Construye un NoRefreshTokenException.
		 * @param authoriationUrl la url de autorizacion a la que redireccionar al usuario.
		 */
		
		public NoRefreshTokenException(String authorizationUrl){
			super(authorizationUrl);
		}
	}
	
	private static class NoUserIdException extends Exception{
		
	}
	
	/**
	 * Recuperar credenciales almacenadas para un id usuario dado.
	 * @param userId ,id usuario
	 * @return Credenciales almacenadas si encontradas, {@code null} EOC
	 * 
	 */
	
	static Credential getStoredCredentials(String userId){
		//TODO: Implementar este metodo para que funcione con BD local.Instanciar una nueva
		//instancia de la clase Credential con un accessToken almacenado y refreshToken
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * Almacenar credenciales OAuth 2.0 en la bd de la aplicacion.
	 * 
	 *  @param userId , la id del usuario.
	 *  @param credentials las credenciales OAuth 2.0 a almacenar.
	 */
	static void storeCredentials (String userId,Credential credentials){
		//TODO: Implementar este metodo para trabajar con la bd
		//Almacenar las credentials.getAccessToken() y credentials.getRefreshToken()
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * Construye una authorization flow y la almacena como un atributo estatico.
	 * 
	 * @return GoogleAuthorizationCodeFlow instancia
	 * @throws IOException incapaz de cargar client_secrets.json
	 * 
	 */
	static GoogleAuthorizationCodeFlow getFlow() throws IOException{
		if (flow==null){
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();
			GoogleClientSecrets clientSecrets =
					GoogleClientSecrets.load(jsonFactory,
							Acreditacion.class.getResourceAsStream(CLIENTSECRETS_LOCATION));
			flow =
					new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, SCOPES)
						.setAccessType("offline").setApprovalPrompt("force").build();
					
		}
		return flow;
	}
	
	/**
	 * Intercambiar codigo de autorizacion para las credenciales OAutho2.0
	 * 
	 * @param authorizationCode codigo de autorizacion para intercambiar por las credenciales OAuth2.0
	 * 
	 * @return OAuth 2.0 credentials
	 * @throws CodeExchangeException Se dio lugar a fallo
	 */
	
	static Credential exchangeCode(String authorizationCode)
		throws CodeExchangeException{
		try{
			GoogleAuthorizationCodeFlow flow = getFlow();
			GoogleTokenResponse response= 
						flow.newTokenRequest(authorizationCode).setRedirectUri(REDIRECT_URI).execute();
			return flow.createAndStoreCredential(response, null);
		}catch (IOException e){
			System.err.println("An error ocurred: "+e);
			throw new CodeExchangeException(null);
		}
	}
	
	/**
	 * Envia una solicitud al UserInfo API para recuperar la informacion de usuario.
	 * 
	 * @param credenciales OAuth 2.p para autorizar la solicitud
	 * @return Informacion Usuario
	 * @throws NoUserIdException Un error se dio lugar
	 */
	
	static Userinfo getUserInfo(Credential credentials)
		throws NoUserIdException{
		Oauth2 userInfoService=
				new Oauth2.Builder(new NetHttpTransport(),new JacksonFactory(), credentials).build();
		Userinfo userInfo= null;
		try{
			userInfo= userInfoService.userinfo().get().execute();
		}catch (IOException e){
			System.err.println("Ocurrio un error "+e);
		}
		if (userInfo !=null && userInfo.getId() !=null){
			return userInfo;			
		}else{
			throw new NoUserIdException();
		}
	}
	
	/**
	 * Recuperar la autorizacion URL
	 * @param emailAddress el email del usaurio
	 * @param state Estado de la autorizacion
	 * @return Authorization URL  a la que redireccionar al usuario.
	 * @throws IOException incapaz de cargar el client_secrets.json
	 */
	public static String getAuthorizationUrl(String emailAddress,String state) throws IOException{
		GoogleAuthorizationCodeRequestUrl urlBuilder =
				getFlow().newAuthorizationUrl().setRedirectUri(REDIRECT_URI).setState(state);
		urlBuilder.set("user_id", emailAddress);
		return urlBuilder.build();
	}
	
	/**
	 * Recuperar las credenciales usando el codigo de autorizacion provisto.
	 * 
	 * Esta funcion intercambia el codigo de autorizacion por un access token y consulta
	 * el UserInfo API para recuperar el email de usuario . Si un refresh token ha sido recuperado
	 * junto con un token de acceso, es almacenado la DB de la aplicacion usando el email como llave.
	 * Sino se recupero un refresh token, la funcion busca una en la bd y la devuelve si fue encontrada o devuelve
	 * Exception NoRefreshTokenException con la autoricacion de la URL a la que redireccionar al usuario.
	 * 
	 *  @parama authorizationCode codigo de autorizacion para usar al recuperar un access token.
	 *  
	 *  @param state estado al que poner el URL de autorizacion en caso de error.
	 *  @return instancia credentials OAuth 2.0 conteniendo un access token y un refresh token.
	 *  @throws NoRefreshTokenException No se encontro Refresh token
	 *  @throws IOException, incapaz de cargar client_secrets.json. 
	 */
	
	public static Credential getCredentials(String authorizationCode,String state)
		throws CodeExchangeException,NoRefreshTokenException,IOException{
		String emailAddress = "";
		try{
			Credential credentials = exchangeCode (authorizationCode);
			Userinfo userInfo = getUserInfo(credentials);
			String userId = userInfo.getId();
			emailAddress = userInfo.getEmail();
			if (credentials.getRefreshToken() != null){
				storeCredentials(userId,credentials);
				return credentials;
			}else{
				credentials = getStoredCredentials(userId);
				if (credentials !=null && credentials.getRefreshToken() != null) {
					return credentials;
				}
			}
		}catch (CodeExchangeException e){
			e.printStackTrace();
			//las aplicaciones drive deberian tratar de recuperar el usuario y credenciales
			//para la sesion actual.
			//si ninguna esta disponible, redireccionar al la url de autentificacion
			e.setAuthorizationUrl(getAuthorizationUrl(emailAddress,state));
			throw e;
		}catch (NoUserIdException e){
			e.printStackTrace();
		}
		//No se encontro el Refresh Token
		String authorizationUrl = getAuthorizationUrl(emailAddress,state);
		throw new NoRefreshTokenException(authorizationUrl);
	}
	
	
	/*
	 * metodos de prueba , BORRRAR
	 * 
	 */
	public static void openURI(String url)
	{	
		if( !java.awt.Desktop.isDesktopSupported() ) {
	
	        System.err.println( "Desktop is not supported (fatal)" );
	        System.exit( 1 );
	    }
	
	    if ( url.length() == 0 ) {
	
	        System.out.println( "Usage: OpenURI(String url)" );
	        System.exit( 0 );
	    }
	
	    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
	
	    if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
	
	        System.err.println( "Desktop doesn't support the browse action (fatal)" );
	        System.exit( 1 );
	    }
	
	    
	
        try {

            java.net.URI uri = new java.net.URI( url);
            desktop.browse( uri );
        }
        catch ( Exception e ) {

            System.err.println( e.getMessage() );
        }
	    
	}
	
	
	public static void main(String[] args) {
		
		//Construir url de solicitud codigo autentificacion
		
		//lanzar servidor de escucha
		
		//Mostrar navegador pagina de consentimiento					
		try {
			
			try {
				//montamos el objeto acreditacion
				CountDownLatch startSignal = new CountDownLatch(1);
				Acreditacion ac=new Acreditacion();
				//construimos el url al que redireccionar al usuario para obtener consentimiento.
				ac.getFlow();
				
				//preparamos el servidor para que este listo para escuchar el String con el authorizationCode
				//escuchamos en el puerto 9013, indicado en el REDIRECT_URI
				Server server=new Server(9013, new Acreditacion(),startSignal);
				//mostramos el navegador con el dialogo de consentimiento
				String url = ac.getAuthorizationUrl("erpisha@gmail.com", null);
				openURI(url);
				startSignal.await();
				try {
					Credential accessToken=ac.exchangeCode(authorizationCode);
				} catch (CodeExchangeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
														
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
		
	}

	@Override
	public void AuthorizationCodeRecibido(String authorizationCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		this.authorizationCode=authorizationCode;
				
	}

	
	
}
