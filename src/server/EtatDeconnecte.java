package server;

public class EtatDeconnecte implements Etat {

	ContexteJoueur joueur;

	// TCPServer server;

	public EtatDeconnecte(ContexteJoueur joueur) {
		this.joueur = joueur;
	}

	// Connecter le joueur et passer à l'état EtatConnecte
	@Override
	public String connecter(String pseudo) {
		if (pseudo == null)
			return "Commande incorrecte. \"/join votrePseudo\"";

		// tester si le pseudo est dispo
		if (!TCPServer.checkPseudo(pseudo))
			joueur.joueur.pseudo = pseudo;
		else
			return "Erreur pseudo pas disponible";

		joueur.setEtat(joueur.getEtatConnecte());
		TCPServer.addJoueur(joueur);

		return "join:" + joueur.joueur.getPseudo("valide");
		// return "pseudo valide";
		// "Vous êtes connectés.\n 100 [Bienvenue au jeu] / 204 [Identification échouée]";
	}

	@Override
	public void deconnecter() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	// quitter le jeu
	@Override
	public void quitter() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	// Choisir un pseudo
	// @Override
	// public String choisirPseudo(String pseudo) {
	// return "Cette commande n'est pas disponible";
	// }

	@Override
	public String rejoindrePartie(int partie) {
		return "Cette commande n'est pas disponible";
	}

	@Override
	public void recevoirPlanJeu() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	@Override
	public String deplacer(String keySerpent, String direction) {

		return null;
	}

	@Override
	public String creerPartie(String nomPartie) {
		return "Cette commande n'est pas disponible";

	}

	@Override
	public String listeJoueur() {
		return "Cette information n'est pas disponible";
	}

	@Override
	public String listePartie() {
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
