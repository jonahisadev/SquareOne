package com.amittaigames.engine.util;

import java.io.*;

public class FileIO {

	private FileIO() {}

	/**
	 * Read internal file
	 * @param path Path to file
	 * @return String contents of file
	 */
	public static String readInternalFile(String path) {
		BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.class.getResourceAsStream(path)));
		StringBuilder sb = new StringBuilder();
		
		try {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	/**
	 * Read external file
	 * @param path Path to file
	 * @return String contents of file
	 */
	public static String readExternalFile(String path) {
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	/**
	 * Read external binary file
	 * @param path Path to file
	 * @param bufSize Buffer size
	 * @return Data buffer
	 */
	public static byte[] readExternalBinary(String path, int bufSize) {
		byte[] buf = new byte[bufSize];
		
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(path));
			dis.readFully(buf);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buf;
	}

	/**
	 * Read external binary file with buffer length at the start of the file
	 * @param path Path to file
	 * @return Data buffer
	 */
	public static byte[] readExternalBinaryWithLength(String path) {
		byte[] buf = null;
		
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(path));
			buf = new byte[dis.readInt()];
			dis.readFully(buf);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buf;
	}

	/**
	 * Write external file
	 * @param path Path to file
	 * @param buffer Data to write
	 */
	public static void writeExternalFile(String path, String buffer) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(buffer);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write external binary file
	 * @param path Path to file
	 * @param data Data buffer
	 */
	public static void writeExternalBinary(String path, byte[] data) {
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(path));
			dos.write(data);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write external binary file with buffer length at the start of the file
	 * @param path Path to file
	 * @param data Data buffer
	 */
	public static void writeExternalBinaryWithLength(String path, byte[] data) {
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(path));
			dos.writeByte(data.length);
			dos.write(data);
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
