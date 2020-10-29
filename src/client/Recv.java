package client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import ui.ChatRoom;

public class Recv implements Runnable {

	private Client client;

	public Recv(Client client) {
		this.client = client;
	}

	public void run() {
		String message = null;

		while (true) {
			message = recv();
			//System.out.println(message);
			if (message == null)
				break;
			String[] code = message.split("/");
			if (message != null) {
				if (code[0].equals("_refresh_")) {
					Color color = null;
					for (int i = 1; i < code.length; i++) {
						if (i % 2 == 0)
							color = Color.DARK_GRAY;
						else
							color = Color.GRAY;
						client.getChatRoomList().addRoomPanel(code[i], color);

					}
				} else {
					Vector<ChatRoom> vec = client.getRoomList();
					for (ChatRoom room : vec) {
						if (room.getTitle().equals(code[0])) {
							room.getTextWindow().append(code[1] + "\n");
							room.getTextWindow().setCaretPosition(room.getTextWindow().getDocument().getLength()); // 맨아래로
																													// 스크롤
						}
					}
				}
			}
		}
		System.out.println("리시브 쓰레드 종료");
	}

	// 수신
	public String recv() {
		String msg = null;

		InputStream is;
		BufferedReader br;

		try {
			is = client.getClient().getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			msg = br.readLine();

			// br.close();
		} catch (Exception e) {
			//
		}

		return msg;
	}

	// 객체 수신
	public Object recvObject() {
		Object o = null;

		try {
			InputStream is = client.getClient().getInputStream();
			ObjectInputStream Objis = new ObjectInputStream(is);

			o = Objis.readObject();

			// Objis.close();
		} catch (Exception e) {
			//
		}

		return o;
	}

	// getter
	public Socket getClient() {
		return client.getClient();
	}
}
