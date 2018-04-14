/** 
 * 
 */
package server;

import dao.DAOFactory;
import dao.JoueurDaoImpl;

/**
 * @author alassane
 *
 */

public class EtatDeconnecte implements Etat {

	ContexteJoueur joueur;

	// TCPServer server;

	public EtatDeconnecte(ContexteJoueur joueur) {
		this.joueur = joueur;
	}

	// Connecter le joueur et passer à l'état EtatConnecte
	@Override
	public String connecter(String pseudo, String password) {
		if (pseudo == null)
			return "Commande incorrecte. \"/join votrePseudo-password\"";

		// chercher le joueur
		if ((new JoueurDaoImpl(DAOFactory.getInstance())).trouver(pseudo,
				password) == null)
			return "Joueur inconnu ou mot de passe incorrect";

		else {
			// tester si le pseudo est indispo
			if (TCPServer.checkPseudo(pseudo))
				return "Un joueur est deja connecte avec ce pseudo";
		}

		joueur.joueur.pseudo = pseudo;
		joueur.setEtat(joueur.getEtatConnecte());
		TCPServer.addJoueur(joueur);
		ServerReceive.pseudo = pseudo;

		return "join:" + joueur.joueur.getPseudo("connecté");
	}

	@Override
	public void deconnecter() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	// quitter le jeu
	@Override
	public void quitter(String pseudo) {
		System.out.println("Vous êtes déjà déconnectés");
	}

	@Override
	public String rejoindreGame() {
		return "Cette commande n'est pas disponible";
	}

	@Override
	public String deplacer(String keySerpent, String direction) {

		return null;
	}

	@Override
	public String listeJoueur() {
		return "Cette information n'est pas disponible";
	}

	@Override
	public String startGame() {
		return "Cette information n'est pas disponible";
	}

	@Override
	public String endGame(String keySerpent) {
		return "Cette information n'est pas disponible";
	}

	@Override
	public String restartGame(String keySerpent) {
		return "Cette information n'est pas disponible";
	}
}
