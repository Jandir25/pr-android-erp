




import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
/*
 * Client ID: 730385891944-7pem7u33iin1bris9lvmjaogqvsvqur9.apps.googleusercontent.com
 * Client secret:	cDJfoM-BuechM6M9Z0emxmpo
 * Redirect uri: urn:ietf:wg:oauth:2.0:oob
 */
import java.util.concurrent.CountDownLatch;

public class DriveCommandLine implements Server.AuthorizationCodeListener{

  private static String CLIENT_ID = "602012443819.apps.googleusercontent.com";
  private static String CLIENT_SECRET = "4eTLo79nwpA2ZuwNLzXQ-Lt6";

  private static String REDIRECT_URI = "http://localhost:9004/";
  private static String inputCode=null;
  private static String authorizationCode="";
  public static void main(String[] args) throws IOException {
	 
    HttpTransport httpTransport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();
   
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
        .setAccessType("offline")
        .setApprovalPrompt("auto").build();
    
    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
    
    System.out.println("Please open the following URL in your browser then type the authorization code:");
    java.net.URI uri;
	try {
		CountDownLatch startSignal = new CountDownLatch(1);
		uri = new URI(url);
		Server servidor=new Server(9004,new DriveCommandLine(),startSignal);
		Desktop.getDesktop().browse(uri);		
		/*parar la hebra main*/
		startSignal.await();
					
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	
    
    String code =authorizationCode; 
    
    GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
    Credential credential = flow.createAndStoreCredential(response, CLIENT_ID);
    //GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
    
    //Create a new authorized API client
    Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();

    //Insert a file  
    File body = new File();    
    body.setTitle("My document");
    body.setDescription("A test document");
    body.setMimeType("text/plain");
    
    java.io.File fileContent = new java.io.File("document.txt");
    FileContent mediaContent = new FileContent("text/plain", fileContent);

    File file = service.files().insert(body, mediaContent).execute();
    System.out.println("File ID: " + file.getId());
  }

@Override
public void AuthorizationCodeRecibido(String codigo) {
	// TODO Auto-generated method stub
	authorizationCode=codigo;	
	System.out.println("Llamada a metodo callback, resultado: "+codigo);
	
}
}