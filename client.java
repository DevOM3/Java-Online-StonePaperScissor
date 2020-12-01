import java.awt.event.*;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.io.*;

class Client extends JFrame implements MouseListener {
	Socket socket;
	JLabel result;
	JLabel clientChoice;
	JLabel serverChoice;
	JLabel status;
	JButton stone;
	JButton paper;
	JButton scissor;

	Client() throws UnknownHostException, IOException{
		socket = new Socket("localhost", 100);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		JLabel title = new JLabel("Stone Paper Scissor", JLabel.CENTER);
		title.setFont(new Font("comicsans", Font.BOLD, 30));

		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(3, 1));
		Font choiceFont = new Font("comicsans", Font.PLAIN, 24);

		clientChoice = new JLabel("", JLabel.CENTER);
		clientChoice.setFont(choiceFont);
		serverChoice = new JLabel("", JLabel.CENTER);
		serverChoice.setFont(choiceFont);
		result = new JLabel("", JLabel.CENTER);
		result.setFont(new Font("Helvetica", Font.BOLD, 44));

		resultPanel.add(clientChoice);
		resultPanel.add(serverChoice);
		resultPanel.add(result);
		
		JPanel buttonPanel = new JPanel();
		Font buttonFont = new Font("comicsans", Font.PLAIN, 20);

		stone = new JButton("Stone");
		stone.setFont(buttonFont);
		stone.addMouseListener(this);
		paper = new JButton("Paper");
		paper.setFont(buttonFont);
		paper.addMouseListener(this);
		scissor = new JButton("Scissor");
		scissor.setFont(buttonFont);
		scissor.addMouseListener(this);

		buttonPanel.add(stone);
		buttonPanel.add(paper);
		buttonPanel.add(scissor);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(resultPanel);
		panel.add(buttonPanel);

		status = new JLabel("", JLabel.CENTER);

		container.add(title, BorderLayout.NORTH);
		container.add(panel, BorderLayout.CENTER);
		container.add(status, BorderLayout.SOUTH);
	}

	public void handleMouseClick(MouseEvent me) throws IOException {
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

		dos.writeUTF(((JButton)me.getSource()).getText());

		String response = (String) dis.readUTF();
		String[] responseArray = response.split(",");	

		clientChoice.setText("You    :    " + responseArray[0]);
		serverChoice.setText("Server :    " + responseArray[1]);
		result.setText(responseArray[2]);

		result.setForeground(responseArray[2].equals("You Won!!!") ? Color.GREEN : responseArray[2].equals("Match Drawn") ? Color.GRAY : Color.RED);
	}

	public void mouseClicked(MouseEvent me) {
		try {
			handleMouseClick(me);
		} catch (IOException e) {}
	}
	public void mouseEntered(MouseEvent me) {
		status.setText(((JButton)me.getSource()).getText());
		((JButton)me.getSource()).setBackground(Color.YELLOW);
	}
	public void mouseExited(MouseEvent me) {
		status.setText("");
		((JButton)me.getSource()).setBackground(Color.WHITE);
	}
	public void mousePressed(MouseEvent me) {
		((JButton)me.getSource()).setBackground(Color.ORANGE);
	}
	public void mouseReleased(MouseEvent me) {}

	public static void main(String []ar) throws IOException {
		Client client = new Client();
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setSize(500, 500);
		client.setVisible(true);
	}
}

