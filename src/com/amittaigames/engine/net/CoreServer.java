package com.amittaigames.engine.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class CoreServer extends Thread{

	private int port;
	private DatagramSocket socket;
	
	protected boolean running = true;
	
	public CoreServer(int port) {
		try {
			this.port = port;
			this.socket = new DatagramSocket(port);
			this.start();
			this.onStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void onStart();
	protected abstract void handlePacket(DatagramPacket packet, String msg);
	
	@Override
	public void run() {
		while (running) {
			try {
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String str = new String(buffer).trim();
				handlePacket(packet, str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void sendPacket(InetAddress ip, int port, String msg) {
		try {
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}