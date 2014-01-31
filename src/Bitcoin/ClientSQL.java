package Bitcoin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientSQL {

	public ClientSQL(String pseudo, String mdp){

		boolean authentifie;
		Scanner sc = new Scanner(System.in);

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //On se connecte à la BDD
			String url = "jdbc:mysql://db4free.net:3306/bitcoindb";
			String user = "projettutbtc";
			String passwd = "bitcoin";
			conn = DriverManager.getConnection(url,user,passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unused")
		java.sql.Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		try {

			do{

				System.out.print("Entrez votre identifiant:");
				pseudo = sc.nextLine();

				System.out.print("Entrez votre mot de passe:");
				mdp = sc.nextLine();
				try {
					mdp=encodeMD5(mdp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				PreparedStatement co = conn.prepareStatement("SELECT * FROM bitcoin WHERE Identifiant = ? AND MotDePasse LIKE BINARY ?");
				co.setString(1, pseudo);
				co.setString(2, mdp);

				ResultSet r=co.executeQuery(); //Ici, on regarde si les identifiants rentrés correspondent bien à une personne dans la BDD


				authentifie = r.next();

				if(authentifie){
					System.out.println("Autentification reussie");

				}
				else{
					System.out.println("Autentification échouée");

				}
			}while(!authentifie);


			PreparedStatement ps = conn.prepareStatement("SELECT * FROM compteBitcoin WHERE identifiant = ? ");
			ps.setString(1, pseudo);

			ResultSet nb=ps.executeQuery();
			Long nombre= (long) 0;
			if(nb.next())
				nombre = nb.getLong("dernierNbMine"); //On récupere le dernier nombre que l'utilisateur a tenté de miner lors de sa derniere connexion afin qu'il ne reparte pas de 0

			while(true){

				testRecompense(nombre, conn, pseudo); 				//On verifie si le nombre correspond à ceux recherchés et si il n'a pas déja été trouvé
				System.out.println(nombre);
				nombre ++;								//On passe au nombre suivant


				if(nombre % 1000 == 0){  				//On ajoute une sécurité comme quoi si le programme se ferme brusquement, il y a une sauvegarde tous les 1000 nombres testés
					PreparedStatement bla = conn.prepareStatement("UPDATE compteBitcoin SET dernierNbMine = ? WHERE identifiant = ?");
					bla.setLong(1, nombre);
					bla.setString(2, pseudo);
					bla.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	



	public static void testRecompense(Long nombre, Connection conn, String pseudo){

		String nombreString = nombre.toString();
		String nombreCode=encodeSHA256(nombreString); //On encore le nombre en SHA256
		if(nombreCode.startsWith("000")){
			PreparedStatement test;
			try {
				test = conn.prepareStatement("SELECT Nombre FROM NombreBitcoin WHERE Nombre = ? "); //On regarde si le nombre trouvé existe deja en BDD
				test.setLong(1, nombre);
				ResultSet bla= test.executeQuery();

				if(!bla.next()){
					test = conn.prepareStatement("INSERT INTO NombreBitcoin (Nombre) VALUES (?)"); //Si non, on l'insere,
					test.setLong(1, nombre);
					test.executeUpdate();

					test = conn.prepareStatement("SELECT * FROM compteBitcoin WHERE identifiant = ? "); //On recupere le nombre de bitcoin de l'utilisateur
					test.setString(1, pseudo);
					bla= test.executeQuery();
					bla.next();
					double bitcoin = bla.getDouble("bitcoin");


					test = conn.prepareStatement("UPDATE compteBitcoin SET bitcoin = ? WHERE identifiant = ?"); //On le récompense de 0.01 bitcoin
					test.setDouble(1, bitcoin+0.01);
					test.setString(2, pseudo);
					test.executeUpdate();
				}


			} catch (SQLException e) {
				e.printStackTrace();
			}


		}



	}




	public static String encodeSHA256(String base) {
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}


	public static String encodeMD5(String data) throws Exception {

		/* Check the validity of data */
		if (data == null || data.isEmpty()) {
			throw new IllegalArgumentException("Null value provided for "
					+ "MD5 Encoding");
		}

		/* Get the instances for a given digest scheme MD5 or SHA */
		MessageDigest m = MessageDigest.getInstance("MD5");

		/* Generate the digest. Pass in the text as bytes, length to the
		 * bytes(offset) to be hashed; for full string pass 0 to text.length()
		 */
		m.update(data.getBytes(), 0, data.length());

		/* Get the String representation of hash bytes, create a big integer
		 * out of bytes then convert it into hex value (16 as input to
		 * toString method)
		 */
		String digest = new BigInteger(1, m.digest()).toString(16);

		return digest;
	}
}
