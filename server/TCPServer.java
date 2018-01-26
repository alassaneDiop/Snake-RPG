
import java.net.*; 
import java.io.*; 

class TCPServer {

	public static void main (String args[]) {
		int port;
		ServerSocket ecoute;
		Socket so;
		BufferedReader entree;
		DataOutputStream sortie;
		String ch; // la chaine recue

		if (args.length == 1) {
			try{
				port = Integer.parseInt(args[0]);

				ecoute = new ServerSocket(port);
				System.out.println("Serveur en attente de connection ...");
				
				while (true) {
					so = ecoute.accept(); // accepter la connexion d'un client
					entree = new BufferedReader(new InputStreamReader(so.getInputStream())); // lire la saisie du client
					sortie = new DataOutputStream(so.getOutputStream());
					ch = entree.readLine(); // on lit ce qui arrive
					System.out.println("Client : message du client "+ch);
					sortie.writeInt(ch.length());
					so.close();
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
