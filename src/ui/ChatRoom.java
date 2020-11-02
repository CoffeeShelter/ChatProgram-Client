package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
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

	private JLabel iconLabel;
	private JLabel miniLabel;
	private JLabel titleLabel;
	private JLabel sendButton;

	// �޼��� ����
	private Color systemColor = Color.red; // �ý���
	private Color myColor = new Color(0, 0, 128); // ��
	private Color otherColor = new Color(0, 100, 0); // ����

	// ȭ�� ��ġ �ٲٱ� ���� ����
	private int oldX; // �̵� �� x ��ǥ
	private int oldY; // �̵� �� y ��ǥ

	// �̸�Ƽ�� ���� â
	// Ctrl + I Ű�� ��, ����
	EmoticonWindow emoticonWindow = new EmoticonWindow(this);

	public ChatRoom(String title, String nickname, Client client) {
		this.client = client;
		this.nickname = nickname;
		this.title = title;

		initialize();
		emoticonWindow.setVisible(false);

	}

	private void initialize() {
		// ���� ��ư
		MouseListener exitListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				mainFrame.dispose();
				OutRoom();
				client.getChatRoomList().refreshRoom();
				emoticonWindow.getMainFrame().dispose();
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

		};

		// �ּ�ȭ ��ư
		MouseListener miniListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				mainFrame.setState(Frame.ICONIFIED);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

		};

		// ���콺 ��ġ ���ϱ� �뵵
		MouseListener mouseListener = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				oldX = e.getLocationOnScreen().x;
				oldY = e.getLocationOnScreen().y;
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		};

		// ������ �ű�� �뵵
		MouseMotionListener mouseMotion = new MouseMotionListener() {
			int moveX, moveY;

			public void mouseDragged(MouseEvent e) {
				moveX = e.getLocationOnScreen().x - oldX; // ������ x�Ÿ�
				moveY = e.getLocationOnScreen().y - oldY; // ������ y�Ÿ�
				mainFrame.setLocation(mainFrame.getLocationOnScreen().x + moveX,
						mainFrame.getLocationOnScreen().y + moveY); // ������
				// �̵�
				oldX = e.getLocationOnScreen().x; // ������ �Ÿ� �ʱ�ȭ
				oldY = e.getLocationOnScreen().y; // ������ �Ÿ� �ʱ�ȭ
			}

			public void mouseMoved(MouseEvent e) {

			}

		};

		// �޼��� ���� ��ư
		MouseListener enterTextButton = new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				String message = title;
				message = message + "/" + inputText.getText() + "/" + nickname; // ������/�޼���/�г���
				client.send(message);
				inputText.setText("");

			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		};

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

		// Ű���� �Է� ó��
		KeyListener keyListener = new KeyListener() {
			boolean isPressedCtrl = false;

			public void keyTyped(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					isPressedCtrl = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_I) {
					if (isPressedCtrl) {
						if (emoticonWindow.isVisible()) {
							emoticonWindow.setVisible(false);
						} else
							emoticonWindow.setVisible(true);
					}
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					isPressedCtrl = false;
				}
			}

		};

		// ���� ������
		mainFrame = new JFrame();
		mainFrame.getContentPane().setBackground(new Color(173, 216, 230));
		mainFrame.setTitle(title);
		mainFrame.setBounds(100, 100, 350, 520);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.getContentPane().addMouseListener(mouseListener);
		mainFrame.getContentPane().addMouseMotionListener(mouseMotion);
		mainFrame.setUndecorated(true);

		// < �� ������ >

		// ��ũ�� ������
		showMessage_ScrollPane = new JScrollPane();
		showMessage_ScrollPane.setBounds(0, 52, 350, 428);
		mainFrame.getContentPane().add(showMessage_ScrollPane);

		// �޼��� ȭ��
		showMessage_Pane = new JTextPane();
		showMessage_Pane
				.setPreferredSize(new Dimension(showMessage_ScrollPane.getWidth(), showMessage_ScrollPane.getHeight()));
		showMessage_Pane.setBackground(new Color(173, 216, 230));
		showMessage_Pane.setEditable(false); // ȭ�� ���� �Ұ�.

		showMessage_ScrollPane.setColumnHeaderView(showMessage_Pane);

		// < �Ʒ� ������ >

		// �޼��� �Է�â ������
		JPanel inputMessage_Panel = new JPanel();
		inputMessage_Panel.setBackground(new Color(255, 250, 205));
		inputMessage_Panel.setBounds(0, 478, 350, 43);
		mainFrame.getContentPane().add(inputMessage_Panel);
		inputMessage_Panel.setLayout(null);

		// �޼��� �Է� �ؽ�Ʈ �ʵ�
		inputText = new JTextField();
		inputText.setBounds(0, 0, 288, 43);
		inputMessage_Panel.add(inputText);
		inputText.addActionListener(enterText);
		inputText.addKeyListener(keyListener);
		inputText.setColumns(10);

		sendButton = new JLabel("\uC804\uC1A1");
		sendButton.setHorizontalAlignment(SwingConstants.CENTER);
		sendButton.setBounds(287, 0, 63, 43);
		sendButton.addMouseListener(enterTextButton);
		inputMessage_Panel.add(sendButton);

		JPanel titlePane = new JPanel();
		titlePane.setBackground(new Color(163, 206, 220));
		titlePane.setBounds(0, 0, 350, 53);
		mainFrame.getContentPane().add(titlePane);
		titlePane.setLayout(null);

		iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(ChatRoom.class.getResource("/ui/talking.png")));
		iconLabel.setBounds(12, 10, 32, 32);
		titlePane.add(iconLabel);

		JLabel exitLabel = new JLabel("X");
		exitLabel.setFont(new Font("����", Font.BOLD, 15));
		exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exitLabel.setBounds(323, 10, 15, 15);
		exitLabel.addMouseListener(exitListener);
		titlePane.add(exitLabel);

		miniLabel = new JLabel("_");
		miniLabel.setFont(new Font("����", Font.BOLD, 15));
		miniLabel.setHorizontalAlignment(SwingConstants.CENTER);
		miniLabel.setBounds(303, 10, 15, 15);
		miniLabel.addMouseListener(miniListener);
		titlePane.add(miniLabel);

		titleLabel = new JLabel(title);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("�޸�����ü", Font.BOLD, 20));
		titleLabel.setBounds(60, 10, 231, 33);
		titlePane.add(titleLabel);

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

}
