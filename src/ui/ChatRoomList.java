package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.Client;
import java.awt.Toolkit;

public class ChatRoomList {

	private Client client;

	// ui
	private JFrame chatRoomList_frame; // ä�ù� ����Ʈ ������
	private JTextField textField; // �˻� â
	private JPanel mainPanel; // ä�� �� ����Ʈ�� ���� �г�

	// �ߺ� üũ�� ä�� �� ���� ����
	//private Vector<String> titleList = new Vector<String>();

	public ChatRoomList(Client client) {
		this.client = client;
		initialize();
	}

	private void initialize() {
		ActionListener addRoom = new ActionListener() {
			// �� �߰� ��ư �̺�Ʈ ó��
			public void actionPerformed(ActionEvent e) {
				new CreateRoom(client);
			}
		};

		ActionListener inRoom = new ActionListener() {
			// �� ���� �̺�Ʈ ó��
			public void actionPerformed(ActionEvent e) {
				new EnterRoom(client, textField.getText());
			}
		};

		ActionListener refreshRoom = new ActionListener() {
			// �� ���ΰ�ħ
			public void actionPerformed(ActionEvent e) {
				refreshRoom();
			}
		};

		chatRoomList_frame = new JFrame();
		chatRoomList_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ChatRoomList.class.getResource("/ui/chat.png")));
		chatRoomList_frame.setTitle("\uCC44\uD305\uBC29 \uB9AC\uC2A4\uD2B8");
		chatRoomList_frame.setBounds(100, 100, 748, 415);
		chatRoomList_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatRoomList_frame.getContentPane().setLayout(null);

		// ��ũ�� ������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 54, 732, 322);
		chatRoomList_frame.getContentPane().add(scrollPane);

		// ��ũ�� �������� �г�
		JPanel scrollPanel = new JPanel();
		scrollPane.setViewportView(scrollPanel);
		scrollPanel.setLayout(new BorderLayout(0, 0));

		// ä�� �� ǥ�� �г��� �� �г� -> ��ũ�� ������ �� �гο� �߰�
		mainPanel = new JPanel();
		mainPanel.setForeground(Color.LIGHT_GRAY);
		scrollPanel.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new GridLayout(0, 1, 0, 1));

		// ��ũ�� ������ �� ������(�˻� �ؽ�Ʈ, �˻� ��ư, ���ΰ�ħ, �� �߰� ����)
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 732, 55);
		chatRoomList_frame.getContentPane().add(panel);
		panel.setLayout(null);

		// �˻� �ؽ�Ʈ â
		textField = new JTextField();
		textField.setBounds(12, 10, 565, 35);
		textField.addActionListener(inRoom);
		panel.add(textField);
		textField.setColumns(10);

		// �˻� ��ư
		JButton searchButton = new JButton("�˻�");
		searchButton.setBounds(589, 10, 97, 35);
		panel.add(searchButton);

		// �� �߰� ��ư
		JButton addRoomButton = new JButton("+");
		addRoomButton.setBounds(693, 34, 39, 21);
		addRoomButton.addActionListener(addRoom);
		panel.add(addRoomButton);

		// ���ΰ�ħ ��ư
		JButton refreshButton = new JButton("R");
		refreshButton.setBounds(693, 0, 39, 22);
		refreshButton.addActionListener(refreshRoom);
		panel.add(refreshButton);

		setVisible(true);

		// ���� ���ΰ�ħ ��ư�� ������ �ʴ���� 1�� �ֱ�� ���� ��ħ.
		Runnable runnable = new Runnable() {
			public void run() {
				refreshRoom();
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 0, 1500, TimeUnit.MILLISECONDS);
	}

	// ����Ʈ�� �� �߰�
	public void addRoomPanel(String title, Color color) {
		JLabel titleLabel = new JLabel();
		titleLabel.setText(title);
		titleLabel.setFont(new Font("���� ��ü L", Font.PLAIN, 13));
		titleLabel.setBounds(10, 10, 700, 15);

		JButton enterButton = new JButton("����");
		enterButton.setBounds(650, 5, 60, 22);

		JPanel roomPanel = new JPanel();
		roomPanel.setPreferredSize(new Dimension(300, 35));
		roomPanel.setLayout(null);
		roomPanel.setBackground(color);

		roomPanel.add(titleLabel);
		roomPanel.add(enterButton);

		ActionListener inRoom = new ActionListener() {
			// �� ���� �̺�Ʈ ó��
			public void actionPerformed(ActionEvent e) {
				boolean isRoom = false;
				for (ChatRoom room : client.getRoomList()) {
					if (room.getTitle().equals(titleLabel.getText())) {
						isRoom = true;
					}
				}
				if (isRoom)
					printErr("���", "�ش� ���� ���� �Ǿ��ֽ��ϴ�.");
				else
					new EnterRoom(client, titleLabel.getText());
			}
		};

		enterButton.addActionListener(inRoom);

		mainPanel.add(roomPanel);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	// ���� â ���
	public void printErr(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}

	// ���ΰ�ħ �����κ��� �� ������ �ٽ� �޾ƿ� ȭ�鿡 ���
	public void refreshRoom() {
		mainPanel.removeAll(); // �����г��� ��� �г� ����
		mainPanel.revalidate(); // �����г� �� �ֽ�ȭ
		mainPanel.repaint(); // ���� �׸�.

		client.send("refresh"); // ���������� �� ����Ʈ ���ΰ�ħ ��û
	}

	// �ش� ������ ȭ�� ����
	public void dispose() {
		chatRoomList_frame.dispose();
	}

	// setter
	public void setVisible(boolean b) {
		chatRoomList_frame.setVisible(b);
	}

	// getter
	public JFrame getFrame() {
		return chatRoomList_frame;
	}

	public JTextField getTextField() {
		return textField;
	}
}
