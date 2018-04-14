/** 
 * 
 */
package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author alassane
 *
 */
// Thread pour recevoir
class ClientReceive extends Thread {

	Socket so = null;
	DataInputStream entree = null;
	String response;

	JFenetre fenetre;

	public ClientReceive(Socket so) {
		this.so = so;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		while (!so.isClosed()) {
			try {
				entree = new DataInputStream(so.getInputStream());
				response = entree.readLine();
				// if (response == "quit") {
				// try {
				// entree.close();
				// so.close();
				// break;
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}

			// System.out.println("Réponse du serveur : " + response);
			gestionMessageServer(response);
		}
	}

	// faire le traitement correspondant à la réponse du serveur
	public void gestionMessageServer(String message) {

		String request = null;
		try {

			if (message.contains(":")) {
				request = message.split(":")[0];
			}

			if (request != null) {
				switch (request) {
				case "join":
					// renseigner le pseudo du joueur
					Joueur.getInstance(so).pseudo = (message.split(":")[1])
							.split(" ")[0];
					System.out.println("Reponse du serveur : " + message);
					break;

				case "listJoueur":
					System.out.println("Reponse du serveur : " + message);
					break;

				case "newGame":
					Facade.splitMessage(message.split(":")[1]);
					System.out.println("Reponse du serveur : " + message);
					break;

				case "startGame":
				case "joinGame":
					Facade.splitMessage(message.split(":")[1]);
					JFenetre.request = "turn";
					Facade.startGame();
					break;

				case "endGame":
					JFenetre.request = "endGame";
					break;

				case "restartGame":
					JFenetre.gameOver = false;
					JFenetre.request = "turn";
					break;

				case "turn":
					if (!JFenetre.gameOver) {
						Facade.splitMessage(message.split(":")[1]);
						JFenetre.request = "turn";
					} else {
						JFenetre.request = "gameOver";
					}
					break;

				default:
					System.out.println("Réponse du serveur : " + message);
					break;
				}
			} else {
				System.out.println("Réponse du serveur : " + message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
