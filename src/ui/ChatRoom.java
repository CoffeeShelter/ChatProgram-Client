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

	// �޼��� ����
	private Color systemColor = Color.red; // �ý���
	private Color myColor = new Color(0, 0, 128); // ��
	private Color otherColor = new Color(0, 100, 0); // ����

	public ChatRoom(String title, String nickname, Client client) {
		this.client = client;
		this.nickname = nickname;
		this.title = title;

		initialize();
		new EmoticonWindow(this);
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

		// ���� ������
		mainFrame = new JFrame();
		mainFrame.setTitle(title);
		mainFrame.setBounds(100, 100, 400, 520);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.addWindowListener(winListener);

		// < �� ������ >

		// ��ũ�� ������
		showMessage_ScrollPane = new JScrollPane();
		showMessage_ScrollPane.setBounds(0, 0, 384, 440);
		mainFrame.getContentPane().add(showMessage_ScrollPane);

		// �޼��� ȭ��
		showMessage_Pane = new JTextPane();
		showMessage_Pane
				.setPreferredSize(new Dimension(showMessage_ScrollPane.getWidth(), showMessage_ScrollPane.getHeight()));
		showMessage_Pane.setBackground(new Color(248, 248, 255));
		showMessage_Pane.setEditable(false); // ȭ�� ���� �Ұ�.

		showMessage_ScrollPane.setViewportView(showMessage_Pane);

		// < �Ʒ� ������ >

		// �޼��� �Է�â ������
		JPanel inputMessage_Panel = new JPanel();
		inputMessage_Panel.setBounds(0, 438, 384, 43);
		mainFrame.getContentPane().add(inputMessage_Panel);
		inputMessage_Panel.setLayout(null);

		// �޼��� �Է� �ؽ�Ʈ �ʵ�
		inputText = new JTextField();
		inputText.setBounds(0, 0, 288, 43);
		inputText.addActionListener(enterText);
		inputMessage_Panel.add(inputText);
		inputText.setColumns(10);

		// �޼��� �Է� ��ư
		JButton inputButton = new JButton("����");
		inputButton.setBounds(287, 0, 97, 43);
		inputButton.addActionListener(enterText);
		inputMessage_Panel.add(inputButton);

		setVisible(true);
	}

	public void InputChat(String name, String msg) {
		StyledDocument styleDoc = showMessage_Pane.getStyledDocument(); // �޼��� ȭ�� â ����
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE); // ��Ÿ��

		// ���� - ���� ����
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

		// �ڽ� (��) - ������ ����
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);

		// �ý��� - ��� ����
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

		// < ��Ÿ�� >

		// �ý��� - ���ϱ�, �� ����
		Style style = styleDoc.addStyle("SYSTEM", def);
		StyleConstants.setForeground(style, systemColor);
		StyleConstants.setBold(style, true);

		// �� - ���ϱ�, �� ����
		style = styleDoc.addStyle("ME", def);
		StyleConstants.setForeground(style, myColor);
		StyleConstants.setBold(style, true);

		// ���� - ���ϱ�, �� ����
		style = styleDoc.addStyle("OTHER", def);
		StyleConstants.setForeground(style, otherColor);
		StyleConstants.setBold(style, true);

		// �޼��� - �� (����)
		style = styleDoc.addStyle("MESSAGE", def);
		StyleConstants.setForeground(style, Color.BLACK);

		// �޼��� ���� �����ؼ� ��� ������
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
			// �ý��� �˸� �� ��
			if (name.equals("SYSTEM")) {
				// ��� ����
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, center, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "] " + msg + "\n",
						styleDoc.getStyle("SYSTEM"));
			}
			// �ڽ�
			else if (name.equals(this.nickname)) {
				if (msg.equals("����")) {
					InputEmoticon(name, "dinosaur");
					return;
				}
				// ���� ����
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, right, false);

				styleDoc.insertString(styleDoc.getLength(), " [" + name + "]\n", styleDoc.getStyle("ME"));
				styleDoc.insertString(styleDoc.getLength(), msg + "\n", styleDoc.getStyle("MESSAGE"));

			}
			// ����
			else {
				if (msg.equals("����")) {
					InputEmoticon(name, "dinosaur");
					return;
				}
				// ���� ����
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, left, false);

				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("OTHER"));
				styleDoc.insertString(styleDoc.getLength(), msg + "\n", styleDoc.getStyle("MESSAGE"));

			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		showMessage_Pane.setCaretPosition(styleDoc.getLength()); // ��ũ�� ������
	}

	public void InputEmoticon(String name, String emoticonName) {
		StyledDocument styleDoc = showMessage_Pane.getStyledDocument(); // �޼��� ȭ�� â ����
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE); // ��Ÿ��
		ImageIcon icon = new ImageIcon("emoticons/cute-food/" + emoticonName + ".png");

		// �� - ���ϱ�, �� ����
		Style style = styleDoc.addStyle("ME", def);
		StyleConstants.setForeground(style, myColor);
		StyleConstants.setBold(style, true);

		// ���� - ���ϱ�, �� ����
		style = styleDoc.addStyle("OTHER", def);
		StyleConstants.setForeground(style, otherColor);
		StyleConstants.setBold(style, true);

		// �̸�Ƽ�� - ������ ���
		style = styleDoc.addStyle("EMOTICON", def);
		StyleConstants.setIcon(style, icon);

		// ���� - ���� ����
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);

		// �ڽ� (��) - ������ ����
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);

		try {
			// ��
			if (this.nickname.equals(name)) {
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, right, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("ME"));
				styleDoc.insertString(styleDoc.getLength(), "Invisible Message\n", styleDoc.getStyle("EMOTICON"));

			}
			// ����
			else {
				styleDoc.setParagraphAttributes(styleDoc.getLength(), 0, left, false);
				styleDoc.insertString(styleDoc.getLength(), "[" + name + "]\n", styleDoc.getStyle("OTHER"));
				styleDoc.insertString(styleDoc.getLength(), "Invisible Message\n", styleDoc.getStyle("EMOTICON"));
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		showMessage_Pane.setCaretPosition(styleDoc.getLength()); // ��ũ�� ������
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
