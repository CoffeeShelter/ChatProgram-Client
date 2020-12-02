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
			// System.out.println(message);
			// message가 null일 경우 서버가 닫힌거로 간주.
			if (message == null)
				break;
			
			String[] code = message.split("/");
			
			//새로고침
			if (code[0].equals("_refresh_")) {
				Color color = null;
				for (int i = 1; i < code.length; i++) {
					if (i % 2 == 0)
						color = new Color(192, 192, 192);
					else
						color =new Color(105, 105, 105);
					client.getChatRoomList().addRoomPanel(code[i], color);

				}
			} 
			// 이모티콘 출력
			// emoticon/<방제목>/<이모티콘 종류>/<보낸 사람 닉네임>
			else if(code[0].equals("emoticon")) {
				
				Vector<ChatRoom> vec = client.getRoomList();
				for (ChatRoom room : vec) {
					if (room.getTitle().equals(code[1])) {
						room.InputEmoticon(code[3], code[2]);
					}
				}
			}
			// 검색한 채팅방 리스트 출력
			// search/<방제목>/.../<방제목>
			else if(code[0].equals("search"))
			{
				Color color = null;
				
				client.getChatRoomList().getRoomPanel().removeAll();
				client.getChatRoomList().getRoomPanel().revalidate();				
				client.getChatRoomList().getRoomPanel().repaint();

				for (int i = 1; i < code.length; i++) {
					if (i % 2 == 0)
						color = new Color(192, 192, 192);
					else
						color =new Color(105, 105, 105);
					client.getChatRoomList().addRoomPanel(code[i], color);

				}
				System.out.println(message);
			}
			else {
				// <방제목>/<메세지>/<닉네임>
				Vector<ChatRoom> vec = client.getRoomList();
				for (ChatRoom room : vec) {
					if (room.getTitle().equals(code[0])) {
						room.InputChat(code[2], code[1]);
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
