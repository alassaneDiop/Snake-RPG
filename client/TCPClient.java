// TCPClient.java
// A client program implementing TCP socket
import java.net.*; 
import java.io.*; 

class TCPClient {

	public static void main (String args[]) { // arguments supply message and hostname of destination
		Socket so = null;
		DataInputStream entree;
		PrintWriter sortie;
		String serveur; // le serveur
		int port; // le port de connexion
		String chaine; // la chaine envoyée
		int l; // la longueur reçue
		
		if (args.length == 3) {
			serveur = args[0];
			port = Integer.parseInt(args[1]);
			chaine = args[2];

			try {
				so = new Socket(serveur, port);
				sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new DataInputStream(so.getInputStream());
				sortie.println(chaine); // on écrit la chaine dans le canal de sortie
				l = entree.readInt(); // on lit l'entier qui arrive
				System.out.println("Message du serveur : la longueur de la chaine "+ l);
				so.close();
			}
			catch (UnknownHostException e){ 
				System.out.println("Sock:"+e.getMessage());}
			catch (EOFException e){
				System.out.println("EOF:"+e.getMessage()); }
			catch (IOException e){
				System.out.println("IO:"+e.getMessage());} 
		}
	}
}

/*
 * Thread pour la réception des messages du serveur
 * */
// class ClientReceive extends Thread {
//
//     Socket client;
//     BufferedReader buffer = null;
//
// 	public ClientReceive(Socket client) throws IOException {
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
//
// #<{(|
//  * Thread pour envoyer des messages au serveur
//  * |)}>#
// class ClientSend extends Thread {
//
//     Socket client;
//     BufferedReader buffer = null;
//
// 	public ClientSend(Socket client) throws IOException {
// 		try {
// 			this.client = client;
// 			// this.start();
// 		}
// 		catch (Exception ex) { System.out.println("Listen :"+ex.getMessage()); }
//     }
//  
// 	public void run() {
//         //objet pour envoyer un flux d'ecriture sur le reseau
//         PrintWriter out = null;
//         String reponse = null;
//         BufferedReader buffer = null;
//
// 		try {
// 			int serverPort = 8000;
// 			String ip = "localhost";
// 			String data = "Hello, How are you?"; 
// 			 
// 			while (true) {
// 				out = new PrintWriter(client.getOutputStream(), true);
// 				buffer = new BufferedReader(new InputStreamReader(System.in));
// 				reponse = buffer.readLine();
// 				out.println(reponse);
// 			}
//
// 		} 
// 		catch (Exception ex) {
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
