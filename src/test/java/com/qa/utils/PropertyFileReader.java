package com.qa.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

	private FileInputStream fis;
	private Properties prop;
	private String filepath = ".\\src\\test\\java\\com\\qa\\data\\app.properties";

	public PropertyFileReader() {
		try {
			fis = new FileInputStream(filepath);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found at location: " + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
