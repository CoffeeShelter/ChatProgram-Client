package ui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EmoticonWindow {

	private ChatRoom chatRoom;
	private JFrame emoticonSelectWindow;
	private JPanel scrollViewPanel;

	private String[] emoticons_cute_food = new String[] { "apple", "bacon", "bananas", "bell-pepper", "broccoli",
			"carrot", "cheese", "cherry", "chicken-leg", "chili-pepper", "chocolate", "coffee-cup", "cookie", "corn",
			"croissant", "cupcake", "donut", "eggplant", "fortune-cookie", "french-fries", "fried-egg", "grapes",
			"hamburger", "hot-dog", "ice-cream", "ketchup", "meatball", "melon", "milk", "milkshake", "mushroom",
			"mustard", "onigiri", "orange", "pea", "peach", "pear", "piece-of-cake", "pineapple", "pizza", "popsicle",
			"pudding", "radish", "scallion", "soda", "strawberry", "sushi", "taco", "toast", "watermelon" };

	public EmoticonWindow(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
		initialize();
	}

	private void initialize() {
		// 키보드 입력 처리
		KeyListener keyListener = new KeyListener() {
			boolean isPressedCtrl = false;
			
			//특정 키 눌렸을 때
			public void keyTyped(KeyEvent e) {

			}
			//키 눌렀을 때
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					isPressedCtrl = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_I) {
					if (isPressedCtrl) {
						if (emoticonSelectWindow.isVisible()) {
							emoticonSelectWindow.setVisible(false);
						} else
							emoticonSelectWindow.setVisible(true);
					}
				}
			}
			//키 땠을 때
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					isPressedCtrl = false;
				}
			}

		};

		emoticonSelectWindow = new JFrame();
		emoticonSelectWindow.setTitle("이모티콘 선택 창");
		emoticonSelectWindow.setBounds(100, 100, 320, 400);
		emoticonSelectWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		emoticonSelectWindow.getContentPane().setLayout(null);
		emoticonSelectWindow.addKeyListener(keyListener);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 304, 361);
		emoticonSelectWindow.getContentPane().add(scrollPane);

		scrollViewPanel = new JPanel();
		scrollViewPanel.setLayout(new GridLayout(0, 4, 2, 2));

		for (int i = 0; i < emoticons_cute_food.length; i++) {
			ImageIcon icon = new ImageIcon("emoticons/cute-food/" + emoticons_cute_food[i] + ".png");
			addEmoticon(icon, "cute-food", emoticons_cute_food[i]);
		}
		scrollPane.add(scrollViewPanel);
		scrollPane.setViewportView(scrollViewPanel);
	}

	private void addEmoticon(ImageIcon icon, String fileName, String iconName) {
		MouseAdapter mouseAdapther = new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// emoticon/<방 제목>/<이모티콘 종류>/<보내는 사람 닉네임>
				String msg = "emoticon/" + chatRoom.getTitle() + "/" + iconName + "/" + chatRoom.getNickname();
				chatRoom.getClient().send(msg);
			}
		};

		JLabel emoticonLabel = new JLabel(icon);
		emoticonLabel.addMouseListener(mouseAdapther);
		scrollViewPanel.add(emoticonLabel);
	}

	public boolean isVisible() {
		return emoticonSelectWindow.isVisible();
	}

	// setter
	public void setVisible(boolean b) {
		emoticonSelectWindow.setVisible(b);
	}
	
	// getter
	public JFrame getMainFrame() {
		return emoticonSelectWindow;
	}
}
