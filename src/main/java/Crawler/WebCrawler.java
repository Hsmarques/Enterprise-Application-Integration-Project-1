package Crawler;

import generated.Communication;
import generated.Screen;
import generated.Smartphone;
import generated.Smartphones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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

	static Smartphones smartphonesList = new Smartphones();
	static StringWriter stringwriter = new StringWriter();
	static String old = null;

	public static void main(String[] args) throws NamingException {
		Sender s = new Sender();
		crawlingForData();
		marshall(smartphonesList);
		File file = new File("SavedXML.txt");
		// If there is previous unsent xml tries to send it again
		if (file.exists()) {
			try {
				old = readFile("SavedXML.txt");
				System.out.println(old);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// If the xml is successfully sent deletes the file
			if (s.getMaxtries() < 10) {
				s.send(old);
				if (file.exists())
					file.delete();
			}
		} else {
			crawlingForData();
			marshall(smartphonesList);
			
			// In case it fails to connect to WildFly saves the XML
			if (s.getMaxtries() == 10) {
				saveXML(stringwriter.toString());
			} else {
				s.send(stringwriter.toString());
			}
		}
	}

	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	/* Saves the xml into a file */
	public static void saveXML(String xml) {
		try {
			PrintWriter out = new PrintWriter("SavedXML.txt");
			out.println(xml);
			out.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	// Crawler for the PixMania
	public static void crawlingForData() {

		Properties prop = new Properties();
		InputStream input = null;

		// loads properties
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

		/*
		 * Load the DOM of pixmania phones
		 */

		dom = jSoupLoader(prop.getProperty("url"));

		Elements phoneDom = dom.select("header.productTitle");// Selects ea

		/*
		 * Fills each Smartphone info using jsoup. Adds each Smartphone to the
		 * Smartphones List
		 */

		for (Element e : phoneDom) {

			dom_child = jSoupLoader(e.child(0).absUrl("href"));

			Smartphone smartphone = new Smartphone();
			Screen screen = new Screen();
			Communication communication = new Communication();

			Elements titlePicker = dom_child.select(".pageTitle > span");
			Elements pricePicker = dom_child.select("div.currentPrice").select("ins");
			Elements categoriesPicker = dom_child.select("table.simpleTable tr");

			smartphone.setModel(titlePicker.text());
			smartphone.setUrl(e.child(0).absUrl("href"));
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
				case "redes":
					smartphone.setNetwork(aspects.select("td").text());
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
					} else if (aspects.parent().previousElementSibling().text().toLowerCase().equals("fotografia")) {
						smartphone.setCamera(aspects.select("td").text());
						break;
					} else
						break;
				case "bateria":
					smartphone.setBatteryType(aspects.select("td").text());
					break;
				case "autonomia":
					smartphone.setAutonomy(aspects.select("td").text());
					break;
				case "autonomia em conversação":
					smartphone.setAutonomy(aspects.select("td").text());
					break;
				case "dimensões":
					smartphone.setDimensions(aspects.select("td").text());
					break;
				case "peso":
					smartphone.setWeight(aspects.select("td").text());
					break;
				default:
					break;
				}

			}
			smartphone.setScreen(screen);
			smartphone.setCommunication(communication);
			smartphonesList.getSmartphone().add(smartphone);

		}
	}

	/*
	 * 
	 */
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
			dom = Jsoup.connect(url).timeout(0).get();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return dom;
	}

}
