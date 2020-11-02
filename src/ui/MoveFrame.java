package ui;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class MoveFrame extends MouseAdapter implements MouseMotionListener {
	JFrame frame;
	Container container;
	private int oldX;
	private int oldY;

	public MoveFrame(Container container) {
		this.container = container;
	}

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

	public void mouseDragged(MouseEvent e) {
		int moveX, moveY;
		moveX = e.getLocationOnScreen().x - oldX; // ������ x�Ÿ�
		moveY = e.getLocationOnScreen().y - oldY; // ������ y�Ÿ�
		frame.setLocation(frame.getLocationOnScreen().x + moveX, frame.getLocationOnScreen().y + moveY); // ������
																											// �̵�
		oldX = e.getLocationOnScreen().x; // ������ �Ÿ� �ʱ�ȭ
		oldY = e.getLocationOnScreen().y; // ������ �Ÿ� �ʱ�ȭ
	}

	public void mouseMoved(MouseEvent e) {

	}
}
