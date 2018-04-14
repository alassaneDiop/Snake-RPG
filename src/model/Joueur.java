/** 
 * 
 */
package model;

import java.net.Socket;

/**
 * @author alassane
 *
 */

public class Joueur {

	public int id;
	public String pseudo;
	public String password;
	public int score;
	public int niveau;

	public Serpent serpent;

	public Socket so;

	public Joueur(Socket so) {
		this.so = so;
	}

	public Joueur(int id, String pseudo, int score, int niveau) {
		this.id = id;
		this.pseudo = pseudo;
		this.score = score;
		this.niveau = niveau;
	}

	public Joueur(String pseudo, int score, int niveau) {
		this.pseudo = pseudo;
		this.score = score;
		this.niveau = niveau;
	}

	public String getPseudo(String message) {
		return pseudo + " " + message;
	}

	public String toString() {
		return "Pseudo : " + pseudo + " score : " + score + "; niveau : "
				+ niveau;
	}

}