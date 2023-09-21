package miniprojectServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;


public class ServerControl extends Server {
	public ServerControl() {
		start();
	}

	public static void main(String[] args) {
		ServerControl serverControl = new ServerControl();
	}

	@Override
	public void start() {
		Socket socket = null;
		OutputStream outputStream = null;
		try {
			setServerSocket(new ServerSocket(getSocket()));
			while (true) {
				System.out.println("유저 대기중");
				socket = getServerSocket().accept();
				ReceiveThread(socket);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (getServerSocket() != null) {
				try {
					getServerSocket().close();
					System.out.println("서버종료");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("서버에러");
				}
			}
		}
	}

	@Override
	public void ReceiveThread(Socket socket) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				OutputStream outputStream = null;
				InputStream inputStream = null;
				ObjectInputStream player_In_Data = null;
				ObjectOutputStream player_Out_Data = null;
				Object sendObject = null;
				Object name = null;
				try {
					inputStream = socket.getInputStream();
					outputStream = socket.getOutputStream();
					
					player_In_Data = new ObjectInputStream(inputStream);
					System.out.println("실행중");
					player_Out_Data = new ObjectOutputStream(outputStream);
					
					getDataSendList().add(player_Out_Data);
					
					name = player_In_Data.readObject();
					System.out.println(name+"님이 들어왔습니다.");
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("데이터 가져오기 실패");
					e.printStackTrace();
				}

				try {
					
					while (true) {
						System.out.println("전송중");
						sendObject = player_In_Data.readObject();
//						sendData(player_In_Data, player_Out_Data);
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						System.out.println(name + "연결 종료");
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	@Override
	public void sendData(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		for (ObjectOutputStream send : getDataSendList()) {
			if (!send.equals(objectOutputStream)) {
				try {
					send.writeObject(objectInputStream);
					send.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
