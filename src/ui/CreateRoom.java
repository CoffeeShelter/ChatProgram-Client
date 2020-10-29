package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Client;
import java.awt.Toolkit;

public class CreateRoom {

	private Client client;

	private JFrame addRoomFrame;
	private JTextField titleText;
	private JTextField nicknameText;

	public CreateRoom(Client client) {
		this.client = client;
		initialize();
	}

	private void initialize() {
		ActionListener add = new ActionListener() {
			// 추가 버튼 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				String title = titleText.getText();
				String nickname= nicknameText.getText();
				client.getRoomList().add(new ChatRoom(title,nickname,client));
				client.send("add/room/"+title);
				
				addRoomFrame.dispose();
			}
		};

		addRoomFrame = new JFrame();
		addRoomFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(CreateRoom.class.getResource("/ui/id-card.png")));
		addRoomFrame.setTitle("방 생성");
		addRoomFrame.setBounds(100, 100, 320, 150);
		addRoomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//현재 창만 닫기
		addRoomFrame.getContentPane().setLayout(null);

		titleText = new JTextField();
		titleText.setBounds(110, 20, 164, 21);
		addRoomFrame.getContentPane().add(titleText);
		titleText.setColumns(10);

		JButton addButton = new JButton("생성");
		addButton.setBounds(177, 78, 97, 23);
		addButton.addActionListener(add);
		addRoomFrame.getContentPane().add(addButton);

		nicknameText = new JTextField();
		nicknameText.setBounds(110, 51, 164, 21);
		addRoomFrame.getContentPane().add(nicknameText);
		nicknameText.setColumns(10);
		nicknameText.addActionListener(add);

		JLabel titleLabel = new JLabel("방 제목");
		titleLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 15));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(28, 23, 57, 15);
		addRoomFrame.getContentPane().add(titleLabel);

		JLabel nicknameLabel = new JLabel("닉네임");
		nicknameLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 15));
		nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nicknameLabel.setBounds(28, 54, 57, 15);
		addRoomFrame.getContentPane().add(nicknameLabel);

		setVisible(true);
	}

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
