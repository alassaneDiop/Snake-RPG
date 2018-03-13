
public class ContexteJoueur {

	Etat etatConnecte;
	Etat etatDeconnecte;

	Etat etat = etatDeconnecte;


	public ContexteJoueur () {
		
	}

	public void setEtat (Etat etat) {
		this.etat = etat;
	}


	public Etat getEtatConnecte () {
		return etatConnecte;
	}


	public Etat getEtatDeconnecte () {
		return etatDeconnecte;
	}

	public void connecter () {
		etat.connecter();
		// joueur.setEtat(joueur.getEtatConnecte());
		// System.out.println("Vous êtes déjà déconnectés");
	}

}
