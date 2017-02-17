package com.amittaigames.engine.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class CoreClient extends Thread {

	private InetAddress ip;
	private int port;
	private DatagramSocket socket;
	
	protected boolean running = true;
	
	/**
	 * @param addr IP address to connect to
	 * @param port Port to connect to
	 */
	public CoreClient(String addr, int port) {
		try {
			this.ip = InetAddress.getByName(addr);
			this.port = port;
			this.socket = new DatagramSocket();
			this.start();
			this.onStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called immediately after client thread has been started
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
	 * Sends packet to connected UDP socket
	 * @param msg String message to send
	 */
	protected void sendPacket(String msg) {
		try {
			DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ip, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
