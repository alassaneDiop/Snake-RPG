/** 
 * 
 */
package client;

/**
 * @author alassane
 *
 */
public class Grenouille extends Case {

	public Grenouille(int x, int y) {
		super(x, y);
	}

	// afficher la grenouille représentée par 0
	public void affichage(String[][] tab) {
		tab[yIndice][xIndice] = "O";
	}

}
