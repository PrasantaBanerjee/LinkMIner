package com.qa.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteData {

	private static FileOutputStream fos;
	
	public static void toTextFile(String path, String data) {	
		try {
			fos = new FileOutputStream(path);
			fos.write(data.getBytes());
			fos.flush();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found at location: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done writing the data to the file.");
	}
}
