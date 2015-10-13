package Crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	
	public static void main(String[] args) {
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Document dom = null;
		Document dom_child = null;
		
		try {
			
			//Load the DOM
			dom = Jsoup.connect(prop.getProperty("url")).timeout(0).get();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Elements test = dom.select("header.productTitle");
		
		for(Element e: test){
			//System.out.println("Next URL: "+ e.child(0).absUrl("href"));
			try {
				
				dom_child = Jsoup.connect(e.child(0).absUrl("href")).timeout(0).get();
				
				Elements titlePicker = dom_child.select(".pageTitle > span");/*Gets the title tag and content*/
				Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
				Elements categoriesPicker = dom_child.select(".simpleTable tr").select("th");
				
			
				
				switch(categoriesPicker.text().toLowerCase()){
				
					case "sistema operativo": System.out.println(categoriesPicker.select("td").text()); 
					break;
					case "processador":  System.out.println(categoriesPicker.select("td").text());
					break;
					default:System.out.println("default break");
					break;
				
				}
				Elements infoPicker = dom_child.select(".simpleTable td");/*Gets the phone specifications tag and content*/
				
				
				
				/*System.out.println("\n" +"Model:" + titlePicker.text() + "\n");
				System.out.println("Price:" +  pricePicker.text() + "\n");
				System.out.println(categoriesPicker.text());*/
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		String title = dom.title();
		System.out.println(title);
	}

}
