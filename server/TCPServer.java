
import java.net.*; 
import java.util.Arrays;
import java.util.List;
import java.io.*; 

class TCPServer {


	public static void main (String args[]) {
		int port;
		ServerSocket ecoute;
		Socket so;
		BufferedReader entree;
		DataOutputStream sortie;
		String ch; // la chaine recue		

		ContexteJoueur contexteJoueur;
		
		if (args.length == 1) {
			try{
				port = Integer.parseInt(args[0]);

				ecoute = new ServerSocket(port);
				System.out.println("Serveur en attente de connection ...");
				
				while (true) {
					so = ecoute.accept(); // accepter la connexion d'un client

					contexteJoueur = new ContexteJoueur();

					entree = new BufferedReader(new InputStreamReader(so.getInputStream())); // lire la saisie du client
					sortie = new DataOutputStream(so.getOutputStream());
					
					while (true) {
						ch = entree.readLine(); // on lit ce qui arrive
	
						switch (ch) {
//
// connecter ();
// deconnecter ();
// quitter();
// choisirPseudo();
// rejoindreJeu();
// recevoirPlanJeu();
// deplacer(String deplacement);

							// case "/quit": 
							// 	response = "Joueur [Couleur] parti";
							// 	break;
                            //
							// case "/pseudo":
							// 	response = "pseudo valide";
							// 	break;
								
							case "/join":
								contexteJoueur.etat.connecter();
								// response = "100 [Bienvenue au jeu] / 204 [Identification échouée]";
								break;
								
							// case "receptionPlan":
							// 	response = "Commande reçue";
							// 	break;
							// 	
							// case "/turn H":
							// case "/turn B":
							// case "/turn G":
							// case "/turn D":
							// 	response = "100 [Direction valide] / [Direction invalide]";
							// 	break;

							default :
								break;
						}

						// Server serv = new Server();

						System.out.println("Commande du joueur : " + ch);
						// String response = serv.gestionCommande(ch);
						// sortie.writeBytes(response + '\n');
						
						if (ch.equals("/quit")) {
							so.close();
							break;
						}
					}
					
				} 
			} 
			catch(IOException e) {
				System.out.println("Listen :"+e.getMessage());
			} 
		}
	}


}









/*
 * Thread pour la réception des messages des clients
 * */
// class ServerReceive extends Thread {

    // Socket client;
    // // private Map<String, Socket> lesClients;
    // BufferedReader buffer = null;

//     //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws IOException {
// 	public ServerReceive(Socket client) throws IOException {
// 		try {
// 			this.client = client;
// 			// this.start();
// 		}
// 		catch (Exception ex) {
// 			System.out.println("Listen :"+ex.getMessage());
// 		}
//     }
//
// 	public void run() { 
// 		try {
// 			while (true) {
// 				buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
// 				String msgClient = buffer.readLine();
// 				System.out.println(msgClient);
// 				client.close();
// 			}
//
// 		} 
// 		catch(EOFException e) {
// 			System.out.println("EOF:"+e.getMessage()); } 
// 		catch(IOException e) {
// 			System.out.println("IO:"+e.getMessage());}  
//
// 		finally { 
// 			try { 
// 				client.close();
// 			}
// 			catch (IOException e){#<{(|close failed|)}>#}
// 		}
// 	}
// }	

/*
 * Thread pour le renvoi des messages aux clients
 * */
// class ServerSend extends Thread {
    //
    // Socket client;
    // // private Map<String, Socket> lesClients;
    // BufferedReader buffer = null;

//     //public Server_Receive(Socket client, Map<String, Socket> lesClients) throws IOException {
// 	public ServerSend(Socket client) throws IOException {
// 		try {
// 			this.client = client;
// 			// this.start();
// 		}
// 		catch (Exception ex) {
// 			System.out.println("Listen :"+ex.getMessage());
// 		}
//     }
//  
// 	public void run() {
//         //objet pour envoyer un flux d'ecriture sur le reseau
//         PrintWriter out = null;
//         String reponse = null;
//         BufferedReader buffer = null;
//         try {
//             out = new PrintWriter(client.getOutputStream(), true);
//             //client.getOutputStream() le flux sur lequel les donnees peuvent etre envoyees du server au client, true pour vider le buffer
//             //initialise le buffer pour les flux entrant du reseau
//             buffer = new BufferedReader(new InputStreamReader(System.in));
//             while (true) {
//                 reponse = buffer.readLine(); //on recupere ce que l'utilisateur tape
//                 if (reponse.equalsIgnoreCase("fin")) {
//                     break;
//                 }
//                 out.println(reponse); //envoyer une donnee sur le reseau
//                 System.out.println("réponse");
//             }
//         } catch (Exception ex) {
//             System.out.println(ex.getMessage());
//         }
//         try {
//             out.close();
//             buffer.close();
//             client.close(); //l'objet client doit etre le dernier a etre ferme
//         } catch (Exception ex) {
//             System.out.println(ex.getMessage());
//         }
//     }
//
// }	


// class Connection extends Thread { 
// 	Socket client;
// 	BufferedReader buffer = null;
// 	
// 	public Connection (Socket aClientSocket) { 
// 		try { 
// 			client = aClientSocket; 
// 			this.start(); 
// 			} 
// 			catch(Exception e) {
// 				System.out.println("Connection:"+e.getMessage());
// 			} 
// 	  } 
//
// 	public void run() { 
// 		try {
// 			while (true) {
// 				buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
// 				String msgClient = buffer.readLine();
// 				System.out.println(msgClient);
//
// 			}
// 			
// 		} 
// 		catch(EOFException e) {
// 			System.out.println("EOF:"+e.getMessage()); } 
// 		catch(IOException e) {
// 			System.out.println("IO:"+e.getMessage());}  
//
// 		finally { 
// 			try { 
// 				client.close();
// 			}
// 			catch (IOException e){#<{(|close failed|)}>#}
// 		}
// 	}
// }
//
