/** 
 * 
 */
package dao;

import model.Joueur;

/**
 * @author alassane
 *
 */

public interface JoueurDao {

	void creer(Joueur joueur); // mettre à jour le score et le niveau d'un
								// joueur

	Joueur trouver(String pseudo, String password); // identifier un joueur

}
