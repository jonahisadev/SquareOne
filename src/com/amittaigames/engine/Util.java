package com.amittaigames.engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Util {

	private Util() {}
	
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

}
