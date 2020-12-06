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

public class EnterRoom {

	private JFrame frame;
	private JTextField textField;
	Client client;
	private String title;

	private int oldX;
	private int oldY;

	public EnterRoom(Client client, String title) {
		this.client = client;
		this.title = title;
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
				frame.setLocation(frame.getLocationOnScreen().x + moveX, frame.getLocationOnScreen().y + moveY); // 프레임
				// 이동
				oldX = e.getLocationOnScreen().x; // 움직인 거리 초기화
				oldY = e.getLocationOnScreen().y; // 움직인 거리 초기화
			}

			public void mouseMoved(MouseEvent e) {

			}

		};

		// 종료 버튼
		MouseListener enterListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (textField.getText().length() > 10) {
					printErr("닉네임 길이 초과", "닉네임은 10자 이내로 정해주세요.");
					textField.setText("");
				} else if (textField.getText().equals("")) {
					printErr("닉네임 오류", "닉네임을 입력하십시오.");
					textField.setText("");
				}
				// 정상 입장
				else {
					client.getRoomList().add(new ChatRoom(title, textField.getText(), client));
					client.send("in/" + title + "/" + textField.getText());
					textField.setText("");
					frame.dispose();
				}
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

		// 종료 버튼
		MouseListener exitListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				frame.dispose();
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

		ActionListener in = new ActionListener() {
			// 입장 버튼 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				// 닉네임 10자 이상 일 경우
				if (textField.getText().length() > 10) {
					printErr("닉네임 길이 초과", "닉네임은 10자 이내로 정해주세요.");
					textField.setText("");
				} else if (textField.getText().equals("")) {
					printErr("닉네임 오류", "닉네임을 입력하십시오.");
					textField.setText("");
				}
				// 정상 입장
				else {
					client.getRoomList().add(new ChatRoom(title, textField.getText(), client));
					client.send("in/" + title + "/" + textField.getText());
					textField.setText("");
					frame.dispose();
				}
			}
		};

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EnterRoom.class.getResource("/ui/worldwide.png")));
		frame.setTitle("\uC785\uC7A5\uD558\uAE30");
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(new Color(173, 216, 230));
		frame.setBounds(100, 100, 320, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().addMouseListener(mouseListener);
		frame.getContentPane().addMouseMotionListener(mouseMotion);

		textField = new JTextField();
		textField.setBounds(32, 84, 194, 32);
		textField.addActionListener(in);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("\uB2C9\uB124\uC784\uC744 \uC785\uB825\uD558\uC138\uC694");
		lblNewLabel.setFont(new Font("휴먼편지체", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(32, 23, 251, 51);
		frame.getContentPane().add(lblNewLabel);

		JLabel enterButtonLabel = new JLabel("");
		enterButtonLabel.setIcon(new ImageIcon(EnterRoom.class.getResource("/ui/go.png")));
		enterButtonLabel.setBounds(251, 84, 32, 32);
		enterButtonLabel.addMouseListener(enterListener);
		frame.getContentPane().add(enterButtonLabel);

		JLabel exitLabel = new JLabel("X");
		exitLabel.setFont(new Font("굴림", Font.BOLD, 15));
		exitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exitLabel.setBounds(295, 10, 16, 16);
		exitLabel.addMouseListener(exitListener);
		frame.getContentPane().add(exitLabel);

		frame.setVisible(true);
	}

	// 에러 창 출력
	public void printErr(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
}
