package ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;

public class ChatRoom {
	
	private Client client;

	private String title; // �ش� �� ����
	private String nickName = "unknown"; // �ش� �濡���� �г���
	
	//ui
	private JFrame chatRoom;
	private JButton sendButton;
	private JTextArea textWindow;
	private JTextField sendText;
	private JScrollPane scrollPane;
	
	public ChatRoom(String title, Client client) {
		this.client = client;
		this.title = title;
		initialize();
		textWindow.append("[ "+title+" ] ä�� �� �Դϴ�.\n");
		chatRoom.setVisible(true);
	}

	public ChatRoom(String title, String nickName, Client client) {
		this.client = client;
		this.title = title;
		this.nickName = nickName;
		initialize();

		chatRoom.setVisible(true);
	}

	public void outRoom() {
		String msg = "out" + "/" + title + "/" + nickName;
		client.send(msg);
		client.getRoomList().remove(this);
	}

	private void initialize() {
		ActionListener enterText = new ActionListener() {
			// �޼��� �Է� �̺�Ʈ ó��
			public void actionPerformed(ActionEvent e) {
				String message = title;
				message = message + "/" + sendText.getText() + "/" + nickName; // ������/�޼���/�г���
				client.send(message);
				sendText.setText("");
			}
		};

		chatRoom = new JFrame();
		chatRoom.setTitle(title);
		chatRoom.setBounds(100, 100, 453, 504);
		chatRoom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		chatRoom.addWindowListener(winListener);
		chatRoom.setIconImage(Toolkit.getDefaultToolkit().getImage(CreateRoom.class.getResource("/ui/chat.png")));
		chatRoom.getContentPane().setLayout(null);

		sendButton = new JButton("\uC804\uC1A1");
		sendButton.setBounds(328, 426, 109, 39);
		sendButton.addActionListener(enterText);
		chatRoom.getContentPane().add(sendButton);

		sendText = new JTextField();
		sendText.setBounds(0, 426, 329, 39);
		sendText.addActionListener(enterText);
		chatRoom.getContentPane().add(sendText);
		sendText.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 437, 428);
		chatRoom.getContentPane().add(scrollPane);

		textWindow = new JTextArea();
		scrollPane.setViewportView(textWindow);
		textWindow.setBackground(new Color(248, 248, 255));

	}

	public void appendText(String msg) {
		textWindow.append(msg + "\n");
	}

	// getter
	public JTextField getSendText() {
		return sendText;
	}

	public JTextArea getTextWindow() {
		return textWindow;
	}

	public JButton getSendButton() {
		return sendButton;
	}

	public Client getClient() {
		return client;
	}

	public String getTitle() {
		return title;
	}

	public String getNickname() {
		return nickName;
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
			outRoom();
		}

		// �����찡 ���� ��.
		public void windowClosed(WindowEvent e) {

		}

		// �����찡 Ȱ��ȭ �� ��
		public void windowActivated(WindowEvent e) {

		}
	};
}
