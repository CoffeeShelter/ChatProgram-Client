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
	private JFrame chatRoomList_frame; // 채팅방 리스트 프레임
	private JTextField textField; // 검색 창
	private JPanel mainPanel; // 채팅 방 리스트를 담을 패널

	// 중복 체크용 채팅 방 제목 벡터
	//private Vector<String> titleList = new Vector<String>();

	public ChatRoomList(Client client) {
		this.client = client;
		initialize();
	}

	private void initialize() {
		ActionListener addRoom = new ActionListener() {
			// 방 추가 버튼 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				new CreateRoom(client);
			}
		};

		ActionListener inRoom = new ActionListener() {
			// 방 입장 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				new EnterRoom(client, textField.getText());
			}
		};

		ActionListener refreshRoom = new ActionListener() {
			// 방 새로고침
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

		// 스크롤 프레임
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 54, 732, 322);
		chatRoomList_frame.getContentPane().add(scrollPane);

		// 스크롤 프레임위 패널
		JPanel scrollPanel = new JPanel();
		scrollPane.setViewportView(scrollPanel);
		scrollPanel.setLayout(new BorderLayout(0, 0));

		// 채팅 방 표시 패널이 들어갈 패널 -> 스크롤 프레임 위 패널에 추가
		mainPanel = new JPanel();
		mainPanel.setForeground(Color.LIGHT_GRAY);
		scrollPanel.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new GridLayout(0, 1, 0, 1));

		// 스크롤 프레임 위 프레임(검색 텍스트, 검색 버튼, 새로고침, 방 추가 포함)
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 732, 55);
		chatRoomList_frame.getContentPane().add(panel);
		panel.setLayout(null);

		// 검색 텍스트 창
		textField = new JTextField();
		textField.setBounds(12, 10, 565, 35);
		textField.addActionListener(inRoom);
		panel.add(textField);
		textField.setColumns(10);

		// 검색 버튼
		JButton searchButton = new JButton("검색");
		searchButton.setBounds(589, 10, 97, 35);
		panel.add(searchButton);

		// 방 추가 버튼
		JButton addRoomButton = new JButton("+");
		addRoomButton.setBounds(693, 34, 39, 21);
		addRoomButton.addActionListener(addRoom);
		panel.add(addRoomButton);

		// 새로고침 버튼
		JButton refreshButton = new JButton("R");
		refreshButton.setBounds(693, 0, 39, 22);
		refreshButton.addActionListener(refreshRoom);
		panel.add(refreshButton);

		setVisible(true);

		// 따로 새로고침 버튼을 누르지 않더라고 1초 주기로 새로 고침.
		Runnable runnable = new Runnable() {
			public void run() {
				refreshRoom();
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 0, 1500, TimeUnit.MILLISECONDS);
	}

	// 리스트에 방 추가
	public void addRoomPanel(String title, Color color) {
		JLabel titleLabel = new JLabel();
		titleLabel.setText(title);
		titleLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 13));
		titleLabel.setBounds(10, 10, 700, 15);

		JButton enterButton = new JButton("입장");
		enterButton.setBounds(650, 5, 60, 22);

		JPanel roomPanel = new JPanel();
		roomPanel.setPreferredSize(new Dimension(300, 35));
		roomPanel.setLayout(null);
		roomPanel.setBackground(color);

		roomPanel.add(titleLabel);
		roomPanel.add(enterButton);

		ActionListener inRoom = new ActionListener() {
			// 방 입장 이벤트 처리
			public void actionPerformed(ActionEvent e) {
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
		};

		enterButton.addActionListener(inRoom);

		mainPanel.add(roomPanel);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	// 에러 창 출력
	public void printErr(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}

	// 새로고침 서버로부터 방 정보를 다시 받아와 화면에 출력
	public void refreshRoom() {
		mainPanel.removeAll(); // 메인패널의 모든 패널 제거
		mainPanel.revalidate(); // 메인패널 값 최신화
		mainPanel.repaint(); // 새로 그림.

		client.send("refresh"); // 서버측으로 방 리스트 새로고침 요청
	}

	// 해당 프레임 화면 종료
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
