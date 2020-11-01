package ui;

import java.awt.GridLayout;
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
		emoticonSelectWindow = new JFrame();
		emoticonSelectWindow.setTitle("\uC774\uBAA8\uD2F0\uCF58 \uC120\uD0DD \uCC3D");
		emoticonSelectWindow.setBounds(100, 100, 320, 400);
		emoticonSelectWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		emoticonSelectWindow.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 330, 400);
		emoticonSelectWindow.getContentPane().add(scrollPane);

		scrollViewPanel = new JPanel();
		scrollViewPanel.setLayout(new GridLayout(0, 4, 2, 2));

		for (int i = 0; i < emoticons_cute_food.length; i++) {
			ImageIcon icon = new ImageIcon("emoticons/cute-food/" + emoticons_cute_food[i] + ".png");
			addEmoticon(icon, "cute-food", emoticons_cute_food[i]);
		}
		scrollPane.add(scrollViewPanel);
		scrollPane.setViewportView(scrollViewPanel);
		emoticonSelectWindow.setVisible(true);
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
}
