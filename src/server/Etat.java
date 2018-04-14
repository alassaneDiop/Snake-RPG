/** 
 * 
 */
package server;

/**
 * @author alassane
 *
 */

public interface Etat {

	public String connecter(String pseudo, String password);

	public void deconnecter();

	public void quitter(String pseudo);

	public String deplacer(String keySerpent, String direction);

	public String rejoindreGame();

	public String listeJoueur();

	public String startGame();

	public String endGame(String keySerpent);

	public String restartGame(String keySerpent);

}
