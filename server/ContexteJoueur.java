
public class ContexteJoueur {

	Etat etatConnecte;
	Etat etatDeconnecte;

	Etat etat;

	public ContexteJoueur() {
		etatDeconnecte = new EtatDeconnecte(this);
		etatConnecte = new EtatConnecte(this);

		etat = etatDeconnecte;
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

	public void connecter(String response) {
		etat.connecter(response);
		// joueur.setEtat(joueur.getEtatConnecte());
		// System.out.println("Vous êtes déjà déconnectés");
	}

}
