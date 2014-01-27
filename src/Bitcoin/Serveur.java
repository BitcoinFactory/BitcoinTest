package Bitcoin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Serveur {
	private static ServerSocket sSocket;

	public static void main (String[] args) throws IOException{

		Serveur boo=new Serveur();
		sSocket = new ServerSocket(62000);
		

		boolean test = true;

		while(test == true){
			Socket s = sSocket.accept();
			
			InputStream is=s.getInputStream();
			final Scanner sc=new Scanner(is);
			final OutputStream os=s.getOutputStream();
			
			Thread t = new Thread(new Runnable(){
				public void run(){
					try {
						os.write("Entrez un nombre suseptible de fonctionner:".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					int nombre= sc.nextInt();
					/*if(existeEnBDD(nombre)){
						os.write("Dommage, le nombre à déjà été trouvé, essaie encore".getBytes());
					}
					else{
						os.write("Bravo, ce nombre n'avait pas été trouvé, félicitation".getBytes());
						//TODO ajoutEnBDD(nombre);
					}*/
				}
			});
			
		}
	}
	 public static byte[] hashFunc(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		  MessageDigest digest = MessageDigest.getInstance("SHA-256");
		  digest.update(input.getBytes("UTF-8"));
		  byte[] hash = digest.digest();
		  
		  return hash;
		 }
}