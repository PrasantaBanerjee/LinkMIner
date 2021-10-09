package com.qa.tests;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import com.qa.crawler.Crawler;
import com.qa.utils.WriteData;

public class TestCrawler {

	private static String timestamp = new SimpleDateFormat("EEE MM-dd-yyyy hh-mm-ss a").format(new Date());
	private static String resultFilename = "Log_" + timestamp + ".txt";
	private static String resultDir = ".\\results\\" + resultFilename;

	@Test
	public void execution() {
		Crawler crawler = new Crawler();
		crawler.authRequired(false);
		crawler.start();		
		WriteData.toTextFile(resultDir, crawler.validateLinks());
		crawler.end();
		
		try {
			Desktop.getDesktop().open(new File(".\\results"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

