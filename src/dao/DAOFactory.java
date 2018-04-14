/** 
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author alassane
 *
 */
public class DAOFactory {

	private String url;
	private String username;
	private String password;
	private Connection connexion;

	public DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DAOFactory getInstance() {

		String url = "jdbc:mysql://localhost:3306/snake";
		String username = "root";
		String password = "";

		DAOFactory instance = new DAOFactory(url, username, password);
		return instance;
	}

	Connection getConnection() throws SQLException {
		// Chargement du driver
		try {
			System.out.println("chargement du drive");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("impossible de charger le driver");
		}

		Properties properties = new Properties();
		properties.setProperty("user", this.username);
		properties.setProperty("password", this.password);
		properties.setProperty("useSSL", "false");
		properties.setProperty("autoReconnect", "true");

		try {
			this.connexion = DriverManager.getConnection(this.url, properties);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de se connecter à la bdd");
		}

		return this.connexion;
	}

	public JoueurDao getJoueurDao() {
		return new JoueurDaoImpl(this);
	}

}
