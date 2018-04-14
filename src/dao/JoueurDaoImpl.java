/** 
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import server.*;
import model.Joueur;
import static dao.DAOUtilitaire.*;

/**
 * @author alassane
 *
 */

public class JoueurDaoImpl implements JoueurDao {

	private DAOFactory daoFActory;
	private static final String SQL_SELECT_PAR_PSEUDO = "SELECT * FROM joueur WHERE pseudo = ? and password = ?";
	public static final String SQL_UPDATE = "UPDATE joueur SET score = ?, niveau = ? WHERE pseudo = ?";

	public JoueurDaoImpl() {

	}

	public JoueurDaoImpl(DAOFactory daoFactory) {
		this.daoFActory = daoFactory;
	}

	@Override
	// mettre à jour les données du joueur en paramétre
	public void creer(Joueur joueur) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			connection = daoFActory.getConnection();

			preparedStatement = initialisationRequetePreparee(connection,
					SQL_UPDATE, true, joueur.score, joueur.niveau,
					joueur.pseudo);

			int statut = preparedStatement.executeUpdate();

			if (statut == 0) {
				throw new Exception("Echec de la mise à jour du joueur");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement,
					connection);
		}

	}

	@Override
	// retrouver un joueur qui tente de se connecter
	public Joueur trouver(String pseudo, String password) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Joueur joueur = null;

		try {
			connection = daoFActory.getConnection();
			preparedStatement = initialisationRequetePreparee(connection,
					SQL_SELECT_PAR_PSEUDO, true, pseudo, password);
			resultSet = preparedStatement.executeQuery();

			// Récupération des données
			joueur = map(resultSet); // map est une fonction de DAOUtilitaire

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Probléme de connection");
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return joueur;
	}

	// cette fonction est appelée à chaque fin de partie pour mettre à jour les
	// données des joueurs (score et niveau)
	public void process(List<ContexteJoueur> lesJoueurs) {

		for (ContexteJoueur contexteJoueur : lesJoueurs) {
			Joueur joueur = new Joueur(contexteJoueur.joueur.pseudo,
					contexteJoueur.joueur.serpent.eatCount,
					contexteJoueur.joueur.serpent.niveau);

			System.out.println(joueur.toString());

			creer(joueur);
		}

	}

}
