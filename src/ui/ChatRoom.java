package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import client.Client;

public class ChatRoom {

	private Client client;
	private String title;
	private String nickname;

	// ui
	private JFrame mainFrame;
	private JScrollPane showMessage_ScrollPane;
	private JTextField inputText;
	// private JTextArea messageArea;
	private JTextPane showMessage_Pane;

	// 메세지 색상
	private Color systemColor = Color.red; // 시스템
	private Color myColor = new Color(0, 0, 128); // 나
	private Color otherColor = new Color(0, 100, 0); // 상대방

	public ChatRoom(String title, String nickname, Client client) {
		this.client = client;
		this.nickname = nickname;
		this.title = title;

		initialize();
		new EmoticonWindow(this);
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

		// 메인 프레임
		mainFrame = new JFrame();
		mainFrame.setTitle(title);
		mainFrame.setBounds(100, 100, 400, 520);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.addWindowListener(winListener);

		// < 위 프레임 >

		// 스크롤 프레임
		showMessage_ScrollPane = new JScrollPane();
		showMessage_ScrollPane.setBounds(0, 0, 384, 440);
		mainFrame.getContentPane().add(showMessage_ScrollPane);

		// 메세지 화면
		showMessage_Pane = new JTextPane();
		showMessage_Pane
				.setPreferredSize(new Dimension(showMessage_ScrollPane.getWidth(), showMessage_ScrollPane.getHeight()));
		showMessage_Pane.setBackground(new Color(248, 248, 255));
		showMessage_Pane.setEditable(false); // 화면 수정 불가.

		showMessage_ScrollPane.setViewportView(showMessage_Pane);

		// < 아래 프레임 >

		// 메세지 입력창 프레임
		JPanel inputMessage_Panel = new JPanel();
		inputMessage_Panel.setBounds(0, 438, 384, 43);
		mainFrame.getContentPane().add(inputMessage_Panel);
		inputMessage_Panel.setLayout(null);

		// 메세지 입력 텍스트 필드
		inputText = new JTextField();
		inputText.setBounds(0, 0, 288, 43);
		inputText.addActionListener(enterText);
		inputMessage_Panel.add(inputText);
		inputText.setColumns(10);

		// 메세지 입력 버튼
		JButton inputButton = new JButton("전송");
		inputButton.setBounds(287, 0, 97, 43);
		inputButton.addActionListener(enterText);
		inputMessage_Panel.add(inputButton);

		setVisible(true);
	}

	public void InputChat(String name, String msg) {
		StyledDocument styleDoc = showMessage_Pane.getStyledDocument(); // 메세지 화면 창 정보
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE); // 스타일

		// 상대방 - 왼쪽 정렬
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

		// 자신 (나) - 오른쪽 정렬
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);

		// 시스템 - 가운데 정렬
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

		// < 스타일 >

		// 시스템 - 진하기, 색 변경
		Style style = styleDoc.addStyle("SYSTEM", def);
		StyleConstants.setForeground(style, systemColor);
		StyleConstants.setBold(style, true);

		// 나 - 진하기, 색 변경
		style = styleDoc.addStyle("ME", def);
		StyleConstants.setForeground(style, myColor);
		StyleConstants.setBold(style, true);

		// 상대방 - 진하기, 색 변경
		style = styleDoc.addStyle("OTHER", def);
		StyleConstants.setForeground(style, otherColor);
		StyleConstants.setBold(style, true);

		// 메세지 - 색 (검정)
		style = styleDoc.addStyle("MESSAGE", def);
		StyleConstants.setForeground(style, Color.BLACK);

		// 메세지 길이 측정해서 길면 나누기
		if (msg.length() > 20) {
			StringBuilder stringBuilder = new StringBuilder(msg);
			int index = 20;
			for (int i = 0; i < msg.length() % 20; i++) {
				if (index > stringBuilder.length())
					break;
				stringBuilder.insert(index, "\n");
				index = index + 20 + i;
			}
			msg = stringBuilder.toString();
		}

		try {
			// 시스템 알림 일 때
			if (name.equals("SYSTEM")) {
				// 가운데 정렬
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, center, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "] " + msg + "\n",
						styleDoc.getStyle("SYSTEM"));
			}
			// 자신
			else if (name.equals(this.nickname)) {
				if (msg.equals("공룡")) {
					InputEmoticon(name, "dinosaur");
					return;
				}
				// 우측 정렬
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, right, false);

				styleDoc.insertString(styleDoc.getLength(), " [" + name + "]\n", styleDoc.getStyle("ME"));
				styleDoc.insertString(styleDoc.getLength(), msg + "\n", styleDoc.getStyle("MESSAGE"));

			}
			// 상대방
			else {
				if (msg.equals("공룡")) {
					InputEmoticon(name, "dinosaur");
					return;
				}
				// 좌측 정렬
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, left, false);

				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("OTHER"));
				styleDoc.insertString(styleDoc.getLength(), msg + "\n", styleDoc.getStyle("MESSAGE"));

			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		showMessage_Pane.setCaretPosition(styleDoc.getLength()); // 스크롤 내리기
	}

	public void InputEmoticon(String name, String emoticonName) {
		StyledDocument styleDoc = showMessage_Pane.getStyledDocument(); // 메세지 화면 창 정보
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE); // 스타일
		ImageIcon icon = new ImageIcon("emoticons/cute-food/" + emoticonName + ".png");

		// 나 - 진하기, 색 변경
		Style style = styleDoc.addStyle("ME", def);
		StyleConstants.setForeground(style, myColor);
		StyleConstants.setBold(style, true);

		// 상대방 - 진하기, 색 변경
		style = styleDoc.addStyle("OTHER", def);
		StyleConstants.setForeground(style, otherColor);
		StyleConstants.setBold(style, true);

		// 이모티콘 - 아이콘 출력
		style = styleDoc.addStyle("EMOTICON", def);
		StyleConstants.setIcon(style, icon);

		// 상대방 - 왼쪽 정렬
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

		// 자신 (나) - 오른쪽 정렬
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);

		try {
			// 나
			if (this.nickname.equals(name)) {
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, right, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("ME"));
				styleDoc.insertString(styleDoc.getLength(), "Invisible Message\n", styleDoc.getStyle("EMOTICON"));

			}
			// 상대방
			else {
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, left, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("OTHER"));
				styleDoc.insertString(styleDoc.getLength(), "Invisible Message\n", styleDoc.getStyle("EMOTICON"));
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		showMessage_Pane.setCaretPosition(styleDoc.getLength()); // 스크롤 내리기
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

	/*
	 * public JTextArea getMessageArea() { return messageArea; }
	 */
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
