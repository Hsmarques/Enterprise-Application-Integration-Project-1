package Crawler;

import generated.Communication;
import generated.Screen;
import generated.Smartphone;
import generated.Smartphones;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import JMS.Sender;

public class WebCrawler {

	static Smartphones smartphonesList = new Smartphones ();
	static StringWriter stringwriter = new StringWriter();

	
	public static void main(String[] args) throws NamingException {
		Sender s = new Sender();
		crawlingThoseWebz();
		marshall(smartphonesList);
		s.send(stringwriter.toString());
	}
	
	public static void crawlingThoseWebz(){
		
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

		// Load the DOM
		dom = jSoupLoader(prop.getProperty("url"));

		Elements phoneDom = dom.select("header.productTitle");

		for (Element e : phoneDom) {
			// System.out.println("Next URL: "+ e.child(0).absUrl("href"));
			dom_child = jSoupLoader(e.child(0).absUrl("href"));

			
			Smartphone smartphone = new Smartphone();
			Screen screen = new Screen();
			Communication communication = new Communication();

			Elements titlePicker = dom_child.select(".pageTitle > span");
			Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
			Elements categoriesPicker = dom_child.select("table.simpleTable tr");
			
			smartphone.setModel(titlePicker.text());
			smartphone.setPrice(pricePicker.text());

			for (Element aspects : categoriesPicker) {
				
				switch (aspects.select("th").text().toLowerCase()) {

				case "sistema operativo":
					smartphone.setSo(aspects.select("td").text());
					break;
				case "processador":
					smartphone.setProcessor(aspects.select("td").text());
					break;
				case "tecnologia do ecrã":
					screen.setType(aspects.select("td").text());
					break;
				case "tamanho do ecrã":
					screen.setSize(aspects.select("td").text());
					break;
				case "frequências":
					smartphone.setFrequency(aspects.select("td").text());
					break;
				case "bluetooth":
					communication.setBluetooth(aspects.select("td").text());
					break;
				case "wi-fi":
					communication.setWifi(aspects.select("td").text());
					break;
				case "resolução máxima (em pixeis)":
					if (aspects.parent().previousElementSibling().text().toLowerCase().equals("máquina fotográfica")) {
						smartphone.setCamera(aspects.select("td").text());
						
						break;
					} else {
						break;
					}
				case "câmera traseira":
					if (aspects.parent().previousElementSibling().text().toLowerCase().equals("máquina fotográfica")) {
						smartphone.setCamera(aspects.select("td").text());
						break;
					} else if(aspects.parent().previousElementSibling().text().toLowerCase().equals("fotografia")) {
						smartphone.setCamera(aspects.select("td").text());
						break;
					}
					else 
						break;
				case "bateria":
					smartphone.setBatteryType(aspects.select("td").text());
					break;
				case "autonomia":
					smartphone.setAutonomy(aspects.select("td").text());
					break;
				case "dimensoes":
					smartphone.setDimensions(aspects.select("td").text());
					break;
				case "peso":
					smartphone.setWeight(aspects.select("td").text());
					break;
				default:
					break;

				}
			}
			smartphonesList.getSmartphone().add(smartphone);
			
		}
	}
	
	public static void marshall(Smartphones smartphones) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Smartphones.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(smartphonesList, stringwriter);
			System.out.println(stringwriter.getBuffer());

		} catch (JAXBException e) {
			e.printStackTrace();
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
