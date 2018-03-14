
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.io.*;

public class TCPServer {

	public static void main(String args[]) {
		int port;
		ServerSocket ecoute;
		Socket so;

		ContexteJoueur contexteJoueur;

		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);

				ecoute = new ServerSocket(port);
				System.out.println("Serveur en attente de connection ...");

				while (true) {
					so = ecoute.accept(); // accepter la connexion d'un client

					contexteJoueur = new ContexteJoueur();

					ThreadEtat threadEtat = new ThreadEtat(so, contexteJoueur);
					threadEtat.start();

				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Listen :" + e.getMessage());
			}
		}
	}

	// Fonction pour le traitement des commandes du joueur
	public void commande(String cmd, String response) {

		switch (cmd) {
		case "/join":
			// cj.connecter(response);
			break;

		default:
			response = "default";
			System.out.println("Commande du joueur : " + cmd);
			break;
		}

	}

}

/*
 * Thread pour le renvoi des messages aux clients
 */
public class ThreadEtat extends Thread {

	Socket so;
	ContexteJoueur cj;

	BufferedReader entree;
	DataOutputStream sortie;

	String ch, response;

	public ThreadEtat(Socket so, ContexteJoueur cj) {
		this.so = so;
		this.cj = cj;
	}

	public void run() {
		try {
			while (true) {

				// contexteJoueur = new ContexteJoueur();

				entree = new BufferedReader(new InputStreamReader(so.getInputStream())); // lire la saisie du client
				sortie = new DataOutputStream(so.getOutputStream());

				while (true) {
					ch = entree.readLine(); // on lit ce qui arrive
					// commande(ch, response);
					//
					// System.out.println("Commande du joueur : " + ch);
					// System.out.println("Reponse du serveur : " + response);
					// sortie.writeBytes(response);

					switch (ch) {

					case "/join":
						// contexteJoueur.connecter();
						response = cj.connecter();
						break;

					case "/quit":
						so.close();
						break;

					default:
						System.out.println("Commande du joueur : " + ch);
						break;
					}

					sortie.writeBytes(response + '\n');

					if (ch.equals("/quit")) {
						so.close();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Socket client;
	// // private Map<String, Socket> lesClients;
	// BufferedReader buffer = null;
	//
	// //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws
	// IOException {
	// public ServerSend(Socket client) throws IOException {
	// try {
	// this.client = client;
	// // this.start();
	// }
	// catch (Exception ex) {
	// System.out.println("Listen :"+ex.getMessage());
	// }
	// }
	// }
}

/*
 * Thread pour la réception des messages des clients
 */
// class ServerReceive extends Thread {

// Socket client;
// // private Map<String, Socket> lesClients;
// BufferedReader buffer = null;

// //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws
// IOException {
// public ServerReceive(Socket client) throws IOException {
// try {
// this.client = client;
// // this.start();
// }
// catch (Exception ex) {
// System.out.println("Listen :"+ex.getMessage());
// }
// }
//
// public void run() {
// try {
// while (true) {
// buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
// String msgClient = buffer.readLine();
// System.out.println(msgClient);
// client.close();
// }
//
// }
// catch(EOFException e) {
// System.out.println("EOF:"+e.getMessage()); }
// catch(IOException e) {
// System.out.println("IO:"+e.getMessage());}
//
// finally {
// try {
// client.close();
// }
// catch (IOException e){#<{(|close failed|)}>#}
// }
// }
// }

/*
 * Thread pour le renvoi des messages aux clients
 */
// class ServerSend extends Thread {
//
// Socket client;
// // private Map<String, Socket> lesClients;
// BufferedReader buffer = null;

// //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws
// IOException {
// public ServerSend(Socket client) throws IOException {
// try {
// this.client = client;
// // this.start();
// }
// catch (Exception ex) {
// System.out.println("Listen :"+ex.getMessage());
// }
// }
//
// public void run() {
// //objet pour envoyer un flux d'ecriture sur le reseau
// PrintWriter out = null;
// String reponse = null;
// BufferedReader buffer = null;
// try {
// out = new PrintWriter(client.getOutputStream(), true);
// //client.getOutputStream() le flux sur lequel les donnees peuvent etre
// envoyees du server au client, true pour vider le buffer
// //initialise le buffer pour les flux entrant du reseau
// buffer = new BufferedReader(new InputStreamReader(System.in));
// while (true) {
// reponse = buffer.readLine(); //on recupere ce que l'utilisateur tape
// if (reponse.equalsIgnoreCase("fin")) {
// break;
// }
// out.println(reponse); //envoyer une donnee sur le reseau
// System.out.println("réponse");
// }
// } catch (Exception ex) {
// System.out.println(ex.getMessage());
// }
// try {
// out.close();
// buffer.close();
// client.close(); //l'objet client doit etre le dernier a etre ferme
// } catch (Exception ex) {
// System.out.println(ex.getMessage());
// }
// }
//
// }

// class Connection extends Thread {
// Socket client;
// BufferedReader buffer = null;
//
// public Connection (Socket aClientSocket) {
// try {
// client = aClientSocket;
// this.start();
// }
// catch(Exception e) {
// System.out.println("Connection:"+e.getMessage());
// }
// }
//
// public void run() {
// try {
// while (true) {
// buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
// String msgClient = buffer.readLine();
// System.out.println(msgClient);
//
// }
//
// }
// catch(EOFException e) {
// System.out.println("EOF:"+e.getMessage()); }
// catch(IOException e) {
// System.out.println("IO:"+e.getMessage());}
//
// finally {
// try {
// client.close();
// }
// catch (IOException e){#<{(|close failed|)}>#}
// }
// }
// }
//
