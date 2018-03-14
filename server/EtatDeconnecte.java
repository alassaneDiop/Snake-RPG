
public class EtatDeconnecte implements Etat {

	ContexteJoueur joueur;

	public EtatDeconnecte(ContexteJoueur joueur) {
		this.joueur = joueur;
	}

	// Connecter le joueur et passer à l'état EtatConnecte
	public void connecter(String response) {
		joueur.setEtat(joueur.getEtatConnecte());
		response = "Vous êtes connectés.\n 100 [Bienvenue au jeu] / 204 [Identification échouée]";
	}

	public void deconnecter() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	// quitter le jeu
	public void quitter() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	// Choisir un pseudo
	public void choisirPseudo() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	public void rejoindreJeu() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	public void recevoirPlanJeu() {
		System.out.println("Vous êtes déjà déconnectés");
	}

	public void deplacer(String deplacement) {
		System.out.println("Vous êtes déjà déconnectés");
	}

}
