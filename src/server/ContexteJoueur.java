/** 
 * 
 */
package server;

import java.net.Socket;
import model.Joueur;

/**
 * @author alassane
 *
 */

public class ContexteJoueur {

	Etat etatConnecte;
	Etat etatDeconnecte;

	Etat etat;

	Socket so;

	public Joueur joueur;

	public ContexteJoueur(Socket so) {
		etatDeconnecte = new EtatDeconnecte(this);
		etatConnecte = new EtatConnecte(this);

		this.so = so;

		etat = etatDeconnecte;
		joueur = new Joueur(so);
	}

	public void replace(ContexteJoueur cj) {
		setEtat(cj.etat);
		this.joueur.so = cj.joueur.so;
		this.joueur = cj.joueur;
		this.joueur.password = cj.joueur.password;
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

	// connexion
	public String connecter(String pseudo, String password) {
		return etat.connecter(pseudo, password);
	}

	// liste des joueurs connectés
	public String listeJoueur() {
		return etat.listeJoueur();
	}

	public String rejoindreGame() {
		return etat.rejoindreGame();
	}

	// démarrer la partie de jeu
	public String startGame(String keySerpent) {
		return etat.startGame();
	}

	// quitter la partie de jeu
	public String endGame(String keySerpent) {
		return etat.endGame(keySerpent);
	}

	// rejouer la partie de jeu
	public String restartGame(String keySerpent) {
		return etat.restartGame(keySerpent);
	}

	// prendre en compte le déplacement du serpent
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

	// deconnexion
	public void deconnecter() {
		etat.deconnecter();
	}

	// quitter le jeu
	public void quitter(String pseudo) {
		etat.quitter(pseudo);

	}

}
