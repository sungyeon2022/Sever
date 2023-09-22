package miniprojectServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data

public class Server {
	private List<ObjectOutputStream> dataSendList;
	private int socket;
	private ServerSocket serverSocket;
	private boolean isString;
	public Server() {
		this.socket = 8050;
		this.isString = false;
		this.dataSendList = Collections.synchronizedList(new ArrayList<ObjectOutputStream>());
	}
	public void start() {}
	public void ReceiveThread(Socket socket) {}
	public void sendData(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {}
	
}
