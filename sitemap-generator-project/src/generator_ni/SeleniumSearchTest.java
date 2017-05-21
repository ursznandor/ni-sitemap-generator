package generator_ni;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumSearchTest {
	
	public static void main(String[] args) throws InterruptedException{
		
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://vidazoldseges.hu");
		WebElement searchBox;
		searchBox = driver.findElement(By.id("woocommerce-product-search-field-0"));
		searchBox.sendKeys("burgonya");
		searchBox.submit();
		Thread.sleep(5000);
		driver.quit();
	}

}