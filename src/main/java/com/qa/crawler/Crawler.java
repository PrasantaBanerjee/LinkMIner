package com.qa.crawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.qa.utils.PropertyFileReader;

public class Crawler {

	private WebDriver driver;
	private boolean flag;
	private PropertyFileReader app = new PropertyFileReader();

	public void authRequired(boolean flag) {
		this.flag = flag;
	}

	public boolean isAuthRequired() {
		return flag;
	}

	public void start() {
		System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.setHeadless(true);
		driver = new ChromeDriver(opt);
		driver.get(app.getProperty("URL").trim());
		System.out.println("Crawler searching for links in " + app.getProperty("URL").trim());
	}

	public HashSet<String> getAllUniqueLinks() {
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[@href]"));
		HashSet<String> uniqueLinks = new HashSet<>();
		for (WebElement each : allLinks) {
			uniqueLinks.add(each.getAttribute("href"));
		}
		return uniqueLinks;
	}

	public String encodeCredentials(String username, String password) {
		return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	public String getResponseFor(String link) {
		String response;
		try {
			URL url = new URL(link);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (isAuthRequired() == true) {
				String username = app.getProperty("Username").trim();
				String password = app.getProperty("Password").trim();
				conn.setRequestProperty("Authorization", "Basic " + encodeCredentials(username, password));
			}
			conn.connect();
			if (conn.getResponseCode() < 400) {
				response = url + " -> " + conn.getResponseMessage() + " [" + conn.getResponseCode() + "].\n";
			} else {
				response = url + " -> " + conn.getResponseMessage() + " [" + conn.getResponseCode() + "].\n";
			}
		} catch (MalformedURLException e) {
			response = "Error occured for [" + link + "]: " + e.getMessage();
		} catch (IOException e) {
			response = "Error occured for [" + link + "]: " + e.getMessage();
		}
		return response;
	}

	public String validateLinks() {
		StringBuilder sb = new StringBuilder();
		for (String eachLink : getAllUniqueLinks()) {
			sb = sb.append(getResponseFor(eachLink));
		}
		return sb.toString();
	}

	public void end() {
		if (driver != null) {
			driver.quit();
		}
		System.out.println("Crawler instance ended.");
	}

}
