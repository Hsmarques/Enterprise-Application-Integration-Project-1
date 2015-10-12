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
		
		Document doc = null;
		Document doc_child = null;
		
		try {
			doc = Jsoup.connect(prop.getProperty("url")).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Elements test = doc.select("header.productTitle");
		
		for(Element e: test){
			System.out.println(e.child(0).absUrl("href"));
			System.out.println("HELLO");
			try {
				doc_child = Jsoup.connect(e.child(0).absUrl("href")).get();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		String title = doc.title();
		System.out.println(title);
	}

}
