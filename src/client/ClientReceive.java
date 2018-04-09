package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

//Thread pour recevoir
class ClientReceive extends Thread {

	Socket so = null;
	DataInputStream entree = null;
	String response;
	ObjectInputStream entreeObject = null;

	JFenetre fenetre;

	public ClientReceive(Socket so) {
		this.so = so;
	}

	public void run() {

		while (true) {
			try {
				entree = new DataInputStream(so.getInputStream());
				response = entree.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

//			 System.out.println("Réponse du serveur : " + response);
			gestionMessageServer(response);
		}
	}

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
				case "listGame":
					System.out.println("Reponse du serveur : " + message);
					break;

				case "newGame":
					Facade.splitMessage(message.split(":")[1]);
					System.out.println("Reponse du serveur : " + message);
					break;

				case "startGame":
				case "joinGame":
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
