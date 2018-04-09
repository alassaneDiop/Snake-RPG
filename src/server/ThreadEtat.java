package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

/*
 * Thread pour le renvoi des messages aux clients
 */
public class ThreadEtat extends Thread {

	Socket so;
	ContexteJoueur contexte;
	TCPServer server;

	// BufferedReader entree;
	// DataOutputStream sortie;

	String ch;

	public ThreadEtat(Socket so, ContexteJoueur cj, TCPServer server) {
		this.so = so;
		this.contexte = cj;
		this.server = server;
	}

	public void run() {
		try {
			// while (true) {

			ServerReceive receiver = new ServerReceive(so, contexte, server);
			// ServerSend sender = new ServerSend(so);
			receiver.start();
			// sender.start();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
