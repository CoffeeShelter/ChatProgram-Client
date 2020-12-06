package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
import java.awt.SystemColor;
import javax.swing.JTextPane;

public class ChatRoomList {

	private Client client;

	// ui
	private JFrame frame; // 채팅방 리스트 프레임
	private JTextField textField; // 검색 창
	private JPanel roomsPanel; // 채팅 방 리스트를 담을 패널

	private int oldX;
	private int oldY;
	// 중복 체크용 채팅 방 제목 벡터
	// private Vector<String> titleList = new Vector<String>();

	public ChatRoomList(Client client) {
		this.client = client;
		initialize();
		frame.setVisible(true);
		
		refreshRoom();
	}

	private void initialize() {
		// 종료 버튼
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

		// 최소화 버튼
		MouseListener miniListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				frame.setState(Frame.ICONIFIED);
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

		// 방추가 버튼
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

		// 새로고침 버튼
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

		// 마우스 위치 구하기 용도
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

		// 프레임 옮기기 용도
		MouseMotionListener mouseMotion = new MouseMotionListener() {
			int moveX, moveY;

			public void mouseDragged(MouseEvent e) {
				moveX = e.getLocationOnScreen().x - oldX; // 움직인 x거리
				moveY = e.getLocationOnScreen().y - oldY; // 움직인 y거리
				frame.setLocation(frame.getLocationOnScreen().x + moveX, frame.getLocationOnScreen().y + moveY); // 프레임
																													// 이동
				oldX = e.getLocationOnScreen().x; // 움직인 거리 초기화
				oldY = e.getLocationOnScreen().y; // 움직인 거리 초기화
			}

			public void mouseMoved(MouseEvent e) {

			}

		};
		
		// 검색 텍스트필드 이벤트
		ActionListener searchListener = new ActionListener() {
			// 검색 텍스트필드 엔터키 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				String command = "search/";
				String roomTitle;
				roomTitle = textField.getText();
				
				command += roomTitle;
				
				client.send(command);
				textField.setText("");
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
		frame.getContentPane().addMouseListener(mouseListener);
		frame.getContentPane().addMouseMotionListener(mouseMotion);

		// 종료 버튼
		JLabel exitButton_Label = new JLabel("X");
		exitButton_Label.setHorizontalAlignment(SwingConstants.CENTER);
		exitButton_Label.setForeground(Color.WHITE);
		exitButton_Label.setFont(new Font("굴림", Font.BOLD, 15));
		exitButton_Label.setBounds(992, 0, 32, 32);
		exitButton_Label.addMouseListener(exitListener);
		frame.getContentPane().add(exitButton_Label);

		// 최소화 버튼
		JLabel miniButton_Label = new JLabel("_");
		miniButton_Label.setHorizontalAlignment(SwingConstants.CENTER);
		miniButton_Label.setForeground(Color.WHITE);
		miniButton_Label.setFont(new Font("굴림", Font.BOLD, 15));
		miniButton_Label.setBounds(970, 0, 32, 32);
		miniButton_Label.addMouseListener(miniListener);
		frame.getContentPane().add(miniButton_Label);

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
		panel.setBackground(SystemColor.controlShadow);
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
		panel_1.setBounds(0, 254, 300, 329);
		left_Panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setBounds(24, 0, 70, 70);
		lblNewLabel_2_1.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/question.png")));
		panel_1.add(lblNewLabel_2_1);

		JLabel lblNewLabel_4 = new JLabel("Explanation");
		lblNewLabel_4.setBounds(106, 0, 194, 70);
		lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 24));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.controlHighlight);
		panel_2.setBounds(0, 69, 300, 260);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/add.png")));
		lblNewLabel_6.setBounds(12, 10, 32, 32);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("");
		lblNewLabel_6_1.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/refresh.png")));
		lblNewLabel_6_1.setBounds(12, 60, 32, 32);
		panel_2.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_6_2 = new JLabel("");
		lblNewLabel_6_2.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/search.png")));
		lblNewLabel_6_2.setBounds(12, 110, 32, 32);
		panel_2.add(lblNewLabel_6_2);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(SystemColor.controlHighlight);
		textPane.setFont(new Font("굴림", Font.BOLD, 20));
		textPane.setText("\uBC29 \uCD94\uAC00 \uBC84\uD2BC");
		textPane.setBounds(56, 10, 232, 32);
		panel_2.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("\uCC44\uD305\uBC29 \uB9AC\uC2A4\uD2B8 \uC0C8\uB85C\uACE0\uCE68");
		textPane_1.setFont(new Font("굴림", Font.BOLD, 20));
		textPane_1.setBackground(SystemColor.controlHighlight);
		textPane_1.setBounds(56, 60, 232, 32);
		panel_2.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("\uAC80\uC0C9 \uBC84\uD2BC");
		textPane_2.setFont(new Font("굴림", Font.BOLD, 20));
		textPane_2.setBackground(SystemColor.controlHighlight);
		textPane_2.setBounds(56, 110, 232, 32);
		panel_2.add(textPane_2);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("");
		lblNewLabel_6_2_1.setIcon(new ImageIcon(ChatRoomList.class.getResource("/ui/enter.png")));
		lblNewLabel_6_2_1.setBounds(11, 160, 32, 32);
		panel_2.add(lblNewLabel_6_2_1);
		
		JTextPane textPane_2_1 = new JTextPane();
		textPane_2_1.setText("\uBC29 \uC785\uC7A5");
		textPane_2_1.setFont(new Font("굴림", Font.BOLD, 20));
		textPane_2_1.setBackground(SystemColor.controlHighlight);
		textPane_2_1.setBounds(56, 160, 232, 32);
		panel_2.add(textPane_2_1);
		
		JTextPane textPane_2_1_1 = new JTextPane();
		textPane_2_1_1.setText("Ctrl + E \uC774\uBAA8\uD2F0\uCF58 \uCC3D");
		textPane_2_1_1.setFont(new Font("굴림", Font.BOLD, 20));
		textPane_2_1_1.setBackground(SystemColor.controlHighlight);
		textPane_2_1_1.setBounds(12, 213, 276, 37);
		panel_2.add(textPane_2_1_1);

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
		textField.addActionListener(searchListener);

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

	public JPanel getRoomPanel()
	{
		return roomsPanel;
	}
<<<<<<< HEAD
=======
	
>>>>>>> c1cebbb2d429581f491980444f5fc0c4fc690c92
}
