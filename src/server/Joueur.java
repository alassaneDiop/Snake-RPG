package server;

import java.net.Socket;

public class Joueur {

	String pseudo;
	int scrore;
	int niveau;

	Serpent serpent;

	Socket so;

	public Joueur(Socket so) {
		this.so = so;
	}

	public Joueur(String pseudo, int scrore, int niveau) {
		this.pseudo = pseudo;
		this.scrore = scrore;
		this.niveau = niveau;
	}

	public String getPseudo(String message) {
		return pseudo + " " + message;
	}

	public String toString() {
		return "Pseudo : " + pseudo + " score : " + scrore + "; niveau : "
				+ niveau;
	}

}