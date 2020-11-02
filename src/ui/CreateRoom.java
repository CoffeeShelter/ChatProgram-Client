package ui;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;

public class CreateRoom {

	private Client client;

	private JFrame addRoomFrame;
	private JTextField titleText;
	private JTextField nicknameText;

	private int oldX;
	private int oldY;

	public CreateRoom(Client client) {
		this.client = client;
		initialize();
	}

	private void initialize() {
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
				addRoomFrame.setLocation(addRoomFrame.getLocationOnScreen().x + moveX,
						addRoomFrame.getLocationOnScreen().y + moveY); // 프레임
				// 이동
				oldX = e.getLocationOnScreen().x; // 움직인 거리 초기화
				oldY = e.getLocationOnScreen().y; // 움직인 거리 초기화
			}

			public void mouseMoved(MouseEvent e) {

			}

		};

		// 종료 버튼
		MouseListener exitListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				addRoomFrame.dispose();
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

		// 방 추가버튼
		MouseListener addButton = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				String title = titleText.getText();
				String nickname = nicknameText.getText();

				if (nickname.equals("") || title.equals("")) {
					printErr("오류", "닉네임 또는 제목을 입력하지 않으셨습니다.");
					titleText.setText("");
					nicknameText.setText("");
				} else {
					client.getRoomList().add(new ChatRoom(title, nickname, client));
					client.send("add/room/" + title);
					addRoomFrame.dispose();
				}
				client.getChatRoomList().refreshRoom();
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

		ActionListener add = new ActionListener() {
			// 추가 버튼 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				String title = titleText.getText();
				String nickname = nicknameText.getText();

				if (nickname.equals("") || title.equals("")) {
					printErr("오류", "닉네임 또는 제목을 입력하지 않으셨습니다.");
					titleText.setText("");
					nicknameText.setText("");
				} else {
					client.getRoomList().add(new ChatRoom(title, nickname, client));
					client.send("add/room/" + title);
					addRoomFrame.dispose();
				}
				client.getChatRoomList().refreshRoom();
			}
		};

		addRoomFrame = new JFrame();
		addRoomFrame.getContentPane().setBackground(new Color(173, 216, 230));
		addRoomFrame.setUndecorated(true);
		addRoomFrame
				.setIconImage(Toolkit.getDefaultToolkit().getImage(CreateRoom.class.getResource("/ui/id-card.png")));
		addRoomFrame.setTitle("방 생성");
		addRoomFrame.setBounds(100, 100, 320, 150);
		addRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 현재 창만 닫기
		addRoomFrame.getContentPane().setLayout(null);
		addRoomFrame.getContentPane().addMouseListener(mouseListener);
		addRoomFrame.getContentPane().addMouseMotionListener(mouseMotion);

		titleText = new JTextField();
		titleText.setBounds(110, 40, 164, 21);
		addRoomFrame.getContentPane().add(titleText);
		titleText.setColumns(10);

		nicknameText = new JTextField();
		nicknameText.setBounds(110, 80, 164, 21);
		addRoomFrame.getContentPane().add(nicknameText);
		nicknameText.setColumns(10);
		nicknameText.addActionListener(add);

		JLabel titleLabel = new JLabel("방 제목");
		titleLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 15));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(28, 43, 57, 15);
		addRoomFrame.getContentPane().add(titleLabel);

		JLabel nicknameLabel = new JLabel("닉네임");
		nicknameLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 15));
		nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nicknameLabel.setBounds(28, 83, 57, 15);
		addRoomFrame.getContentPane().add(nicknameLabel);

		JLabel lblNewLabel = new JLabel("X");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(292, 10, 16, 16);
		lblNewLabel.addMouseListener(exitListener);
		addRoomFrame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(CreateRoom.class.getResource("/ui/go.png")));
		lblNewLabel_1.setBounds(276, 102, 32, 32);
		lblNewLabel_1.addMouseListener(addButton);

		addRoomFrame.getContentPane().add(lblNewLabel_1);

		setVisible(true);
	}

	// 에러 창 출력
	public void printErr(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}

	// setter
	public void setVisible(boolean b) {
		addRoomFrame.setVisible(b);
	}

	// getter
	public JFrame getFrame() {
		return addRoomFrame;
	}

	public JTextField getTextField() {
		return titleText;
	}

	public JTextField getTextField_1() {
		return nicknameText;
	}
}
