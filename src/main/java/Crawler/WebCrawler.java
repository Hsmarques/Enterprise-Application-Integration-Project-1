package Crawler;

import generated.Communication;
import generated.Screen;
import generated.Smartphone;
import generated.Smartphones;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

		// Load the DOM
		dom = jSoupLoader(prop.getProperty("url"));

		Elements phoneList = dom.select("header.productTitle");

		for (Element e : phoneList) {
			// System.out.println("Next URL: "+ e.child(0).absUrl("href"));
			dom_child = jSoupLoader(e.child(0).absUrl("href"));

			Smartphones smartphones = new Smartphones();
			Smartphone smartphone = new Smartphone();
			Screen screen = new Screen();
			Communication communication = new Communication();

			Elements titlePicker = dom_child.select(".pageTitle > span");
			Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
			Elements categoriesPicker = dom_child.select("table.simpleTable tr");

			System.out.println("=======================================");
			System.out.println("Model:" + titlePicker.text());
			System.out.println("Preco" + pricePicker.text());

			for (Element aspects : categoriesPicker) {

				switch (aspects.select("th").text().toLowerCase()) {

				case "sistema operativo":
					smartphone.setSo(aspects.select("td").text());
					System.out.println("so: " + aspects.select("td").text());
					break;
				case "processador":
					smartphone.setProcessor(aspects.select("td").text());
					System.out.println("processador:" + aspects.select("td").text());
					break;
				case "tecnologia do ecr�":
					screen.setType(aspects.select("td").text());
					System.out.println("tecnologia ecra: " + aspects.select("td").text());
					break;
				case "tamanho do ecr�":
					screen.setSize(aspects.select("td").text());
					System.out.println("tamanho ecra:" + aspects.select("td").text());
					break;
				case "frequ�ncias":
					smartphone.setFrequency(aspects.select("td").text());
					System.out.println("Frequ�ncias:" + aspects.select("td").text() );
					break;
				case "bluetooth":
					communication.setBluetooth(aspects.select("td").text());
					System.out.println("bluetooth:" + aspects.select("td").text());
					break;
				case "wi-fi":
					communication.setWifi(aspects.select("td").text());
					System.out.println("wi-fi:" + aspects.select("td").text());
					break;
				case "resolu��o m�xima (em pixeis)":

					if (aspects.parent().previousElementSibling().text().toLowerCase().equals("m�quina fotogr�fica")) {
						smartphone.setCamera(aspects.select("td").text());
						System.out.println("c�mara:" + aspects.select("td").text());
						break;
					} else {
						break;
					}
				case "c�mera frontal":
					if (aspects.parent().previousElementSibling().text().toLowerCase().equals("m�quina fotogr�fica")) {
						smartphone.setCamera(aspects.select("td").text());
						System.out.println("c�mara:" + aspects.select("td").text());
						break;
					} else {
						break;
					}
				case "bateria":
					smartphone.setBatteryType(aspects.select("td").text());
					System.out.println(aspects.select("td").text());
					break;
				case "autonomia":
					smartphone.setAutonomy(aspects.select("td").text());
					System.out.println(aspects.select("td").text());
					break;
				case "dimensoes":
					smartphone.setDimensions(aspects.select("td").text());
					System.out.println(aspects.select("td").text());
					break;
				case "peso":
					smartphone.setWeight(aspects.select("td").text());
					System.out.println(aspects.select("td").text());
					break;
				default:
					break;

				}
			}

		}
	}

	/*
	 * MARSHALL AND UNMARSHALL try { JAXBContext context =
	 * JAXBContext.newInstance(Smartphone.class); Marshaller marshaller =
	 * context.createMarshaller();
	 * 
	 * marshaller.marshal(jaxbElement, handler);
	 * 
	 * 
	 * } catch (JAXBException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); }
	 */

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
