package com.project.staragile.insureme;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class SeleniumTestngTest {
WebDriver driver;

@BeforeTest
public void initDriver() {
String path = System.getProperty("user.dir") + File.separator + "driver" + File.separator
+ "chromedriver-2";
System.setProperty("webdriver.chrome.driver", path);
WebDriver driver=new ChromeDriver();
driver.manage().window().maximize();
}

@Test
void testCreatePolicy() {
	Policy policy = new Policy(1, "Subhajit Saha", "Individual" , 10000, "11-Jun-1988", "11-Jul-2023");
	PolicyService pService = new PolicyService();
	//Policy outputPolicy = pService.CreatePolicy();
	assertEquals(policy.getPolicyId(), pService.generateDummyPolicy().getPolicyId());
	
}

@Test
void testSearchPolicy() {
	PolicyService pService = new PolicyService();
	assertEquals(null,pService.searchPolicy());
}

@AfterTest
public void tearDown() {
driver.close();
driver.quit();
}

}