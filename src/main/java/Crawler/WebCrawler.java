package Crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import generated.Smartphone;

public class WebCrawler {
	public static void main(String[] args) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Document dom = null;
		Document dom_child = null;
		
		dom = jSoupLoader(prop.getProperty("url"));

		String title = dom.title();
		System.out.println(title);
		
		Elements phoneList = dom.select("header.productTitle");
		
		for(Element e: phoneList){
			//System.out.println("Next URL: "+ e.child(0).absUrl("href"));
				
				dom_child = jSoupLoader(e.child(0).absUrl("href"));
				
				Elements titlePicker = dom_child.select(".pageTitle > span");/*Gets the title tag and content*/
				Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
				Elements categoriesPicker = dom_child.select(".simpleTable tr");
				
			
				
				
				for(Element aspects : categoriesPicker){
					
					
					
					switch(aspects.select("th").text().toLowerCase()){
				
						case "sistema operativo":
							System.out.println("so: " + aspects.select("td").text());
						break;
						case "tecnologia do ecr�":  
							System.out.println("tecnologia ecra: "+ aspects.select("td").text()+"\n");
						break;
						case "tamanho do ecr�":  
							System.out.println("tamanho ecra:" + aspects.select("td").text());
						break;
						case "frequ�ncias":  
							System.out.println("Frequ�ncias:" + aspects.select("td").text());
						break;
						case "redes":  
							System.out.println("redes:" + aspects.select("td").text());
						break;
						case "bluetooth":  
							System.out.println("bluetooth:" + aspects.select("td").text());
						break;
						case "wifi":  
							System.out.println("wifi:" + aspects.select("td").text());
						break;
						case "c�mera frontal":
							System.out.println("CAMERAAAAAA");
							if(aspects.parent().previousElementSibling().text().toLowerCase() == "fotografia")
								System.out.println("funciona!!!!!!!!!!!!!");
							else
								System.out.println("nao funciona ");
						break;
						case "c�mera traseira":
						break;	
						case "bateria":
						break;
						case "autonomia":
						break;
						case "dimensoes":
						break;
						case "pesos":
						break;
						default://System.out.println("default break");
						break;
				
					}
				}	
		}
	}
	public static Document jSoupLoader(String url) {
		Document dom = null;
		try {
			// Load the DOM
			dom = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dom;
	}
}
