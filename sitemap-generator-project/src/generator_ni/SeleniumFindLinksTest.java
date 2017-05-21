package generator_ni;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.By;
import java.util.List;

public class SeleniumFindLinksTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://vidazoldseges.hu/");
		List<WebElement> links = driver.findElements(By.tagName("a"));
		
		for(WebElement ele:links){
			System.out.println(ele.getAttribute("href"));
		}
		
	}

}
