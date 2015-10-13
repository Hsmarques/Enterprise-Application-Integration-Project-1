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
		
		Elements phoneList = dom.select("header.productTitle");
		
		for(Element e: phoneList){
			//System.out.println("Next URL: "+ e.child(0).absUrl("href"));
			try {
				
				dom_child = Jsoup.connect(e.child(0).absUrl("href")).timeout(0).get();
				
				Elements titlePicker = dom_child.select(".pageTitle > span");
				Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
				Elements categoriesPicker = dom_child.select("table.simpleTable tr");
				
				System.out.println("=======================================");
				System.out.println("Model:" + titlePicker.text());
				System.out.println("Preco" + pricePicker.text());
				
				
				
				for(Element aspects : categoriesPicker){
					
				
					
					switch(aspects.select("th").text().toLowerCase()){
				
						case "sistema operativo":
							System.out.println("so: " + aspects.select("td").text());
						break;
						case "processador":
							System.out.println("processador:" + aspects.select("td").text());
						break;
						case "tecnologia do ecrã":  
							System.out.println("tecnologia ecra: "+ aspects.select("td").text());
						break;
						case "tamanho do ecrã":  
							System.out.println("tamanho ecra:" + aspects.select("td").text());
						break;
						case "frequências":  
							System.out.println("Frequências:" + aspects.select("td").text());
						break;
						case "redes":  
							System.out.println("redes:" + aspects.select("td").text());
						break;
						case "bluetooth":  
							System.out.println("bluetooth:" + aspects.select("td").text());
						break;
						case "wi-fi":  
							System.out.println("wi-fi:" + aspects.select("td").text());
						break;
						case "resolução máxima (em pixeis)":
							
							if(aspects.parent().previousElementSibling().text().toLowerCase().equals("máquina fotográfica")){
								System.out.println("câmara:" + aspects.select("td").text());
								break;
							}
							else{
								break;	
							}
						case "câmera frontal":
							
						break;
					
						case "bateria":
							System.out.println(aspects.select("td").text());
						break;
						case "autonomia":
							System.out.println(aspects.select("td").text());
						break;
						case "dimensoes":
							System.out.println(aspects.select("td").text());
						break;
						case "peso":
							System.out.println(aspects.select("td").text());
						break;
						default: 
						break;
				
					}
				}
		
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
	}

}
