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
		
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://vidazoldseges.hu");
		
		int level = 1;
		
		ArrayList< ArrayList<String> > linkTree = new ArrayList<>();
	 
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.tagName("a"));
		
		linkTree.add(getLinks(elements));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < level; ++i) {
			ArrayList<String> currentLevel = linkTree.get(i);
			ArrayList<String> newLevel = new ArrayList<>();
			for (String link : currentLevel) {
				if (link.startsWith("https")) {
					driver.get(link);
					newLevel.addAll(getLinks((ArrayList<WebElement>) driver.findElements(By.tagName("a"))));
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			linkTree.add(newLevel);
		}
		
		for (String link : linkTree.get(level)) {
			System.out.println(link);
		}
	}

}
