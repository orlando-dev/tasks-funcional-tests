package br.ce.orlando.tasks.funcional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
//	private String noteRemoteDriver = "http://192.168.56.1:4444/wd/hub";
//	private String noteNavigate = "http://192.168.56.1:8001/tasks";
	
	private String pcRemoteDriver = "http://192.168.18.80:4444/wd/hub";
	private String pcNavigate = "http://192.168.18.80:8001/tasks";
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver(); Local
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL(pcRemoteDriver), cap);
		driver.navigate().to(pcNavigate);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("deveSalvarTarefaComSucesso - Test E2E ");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", mensagem);

		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();

		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", mensagem);

		} finally {
			driver.quit();
		}
	}

	@Test
	public void naoDeveSalvarTarefasSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("naoDeveSalvarTarefasSemData");
			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", mensagem);

		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("naoDeveSalvarTarefaComDataPassada");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");
			driver.findElement(By.id("saveButton")).click();

			String mensagem = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", mensagem);

		} finally {
			driver.quit();
		}
	}
}
