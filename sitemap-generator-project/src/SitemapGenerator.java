import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SitemapGenerator {
	
		private static ArrayList<String> getLinks(ArrayList<WebElement> elements) {
		ArrayList<String> level = new ArrayList<>();
		for (WebElement element : elements) {
			String url = element.getAttribute("href");
			if (url != null && url.startsWith("http")) {
				level.add(element.getAttribute("href"));
			}
		}
		return level;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
