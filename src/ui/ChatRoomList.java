package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import client.Client;

public class ChatRoomList {

	private Client client;

	// ui
	private JFrame frame; // 채팅방 리스트 프레임
	private JTextField textField; // 검색 창
	private JPanel roomsPanel; // 채팅 방 리스트를 담을 패널

	// 중복 체크용 채팅 방 제목 벡터
	// private Vector<String> titleList = new Vector<String>();

	public ChatRoomList(Client client) {
		this.client = client;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		MouseListener exitListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
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

		MouseListener addRoomListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				new CreateRoom(client);
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

		MouseListener refreshListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				refreshRoom();
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

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds((int) ((screenSize.getWidth() - 1024) / 2), (int) ((screenSize.getHeight() - 600) / 2), 1024,
				600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);

		JLabel exitButton_Label = new JLabel("X");
		exitButton_Label.setHorizontalAlignment(SwingConstants.CENTER);
		exitButton_Label.setForeground(Color.WHITE);
		exitButton_Label.setFont(new Font("굴림", Font.BOLD, 15));
		exitButton_Label.setBounds(992, 0, 32, 32);
		exitButton_Label.addMouseListener(exitListener);
		frame.getContentPane().add(exitButton_Label);

		JPanel left_Panel = new JPanel();
		left_Panel.setBackground(Color.GRAY);
		left_Panel.setBounds(0, 0, 300, 600);
		frame.getContentPane().add(left_Panel);
		left_Panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/chat (1).png")));
		lblNewLabel.setBounds(32, 32, 64, 64);
		left_Panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Talk Talk");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 40));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(108, 32, 180, 64);
		left_Panel.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 175, 300, 70);
		left_Panel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/list-text.png")));
		lblNewLabel_2.setBounds(24, 0, 70, 70);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("ChatRoom List");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 24));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(106, 0, 194, 70);
		panel.add(lblNewLabel_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(0, 254, 300, 70);
		left_Panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/question.png")));
		lblNewLabel_2_1.setBounds(24, 0, 70, 70);
		panel_1.add(lblNewLabel_2_1);

		JLabel lblNewLabel_4 = new JLabel("Explanation");
		lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 24));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(106, 0, 194, 70);
		panel_1.add(lblNewLabel_4);

		JPanel right_Panel = new JPanel();
		right_Panel.setBackground(Color.DARK_GRAY);
		right_Panel.setBounds(299, 34, 725, 566);
		frame.getContentPane().add(right_Panel);
		right_Panel.setLayout(new CardLayout(0, 0));

		JPanel ChatRoomList = new JPanel();
		ChatRoomList.setBackground(Color.DARK_GRAY);
		right_Panel.add(ChatRoomList, "name_7794638925300");
		ChatRoomList.setLayout(null);

		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("한컴 윤체 L", Font.BOLD, 20));
		textField.setBackground(Color.DARK_GRAY);
		textField.setBounds(430, 30, 230, 35);
		ChatRoomList.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/search.png")));
		lblNewLabel_5.setBounds(672, 30, 32, 32);
		ChatRoomList.add(lblNewLabel_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 90, 701, 460);
		ChatRoomList.add(scrollPane);

		JPanel scrollViewPanel = new JPanel();
		scrollViewPanel.setBackground(Color.GRAY);
		scrollPane.setViewportView(scrollViewPanel);
		scrollViewPanel.setLayout(new BorderLayout(0, 0));

		roomsPanel = new JPanel();
		roomsPanel.setBackground(Color.GRAY);
		scrollViewPanel.add(roomsPanel, BorderLayout.NORTH);
		roomsPanel.setLayout(new GridLayout(0, 1, 0, 1));

		JLabel addRoomButton = new JLabel("");
		addRoomButton.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/add.png")));
		addRoomButton.setBounds(12, 48, 32, 32);
		addRoomButton.addMouseListener(addRoomListener);
		ChatRoomList.add(addRoomButton);

		JLabel refreshButton = new JLabel("");
		refreshButton.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/refresh.png")));
		refreshButton.setBounds(65, 48, 32, 32);
		refreshButton.addMouseListener(refreshListener);
		ChatRoomList.add(refreshButton);

	}

	// 리스트에 방 추가
	public void addRoomPanel(String title, Color color) {
		JLabel titleLabel = new JLabel();
		titleLabel.setText(title);
		titleLabel.setFont(new Font("굴림", Font.BOLD, 30));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setBounds(10, 10, 700, 40);

		JLabel enterButton = new JLabel();
		enterButton.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/enter.png")));
		enterButton.setBounds(640, 10, 40, 40);

		JPanel roomPanel = new JPanel();
		roomPanel.setPreferredSize(new Dimension(700, 60));
		roomPanel.setLayout(null);
		roomPanel.setBackground(color);

		roomPanel.add(titleLabel);
		roomPanel.add(enterButton);

		MouseListener inRoom = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				boolean isRoom = false;
				for (ChatRoom room : client.getRoomList()) {
					if (room.getTitle().equals(titleLabel.getText())) {
						isRoom = true;
					}
				}
				if (isRoom)
					printErr("경고", "해당 방은 입장 되어있습니다.");
				else
					new EnterRoom(client, titleLabel.getText());
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}
			// 방 입장 이벤트 처리
		};

		enterButton.addMouseListener(inRoom);

		roomsPanel.add(roomPanel);

		roomsPanel.revalidate();
		roomsPanel.repaint();
	}

	// 에러 창 출력
	public void printErr(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}

	// 새로고침 서버로부터 방 정보를 다시 받아와 화면에 출력
	public void refreshRoom() {
		roomsPanel.removeAll(); // 메인패널의 모든 패널 제거
		roomsPanel.revalidate(); // 메인패널 값 최신화
		roomsPanel.repaint(); // 새로 그림.

		client.send("refresh"); // 서버측으로 방 리스트 새로고침 요청
	}

	// 해당 프레임 화면 종료
	public void dispose() {
		frame.dispose();
	}

	// setter
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	// getter
	public JFrame getFrame() {
		return frame;
	}

	public JTextField getTextField() {
		return textField;
	}
}
