package br.ce.orlando.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
//	private String noteRemoteDriver = "http://172.26.185.107:4444/wd/hub";
//	private String noteNavigate = "http://172.26.185.107:9999/tasks";
	
	private String pcRemoteDriver = "http://192.168.18.80:4444/wd/hub";
	private String pcNavigate = "http://192.168.18.80:9999/tasks";

	@Test
	public void healthCheck() throws MalformedURLException {
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL(pcRemoteDriver), cap);
		try {
			driver.navigate().to(pcNavigate);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("1.2.3"));
			
		} finally  {
			driver.quit();
		}
		
	}
	
}
