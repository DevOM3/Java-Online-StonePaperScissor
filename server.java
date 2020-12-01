import java.net.*;
import java.io.*;
import java.util.Random;

class Server {
	public static void main(String []ar) throws IOException {
		String[] choices = {"Stone", "Paper", "Scissor"};

		ServerSocket serverSocket = new ServerSocket(100);
		Socket socket = serverSocket.accept();

		while(true){
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			String clientChoice = (String) dis.readUTF();
			String serverChoice = choices[new Random().nextInt(3)];

		// (serverChoice.equals("Stone") && clientChoice.equals("Scissor")) || (serverChoice.equals("Stone") && clientChoice.equals("Paper")) || (serverChoice.equals("Scissor") && clientChoice.equals("Paper")) ? 
			String response = serverChoice.equals(clientChoice) ? "Match Drawn" : (serverChoice.equals("Stone") && clientChoice.equals("Paper")) || (serverChoice.equals("Scissor") && clientChoice.equals("Stone")) || (serverChoice.equals("Paper") && clientChoice.equals("Scissor")) ? "You Won!!!" : "You Lost";
		
			dos.writeUTF(clientChoice + "," + serverChoice + "," + response);
		}
/*
		serverSocket.close();
		socket.close();
*/
	}
}
/*
You won:
Server	Client

Stone	Paper
Paper	Scissor
Scissor	Stone


You lost:
Server	Client

Stone	Scissor
Stone	Paper
Scissor	Paper
*/