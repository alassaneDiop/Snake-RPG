import java.net.*; 
import java.io.*; 

public class TCPServer {

	public static void main (String args[]) {
		try { 
			int serverPort = 8000; 
			ServerSocket listenSocket = new ServerSocket(serverPort); 
	  
			System.out.println("Serveur en attente de connection ...");
		
			while(true) { 
				Socket clientSocket = listenSocket.accept(); 
				Connection c = new Connection(clientSocket); 
			} 
		} 
		catch(IOException e) {
			System.out.println("Listen :"+e.getMessage());
		} 
  }
}


class Connection extends Thread { 
	Socket client;
	BufferedReader buffer = null;
	
	public Connection (Socket aClientSocket) { 
		try { 
			client = aClientSocket; 
			this.start(); 
			} 
			catch(Exception e) {
				System.out.println("Connection:"+e.getMessage());
			} 
	  } 

	public void run() { 
		try {
			while (true) {
				buffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String msgClient = buffer.readLine();
				System.out.println(msgClient);

			}
			
		} 
		catch(EOFException e) {
			System.out.println("EOF:"+e.getMessage()); } 
		catch(IOException e) {
			System.out.println("IO:"+e.getMessage());}  

		finally { 
			try { 
				client.close();
			}
			catch (IOException e){/*close failed*/}
		}
	}
}

