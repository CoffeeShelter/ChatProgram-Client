package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import ui.ChatRoom;
import ui.ChatRoomList;

public class Client {

	private boolean exit = false;
	private Socket client;
	private ChatRoom chatRoom;
	private Vector<ChatRoom> roomVector = new Vector<ChatRoom>();
	
	//ui
	private ChatRoomList roomList;

	private BufferedReader br; // 수신
	private PrintWriter pw; // 송신

	public Client() {
		try {
			this.client = new Socket("127.0.0.1", 7777);
			if (this.client.isConnected())
				System.out.println("접속하였습니다.");

			// 수신용 변수
			InputStream is = this.client.getInputStream(); // 바이트형 으로 수신
			this.br = new BufferedReader(new InputStreamReader(is)); // 바이트형 -> 문자형으로 변환

			// 송신용 변수
			OutputStream os = this.client.getOutputStream(); // 바이트형으로 송신
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)); // 바이트형 -> 문자형으로 변환
			this.pw = new PrintWriter(bw, true);

		} catch (Exception e) {
			//
		}
	}

	public void runClient() {
		Thread recvThread = new Thread(new Recv(this));
		roomList = new ChatRoomList(this);
		
		recvThread.start();
		
		while (!exit);

		roomList.dispose();
	}

	// 수신
	public String recv() {
		String msg = null;
		try {
			msg = br.readLine();
		} catch (IOException e) {
			//
		}
		return msg;
	}

	// 객체 수신
	public Object recvObject() {
		Object o = null;

		try {
			InputStream is = client.getInputStream();
			ObjectInputStream Objis = new ObjectInputStream(is);

			o = Objis.readObject();

			// Objis.close();
		} catch (Exception e) {
			//
		}

		return o;
	}

	// 전송
	public void send(String msg) {
		pw.println(msg);
	}

	public void addRoom(String title) {
		String msg = "add/room/" + title;
		send(msg);
	}

	public void closeClient() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getter
	public Socket getClient() {
		return client;
	}

	public Vector<ChatRoom> getRoomList() {
		return roomVector;
	}

	public ChatRoomList getChatRoomList() {
		return roomList;
	}

	public boolean isExit() {
		return exit;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public BufferedReader getBufferedReader() {
		return br;
	}

	public PrintWriter getPrintWriter() {
		return pw;
	}

	
	//setter
	public void setExit(boolean b) {
		this.exit = b;
	}

}
