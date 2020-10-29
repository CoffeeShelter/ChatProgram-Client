package ui;

import java.awt.Color;
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

public class EnterRoom {

	private JFrame frame;
	private JTextField textField;
	Client client;
	private String title;

	public EnterRoom(Client client, String title) {
		this.client = client;
		this.title = title;
		initialize();
	}

	private void initialize() {
		ActionListener in = new ActionListener() {
			// 입장 버튼 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				client.send("in/" + title + "/" + textField.getText());
				client.getRoomList().add(new ChatRoom(title, textField.getText(), client));
				textField.setText("");
				frame.dispose();
			}
		};

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EnterRoom.class.getResource("/ui/worldwide.png")));
		frame.setTitle("\uC785\uC7A5\uD558\uAE30");
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 320, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(32, 65, 116, 21);
		textField.addActionListener(in);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\uC785\uC7A5");
		btnNewButton.setBounds(184, 64, 97, 23);
		btnNewButton.addActionListener(in);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("\uB2C9\uB124\uC784\uC744 \uC785\uB825\uD558\uC138\uC694");
		lblNewLabel.setFont(new Font("한컴 윤체 L", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(32, 23, 238, 32);
		frame.getContentPane().add(lblNewLabel);

		frame.setVisible(true);
	}
}
