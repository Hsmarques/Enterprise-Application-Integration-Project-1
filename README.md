# <div align="center">Enterprise Application Integration course</div>

## <div align="center">Project #1</div>

#### Objectives
* Learn **XML technologies**. In particular, you will learn XML, XSD, XSL, XSLT and XPATH. This project is mostly about XML processing.  

* Understand the technique of “**Screen Scraping**”. Screen scraping consists in parsing the information shown in a terminal so that it can be used on a different system. It is the technique used for application integration where the only access point to an application is through its user interface (e.g., a venerable VT100 text terminal). Since, nowadays, web systems are ubiquitous, screen scraping is mostly used to gather and process information from web sites that do not expose APIs to the general public (or their business partners).

* Remember (or learn) how to use **HTML parsers**. These parsers can read HTML code and create data structures representing the web page, such as DOM 1 documents. You may also need to resort to regular expressions to clean data available in the DOM document. Regular expressions are an extremely powerful mechanism for cleaning, gathering and processing data embedded in text files.

* Learn how to create simple asynchronous and message‐oriented applications.

![Information flow](https://dl.dropboxusercontent.com/u/15756440/IS%20project%201.png)

In this assignment you should create four applications. The first one is a Web Crawler that collects information of smartphones from the Pixmania web site (http://www.pixmania.pt/index.html), extracts the relevant data to XML, and sends it to a Java Message Service Topic. This topic serves two other applications that process the data. One of the applications, Price Keeper, keeps the prices of smartphones in memory. The Price Requester asks prices of smartphones to the Price Keeper. The other application, HTML Summary Creator, summarizes the information and creates HTML files for later visualization. Figure 1 illustrates this scenario. The four applications are described in the following paragraphs.
<hr>

#### The Web Crawler
The Web Crawler is a stand-­‐alone command-­‐line application that reads a web page and sends an XML message to a JMS Topic, carrying details of smartphones from the Pixmania site. You should use an HTML parser (e.g., Jsoup), to get the data from the web page. You should not parse the web page directly using regular expressions. Nevertheless, you are allowed to use regular expressions to extract small pieces of data from the results of the HTML parser. For example, you might find a string that looks like “September 10, 2015” and use regular expressions to extract the different parts of the date.

Once you get the DOM document of the web page, you will need to convert it to XML. If this helps, you can do as follows, although creating the Java classes from scratch is also possible:

* Define the XML schema (this may involve the trang tool, to create XSD from an XML sample). You must include an XML schema file (XSD) as part of your final submission and be ready to explain it;
* From the XML schema, generate the Java classes using the XML binding compiler, xjc);
* Once you have the Java classes that can keep the data, you can instantiate and use them in the normal way, in the  Web Crawler (or other application) source code.

Each time the Crawler runs, it parses the web page, creates and populates the Java objects that keep the web site’s data, and sends a JMS message with the XML document to the JMS Topic. If the topic is down for some reason, you should keep a file with the data that the Crawler was unable to publish. In this case, the Crawler should stay in a cycle for a limited amount of time, retrying to publish. If it does not manage to do the publication after a given number of attempts, it should give up and exit. The next time it starts, the Crawler must automatically check if there is any unpublished message to send and repeat the cycle once again.

You are responsible for defining the exact format of the XML messages. For your reference, you should consider that each message must contain a list of smartphones, each having data like brand, model, processor, screen, camera, URL of the description, etc. You can limit your search to 15­‐20 smartphones, from 4-­5 different brands. You can take different options here, but you are advised to discuss them with the Professor.

Although you only need one site and HTTP access, design your Crawler so that:
* Changing web site does not require too much effort;
* Changing to another input data source (e.g., FTP, file access) is simple.

Finally, **keep some test HTML files in your disk**, just in case the website changes. Your web crawler **must** be able to read data from these files.
<hr>

#### HTML Summary Creator
This application should run permanently, waiting for XML messages from the JMS topic. This application must create an HTML file, using the XML files coming from the Topic (keep one file per reading of the Crawler). For this, you should use an XSL template for transforming the resulting XML file into HTML.

You are pretty much free to define the HTML as you want, but you should include all the data collected by the Crawler. Organization of the page is important. Use a web browser with a built-­‐in XSLT engine (e.g., Firefox) to apply the transformation and display the resulting HTML page.

Note: Use durable subscriptions to ensure that even if the HTML Summary Creator fails, the Topic will keep the messages for later retrieval.
<hr>

#### Price Keeper
The purpose of this application is to keep track of the smartphone prices, as the Web Crawler sends them. Keeping the prices in memory is sufficient for the sake of this assignment. The Price Keeper must listen to two different destinations: the topic where the Crawler sends its messages (a durable subscription is necessary here) and a queue where the price requester(s) ask for prices.
<hr>

#### Price Requester
The Price Requester should be a very simple application. It must allow the user to introduce some reference of the smartphone (e.g., brand and model), sends this data to the Price Keeper and gets a price back. To get the response, the Price Requester must create a **temporary queue** and add the reference of this queue in the request it does to the Keeper. This easily allows several Requesters to run at the same time.
