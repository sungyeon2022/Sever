package miniprojectServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import data.DataClass;



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

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				OutputStream outputStream = null;
				InputStream inputStream = null;
				ObjectInputStream player_In_Data = null;
				ObjectOutputStream player_Out_Data = null;
				DataClass reciveDataClass = null;
				Object name = null;
				boolean isMulti = false;
				try {
					outputStream = socket.getOutputStream();
					inputStream = socket.getInputStream();
					player_Out_Data = new ObjectOutputStream(outputStream);
					player_In_Data = new ObjectInputStream(inputStream);
					System.out.println("실행중");
					getDataSendList().add(player_Out_Data);
					reciveDataClass = (DataClass) player_In_Data.readObject();
					name = reciveDataClass.getClientName();
					System.out.println(name + "연결 성공");
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("데이터 가져오기 실패");
					e.printStackTrace();
				}
				try {
					while (true) {
						reciveDataClass = (DataClass) player_In_Data.readObject();
						if(getDataSendList().size()==2 && !isMulti) {
							isMulti = true;
							reciveDataClass.setStartTime((int)System.currentTimeMillis()/10);
						}
						sendData(reciveDataClass, player_Out_Data);
						System.out.println(reciveDataClass.toString());
					}
				} catch (IOException | ClassNotFoundException e) {
				} finally {
					try {
						System.out.println(name + "연결 종료");
						getDataSendList().remove(player_Out_Data);
						socket.close();
					} catch (IOException e) {
						System.out.println("클라이언트 강제 종료");
					}
				}
			}
		}).start();
	}
	@Override
	public void sendData(DataClass sendDataClass, ObjectOutputStream objectOutputStream) {
		
		for (ObjectOutputStream send : getDataSendList()) {
			if (!send.equals(objectOutputStream)) {
				try {
					send.writeObject(sendDataClass);
					send.reset();
				} catch (IOException e) {
					System.out.println("전송 종료");
				}

			}
		}
	}
}
