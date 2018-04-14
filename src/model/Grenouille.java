/** 
 * 
 */
package model;

import java.util.Random;

/**
 * @author alassane
 *
 */

public class Grenouille extends Case {

	final static Random RND = new Random();

	public Grenouille() {
		super(getRandomX(), getRandomY());
	}

	private static int getRandomX() {
		return RND.nextInt((NBRE_DE_COLONNES - 1) - 1) + 1;
	}

	private static int getRandomY() {
		return RND.nextInt((NBRE_DE_LIGNES - 1) - 3) + 3;
	}

	// générer une nouvelle grenouille
	public void nouvelleGrenouille() {
		xIndice = getRandomX();
		yIndice = getRandomY();
	}

	// parser la position de la grenouille
	public String getPosition() {
		String chaine = "";
		chaine += xIndice + "," + yIndice + ";";

		return chaine.substring(0, chaine.length() - 1); // supprimer le dernier
															// ';'
	}

}
