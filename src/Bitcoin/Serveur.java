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
		
		try{
			sSocket = new ServerSocket(62002);
			System.out.println("Le serveur est initialisé");
			
			while(true){
				
				Socket s = sSocket.accept();
				System.out.println("Le serveur accepte la co");
				
				InputStream is=s.getInputStream();
				final Scanner sc=new Scanner(is);
				final OutputStream os=s.getOutputStream();
				
				Thread t = new Thread(new Runnable(){
					public void run(){
						do{
							try {
								os.write("Entrez un nombre suseptible de fonctionner:".getBytes());
							} catch (IOException e) {
								e.printStackTrace();
							}
							String nombre = sc.nextLine();
							System.out.println(nombre);
							/*if(existeEnBDD(nombre)){
								os.write("Dommage, le nombre à déjà été trouvé, essaie encore".getBytes());
							}
							else{
								os.write("Bravo, ce nombre n'avait pas été trouvé, félicitation".getBytes());
								//TODO ajoutEnBDD(nombre);
							}*/
						}while(sc.hasNext());
					}
				});
				
			}
			
			// sSocket.close();				// Il faudra trouver un moyen de fermer proprement
		
		}
		catch(BindException b){
			System.out.println("La connexion était déjà ouverte");
		}
		
	}
	 public static byte[] hashFunc(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		  MessageDigest digest = MessageDigest.getInstance("SHA-256");
		  digest.update(input.getBytes("UTF-8"));
		  byte[] hash = digest.digest();
		  
		  return hash;
		 }
}