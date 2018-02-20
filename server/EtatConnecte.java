
public class EtatConnecte implements Etat {

	ContexteJoueur joueur;
	

	public EtatConnecte (ContexteJoueur joueur) {
		this.joueur = joueur;
	}


	public void connecter () {
		System.out.println("Vous êtes déjà connectés");
	}


	// déconnecter le joueur et passer à l'état EtatDeconnecte
	public void	deconnecter () {
		joueur.setEtat(joueur.getEtatDeconnecte());
	}


	// quitter le jeu
	public void quitter () {
	}


	// Choisir un pseudo
	public void choisirPseudo() {
	}


	public void rejoindreJeu () {
	}


	public void recevoirPlanJeu () {
	}


	public void deplacer (String deplacement) {

	}

}
