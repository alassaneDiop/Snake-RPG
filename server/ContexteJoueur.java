package server;

import java.net.Socket;
import java.util.ArrayList;

public class ContexteJoueur {

	Etat etatConnecte;
	Etat etatDeconnecte;

	Etat etat;

	// TCPServer server;
	Socket so;

	Joueur joueur;

	// public ContexteJoueur(TCPServer server, Socket so) {
	public ContexteJoueur(Socket so) {
		etatDeconnecte = new EtatDeconnecte(this);
		etatConnecte = new EtatConnecte(this);

		// this.server = server;
		this.so = so;

		etat = etatDeconnecte;
		joueur = new Joueur(so);
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Etat getEtatConnecte() {
		return etatConnecte;
	}

	public Etat getEtatDeconnecte() {
		return etatDeconnecte;
	}

	public String connecter(String pseudo) {
		return etat.connecter(pseudo);
	}

	public String listeJoueur() {
		return etat.listeJoueur();
	}

	public String listePartie() {
		return etat.listePartie();
	}

	public String creerPartie(String nomPartie) {
		return etat.creerPartie(nomPartie);
	}

	public String rejoindrePartie(int numeroPartie) {
		return etat.rejoindrePartie(numeroPartie);
	}

	// démarrer la partie de jeu
	public String startGame(String keySerpent) {
		if (ModeleDuJeu.getInstance().lesSerpents.containsKey(keySerpent)) {
			Partie.running = true;
			(new Partie()).start();

			return etat.startGame();
		}
		
		return "Veuiller rejoindre une partie de jeu";
	}

	// quitter la partie de jeu
	public String endGame(String keySerpent) {
		return etat.endGame(keySerpent);
	}

	// rejouer la partie de jeu
	public String restartGame(String keySerpent) {
		return etat.restartGame(keySerpent);
	}

	public String deplacer(String keySerpent, String direction) {
		switch (direction) {
		case "UP":
		case "DOWN":
		case "LEFT":
		case "RIGHT":
			return etat.deplacer(keySerpent, direction);

		default:
			return "Commande incorrecte. \"/turn UP | DOWN | LEFT | RIGHT\"";
		}
	}

	public void deconnecter() {
		etat.deconnecter();
	}

	public void quitter() {

	}

}
