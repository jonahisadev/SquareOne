package com.amittaigames.engine.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class CoreServer extends Thread{

	private int port;
	private DatagramSocket socket;
	
	protected boolean running = true;
	
	/**
	 * @param port to start the server on
	 */
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

	/**
	 * Called immediately after server thread has been started
	 */
	protected abstract void onStart();

	/**
	 * Called on packet receipt to handle the packet data
	 * @param packet Datagram for IP and port
	 * @param msg String message of packet
	 */
	protected abstract void handlePacket(DatagramPacket packet, String msg);

	/**
	 * Waits for packet to be received
	 */
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

	/**
	 * Sends packet to client UDP socket
	 * @param ip IP to send packet to
	 * @param port Port of client application   
	 * @param msg String message to send   
	 */
	protected void sendPacket(InetAddress ip, int port, String msg) {
		try {
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}