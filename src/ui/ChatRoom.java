package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.Client;

public class ChatRoom {

	private Client client;
	private String title;
	private String nickname;

	// ui
	private JFrame mainFrame;
	private JTextField inputText;
	private JPanel Message_Panel;
	private JScrollPane showMessage_ScrollPane;

	public ChatRoom(String title, String nickname, Client client) {
		this.client = client;
		this.nickname = nickname;
		this.title = title;

		initialize();
	}

	private void initialize() {
		
		// �޼��� ���� �̺�Ʈ
		ActionListener enterText = new ActionListener() {
			// �޼��� �Է� �̺�Ʈ ó��
			public void actionPerformed(ActionEvent e) {
				String message = title;
				message = message + "/" + inputText.getText() + "/" + nickname; // ������/�޼���/�г���
				client.send(message);
				inputText.setText("");
			}
		};

		mainFrame = new JFrame();
		mainFrame.setTitle(title);
		mainFrame.setBounds(100, 100, 400, 520);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.addWindowListener(winListener);

		showMessage_ScrollPane = new JScrollPane();
		showMessage_ScrollPane.setBounds(0, 0, 384, 440);
		mainFrame.getContentPane().add(showMessage_ScrollPane);

		JPanel showMessage_Panel = new JPanel();
		showMessage_ScrollPane.setViewportView(showMessage_Panel);
		showMessage_Panel.setBackground(new Color(248, 248, 255));
		showMessage_Panel.setLayout(new BorderLayout(0, 0));
		
		Message_Panel = new JPanel();
		showMessage_Panel.add(Message_Panel, BorderLayout.NORTH);
		Message_Panel.setLayout(new GridLayout(0,1));

		
		JPanel inputMessage_Panel = new JPanel();
		inputMessage_Panel.setBounds(0, 438, 384, 43);
		mainFrame.getContentPane().add(inputMessage_Panel);
		inputMessage_Panel.setLayout(null);

		inputText = new JTextField();
		inputText.setBounds(0, 0, 288, 43);
		inputText.addActionListener(enterText);
		inputMessage_Panel.add(inputText);
		inputText.setColumns(10);

		JButton inputButton = new JButton("����");
		inputButton.setBounds(287, 0, 97, 43);
		inputButton.addActionListener(enterText);
		inputMessage_Panel.add(inputButton);

		setVisible(true);
	}
	//

	public void InputChat(String name, String msg) {	
		int count = 1;
		StringBuilder Message = new StringBuilder("<html>");
		Message.append(msg);
		int index = 36;
		for (int i = 0; i < (msg.length()) % 20; i++) {
			if(index >= msg.length()) break;
			Message.insert(index, "<br/>");
			index = index + 15 + i + 1;
			count ++;
		}
		Message.append("</html>");
		msg = Message.toString();

		JLabel nameLabel = new JLabel();
		nameLabel.setText(name);
		nameLabel.setFont(new Font("���� ��ü L", Font.PLAIN, 13));
		if (name.equals("SYSTEM"))
			nameLabel.setForeground(Color.red);
		else if (name.equals(this.nickname))
			nameLabel.setForeground(Color.blue);
		else
			nameLabel.setForeground(new Color(0, 128, 0));
		nameLabel.setBounds(5, 5, 100, 15);

		JLabel msgLabel = new JLabel();
		msgLabel.setText(msg);
		msgLabel.setFont(new Font("���� ��ü L", Font.PLAIN, 13));
		msgLabel.setBounds(80, 5, 400, 18*count);

		JPanel talkBox = new JPanel(); // ��ǳ��
		talkBox.setPreferredSize(new Dimension(200, 25*count));
		talkBox.setLayout(null);
		talkBox.setBackground(new Color(248, 248, 255));

		talkBox.add(nameLabel);

		talkBox.add(msgLabel);

		Message_Panel.add(talkBox);
		
		Message_Panel.revalidate();
		Message_Panel.repaint();	
	}

	public void OutRoom() {
		String msg = "out" + "/" + title + "/" + nickname;
		client.send(msg);
		client.getRoomList().remove(this);
	}

	// setter
	public void setVisible(boolean b) {
		mainFrame.setVisible(b);
	}

	// getter
	public String getTitle() {
		return title;
	}

	public Client getClient() {
		return client;
	}

	public String getNickname() {
		return nickname;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public JTextField getInputText() {
		return inputText;
	}

	public JPanel getMessage_Panel() {
		return Message_Panel;
	}

	public JScrollPane getShowMessage_ScrollPane() {
		return showMessage_ScrollPane;
	}
	
	// ������ �̺�Ʈ ó��
	WindowListener winListener = new WindowListener() {
		// â ���� �̺�Ʈ ó��
		public void windowOpened(WindowEvent e) {

		}

		// �����찡 ������ȭ ���� ��
		public void windowIconified(WindowEvent e) {

		}

		// �����찡 ����ȭ �� ��
		public void windowDeiconified(WindowEvent e) {

		}

		// �����찡 ��Ȱ��ȭ �� ��
		public void windowDeactivated(WindowEvent e) {

		}

		// �����찡 �������� �� ��. (X��ư ������ ��)
		public void windowClosing(WindowEvent e) {
			OutRoom();
		}

		// �����찡 ���� ��.
		public void windowClosed(WindowEvent e) {

		}

		// �����찡 Ȱ��ȭ �� ��
		public void windowActivated(WindowEvent e) {

		}
	};
}