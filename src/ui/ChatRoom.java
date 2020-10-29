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

	private String title; // 해당 방 제목
	private String nickName = "unknown"; // 해당 방에서의 닉네임
	
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
		textWindow.append("[ "+title+" ] 채팅 방 입니다.\n");
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
			// 메세지 입력 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				String message = title;
				message = message + "/" + sendText.getText() + "/" + nickName; // 방제목/메세지/닉네임
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

	// 윈도우 이벤트 처리
	WindowListener winListener = new WindowListener() {
		// 창 종료 이벤트 처리
		public void windowOpened(WindowEvent e) {

		}

		// 윈도우가 아이콘화 됐을 때
		public void windowIconified(WindowEvent e) {

		}

		// 윈도우가 정상화 될 때
		public void windowDeiconified(WindowEvent e) {

		}

		// 윈도우가 비활성화 될 떄
		public void windowDeactivated(WindowEvent e) {

		}

		// 윈도우가 닫히려고 할 때. (X버튼 눌렀을 때)
		public void windowClosing(WindowEvent e) {
			outRoom();
		}

		// 윈도우가 닫힌 후.
		public void windowClosed(WindowEvent e) {

		}

		// 윈도우가 활성화 될 때
		public void windowActivated(WindowEvent e) {

		}
	};
}
