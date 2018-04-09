package server;

import java.util.Random;

public class Grenouille extends Case {

	final static Random RND = new Random();

	public Grenouille() {
		super(getRandomX(), getRandomY());
	}

	private static int getRandomX() {
		return RND.nextInt((NBRE_DE_COLONNES -1) - 1) + 1;
		// return RND.nextInt(NBRE_DE_COLONNES - 3);
	}

	private static int getRandomY() {
//		return RND.nextInt(NBRE_DE_LIGNES);
		return RND.nextInt((NBRE_DE_LIGNES-1) - 3) + 3;
	}

	public void nouvelleGrenouille() {
		xIndice = getRandomX();
		yIndice = getRandomY();
	}

	public String getPosition() {
		String chaine = "";
		chaine += xIndice + "," + yIndice + ";";

		return chaine.substring(0, chaine.length() - 1); // supprimer le dernier
															// ';'
	}

}
