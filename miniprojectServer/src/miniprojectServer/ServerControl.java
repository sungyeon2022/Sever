package miniprojectServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Timer.TimerControl;



public class ServerControl extends Server {
	private TimerControl timerControl = new TimerControl();
	public ServerControl() {
		multiCheckThread();
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
				Object sendObject = null;
				Object name = null;
				try {
					outputStream = socket.getOutputStream();
					inputStream = socket.getInputStream();
					player_Out_Data = new ObjectOutputStream(outputStream);
					player_In_Data = new ObjectInputStream(inputStream);
					System.out.println("실행중");
					getDataSendList().add(player_Out_Data);
					name = ((HashMap<String, Object>) player_In_Data.readObject()).get("Client name");
					System.out.println(name + "님이 들어왔습니다.");
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("데이터 가져오기 실패");
					e.printStackTrace();
				}
				try {
					while (true) {
						sendObject = player_In_Data.readObject();
						sendData(sendObject, player_Out_Data);
						System.out.println((HashMap<String, Object>) sendObject);
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
	public void sendData(Object sendObject, ObjectOutputStream objectOutputStream) {
		((HashMap<String, Object>)sendObject).put("Timer", timerControl.getTimerString());
		((HashMap<String, Object>)sendObject).put("isStart", timerControl.isStart());
		for (ObjectOutputStream send : getDataSendList()) {
			if (!send.equals(objectOutputStream)) {
				try {
					send.writeObject(sendObject);
					send.reset();
				} catch (IOException e) {
					System.out.println("전송 종료");
				}

			}
		}
	}
	public void multiCheckThread() {
		new Thread(()->{
			while (!(getDataSendList().size()==2));
			if(getDataSendList().size()==2) {
				for(int i = 0;i<=9;i++) {
					timerControl.setTimerString(Integer.toString(10-i));
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				timerControl.checkPassedTimeThread();
			}
			
		}).start();
	}
}
