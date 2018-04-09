package client;

public class Case implements Constantes {

	int xIndice;
	int yIndice;

	public Case(int xIndice, int yIndice) {
		this.xIndice = xIndice;
		this.yIndice = yIndice;
	}

	// renvoie true si la case est contenue dans le plateau de jeu
	public boolean estValide() {
		return this.xIndice >= 0 && this.xIndice < NBRE_DE_COLONNES
				&& this.yIndice >= 0 && this.yIndice < NBRE_DE_LIGNES;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Case) {
			Case box = (Case) obj;
			return this.xIndice == box.xIndice && this.yIndice == box.yIndice;
		}
		return false;
	}

}