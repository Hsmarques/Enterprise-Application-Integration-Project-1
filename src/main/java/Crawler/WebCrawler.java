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
<<<<<<< HEAD

=======
	
	
>>>>>>> refs/remotes/origin/master
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
		
<<<<<<< HEAD
		dom = jSoupLoader(prop.getProperty("url"));

		Elements phoneList = dom.select("header.productTitle");

		for (Element e : phoneList) {

			Smartphone smartphone = new Smartphone();

			dom_child = jSoupLoader(e.child(0).absUrl("href"));

			Elements titlePicker = dom_child.select(
					".pageTitle > span");/*
											 * Gets the title tag and content
											 */
			Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
			Elements categoriesPicker = dom_child.select(".simpleTable tr");

			for (Element phoneDesc : categoriesPicker) {

				switch (phoneDesc.select("td").text().toLowerCase()) {

				case "sistema operativo":
					System.out.println(categoriesPicker.select("td").text());
					break;
				case "processador":
					System.out.println(categoriesPicker.select("td").text());
					break;
				default:
					System.out.println("default break");
					break;

				}
			}
			Elements infoPicker = dom_child
					.select(".simpleTable td");/*
												 * Gets the phone specifications
												 * tag and content
												 */

			/*
			 * System.out.println("\n" +"Model:" + titlePicker.text() + "\n");
			 * System.out.println("Price:" + pricePicker.text() + "\n");
			 * System.out.println(categoriesPicker.text());
			 */

		}

		String title = dom.title();
		System.out.println(title);
	}

	public static Document jSoupLoader(String url) {
		Document dom = null;
		try {
			// Load the DOM
			dom = Jsoup.connect(url).timeout(0).get();
=======
		Document dom = null;
		Document dom_child = null;
		
		try {
			
			//Load the DOM
			dom = Jsoup.connect(prop.getProperty("url")).timeout(0).get();
		
>>>>>>> refs/remotes/origin/master
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
		return dom;
=======
		
		Elements phoneList = dom.select("header.productTitle");
		
		for(Element e: phoneList){
			//System.out.println("Next URL: "+ e.child(0).absUrl("href"));
			try {
				
				dom_child = Jsoup.connect(e.child(0).absUrl("href")).timeout(0).get();
				
				Elements titlePicker = dom_child.select(".pageTitle > span");/*Gets the title tag and content*/
				Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
				Elements categoriesPicker = dom_child.select(".simpleTable tr");
				
			
				
				
				for(Element aspects : categoriesPicker){
					
					
					
					switch(aspects.select("th").text().toLowerCase()){
				
						case "sistema operativo":
							System.out.println("so: " + aspects.select("td").text());
						break;
						case "tecnologia do ecrã":  
							System.out.println("tecnologia ecra: "+ aspects.select("td").text()+"\n");
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
						case "wifi":  
							System.out.println("wifi:" + aspects.select("td").text());
						break;
						case "câmera frontal":
							System.out.println("CAMERAAAAAA");
							if(aspects.parent().previousElementSibling().text().toLowerCase() == "fotografia")
								System.out.println("funciona!!!!!!!!!!!!!");
							else
								System.out.println("nao funciona ");
						break;
						case "câmera traseira":
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
		
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		String title = dom.title();
		System.out.println(title);
>>>>>>> refs/remotes/origin/master
	}
}
