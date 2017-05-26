import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		System.setProperty("webdriver.gecko.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		try{
			FileReader fr = new FileReader("linkAndLevel.txt");
			Scanner sc = new Scanner(fr);
			String[]str = sc.nextLine().split(";");
			sc.close();
			
			if(str.length !=2) {
				throw new NotEnoughElement();
			}
			driver.get(str[0]);
			int level = Integer.parseInt(str[1]);
		
		ArrayList<String> links = new ArrayList<>();
		
		ArrayList< ArrayList<String> > linkTree = new ArrayList<>();
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.tagName("a"));
		
		linkTree.add(getLinks(elements));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
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
						
						e.printStackTrace();
					}
				}
			}
			linkTree.add(newLevel);
		}
		
		for (String link : linkTree.get(level)) {
			System.out.println(link);
		}
		
		}catch (FileNotFoundException err){
			System.out.println("File (linkAndLevel.txt) not found!");
		}catch (NotEnoughElement err){
			System.out.println(err.toString());
		}
	}
}
