package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;

public class ChatRoom {

	private Client client;
	private String title;
	private String nickname;

	// ui
	private JFrame mainFrame;
	private JTextField inputText;
	private JTextArea messageArea;
	private JScrollPane showMessage_ScrollPane;

	public ChatRoom(String title, String nickname, Client client) {
		this.client = client;
		this.nickname = nickname;
		this.title = title;

		initialize();
	}

	private void initialize() {
		
		// 메세지 전송 이벤트
		ActionListener enterText = new ActionListener() {
			// 메세지 입력 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				String message = title;
				message = message + "/" + inputText.getText() + "/" + nickname; // 방제목/메세지/닉네임
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
		
		messageArea = new JTextArea();
		showMessage_Panel.add(messageArea, BorderLayout.NORTH);
		messageArea.setLayout(new GridLayout(0,1));

		
		JPanel inputMessage_Panel = new JPanel();
		inputMessage_Panel.setBounds(0, 438, 384, 43);
		mainFrame.getContentPane().add(inputMessage_Panel);
		inputMessage_Panel.setLayout(null);

		inputText = new JTextField();
		inputText.setBounds(0, 0, 288, 43);
		inputText.addActionListener(enterText);
		inputMessage_Panel.add(inputText);
		inputText.setColumns(10);

		JButton inputButton = new JButton("전송");
		inputButton.setBounds(287, 0, 97, 43);
		inputButton.addActionListener(enterText);
		inputMessage_Panel.add(inputButton);

		setVisible(true);
	}
	//

	public void InputChat(String name, String msg) {	
		

		messageArea.append("["+name+"] "+msg+"\n");
		
		messageArea.revalidate();
		messageArea.repaint();	
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

	public JTextArea getMessageArea() {
		return messageArea;
	}

	public JScrollPane getShowMessage_ScrollPane() {
		return showMessage_ScrollPane;
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
			OutRoom();
		}

		// 윈도우가 닫힌 후.
		public void windowClosed(WindowEvent e) {

		}

		// 윈도우가 활성화 될 때
		public void windowActivated(WindowEvent e) {

		}
	};
}
