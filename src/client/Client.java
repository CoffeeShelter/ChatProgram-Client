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

	private BufferedReader br; // ����
	private PrintWriter pw; // �۽�

	public Client() {
		try {
			this.client = new Socket("127.0.0.1", 7777);
			if (this.client.isConnected())
				System.out.println("�����Ͽ����ϴ�.");

			// ���ſ� ����
			InputStream is = this.client.getInputStream(); // ����Ʈ�� ���� ����
			this.br = new BufferedReader(new InputStreamReader(is)); // ����Ʈ�� -> ���������� ��ȯ

			// �۽ſ� ����
			OutputStream os = this.client.getOutputStream(); // ����Ʈ������ �۽�
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)); // ����Ʈ�� -> ���������� ��ȯ
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

	// ����
	public String recv() {
		String msg = null;
		try {
			msg = br.readLine();
		} catch (IOException e) {
			//
		}
		return msg;
	}

	// ��ü ����
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

	// ����
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
