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

	public static void main(String[] args) throws IOException {
		
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("https://vidazoldseges.hu");
		
		int level = 1;
		
		ArrayList<String> links = new ArrayList<>();
		
		ArrayList< ArrayList<String> > linkTree = new ArrayList<>();
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.tagName("a"));
		
		linkTree.add(getLinks(elements));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < level; ++i) {
			ArrayList<String> currentLevel = linkTree.get(i);
			ArrayList<String> newLevel = new ArrayList<>();
			for (String link : currentLevel) {
				if (link.startsWith("http")) {
					driver.get(link);
					
					FileWriter fw = new FileWriter("sitemap.txt");
			        PrintWriter pw = new PrintWriter(fw);
						if(!links.contains(link)) {
							links.add(link);
						}
							else{
								System.err.println("Duplicated link!");
							}
						
						for(String linklist: links){
							pw.println(linklist);
						}
						pw.close();

					newLevel.addAll(getLinks((ArrayList<WebElement>) driver.findElements(By.tagName("a"))));
					try {
						Thread.sleep(2000);
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
