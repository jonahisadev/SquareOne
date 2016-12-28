package com.amittaigames.engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Util {

	private Util() {}

	/**
	 * Read internal file
	 * @param path Path to file
	 * @return String contents of file
	 */
	public static String readInternalFile(String path) {
		BufferedReader br = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream(path)));
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

}
