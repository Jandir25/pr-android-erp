import java.io.*;
public class PruebaFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File fileContent= new File("document.txt");
		if (fileContent.exists()) {
			System.out.println("existe");
		}
		System.out.println("ejecutado");
		
	}

}
