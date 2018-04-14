/** 
 * 
 */
package client;

import java.net.Socket;

/**
 * @author alassane
 *
 */
public class Joueur {

	String pseudo;
	int scrore;
	int niveau;

	private static Joueur instance;

	Socket so;

	private Joueur(Socket so) {
		this.so = so;
	}

	public static Joueur getInstance() {
		return instance;
	}

	public static Joueur getInstance(Socket so) {
		if (instance == null)
			instance = new Joueur(so);

		return instance;
	}

	public String toString() {
		return "Pseudo : " + pseudo + " score : " + this.scrore + "; niveau : "
				+ niveau;
	}

}