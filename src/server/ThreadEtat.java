/** 
 * 
 */
package server;

import java.net.Socket;

/**
 * @author alassane
 *
 */

/*
 * Thread pour la réception des messages
 */
public class ThreadEtat extends Thread {

	Socket so;
	ContexteJoueur contexte;
	TCPServer server;

	String ch;

	public ThreadEtat(Socket so, ContexteJoueur cj, TCPServer server) {
		this.so = so;
		this.contexte = cj;
		this.server = server;
	}

	public void run() {
		try {

			ServerReceive receiver = new ServerReceive(so, contexte, server);
			receiver.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
