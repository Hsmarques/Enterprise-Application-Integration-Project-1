//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.14 at 03:21:15 PM BST 
//


package generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Wifi_QNAME = new QName("", "wifi");
    private final static QName _Color_QNAME = new QName("", "color");
    private final static QName _Weight_QNAME = new QName("", "weight");
    private final static QName _Type_QNAME = new QName("", "type");
    private final static QName _Processor_QNAME = new QName("", "processor");
    private final static QName _Url_QNAME = new QName("", "url");
    private final static QName _Frequency_QNAME = new QName("", "frequency");
    private final static QName _Network_QNAME = new QName("", "network");
    private final static QName _Autonomy_QNAME = new QName("", "autonomy");
    private final static QName _Size_QNAME = new QName("", "size");
    private final static QName _Bluetooth_QNAME = new QName("", "bluetooth");
    private final static QName _Price_QNAME = new QName("", "price");
    private final static QName _BatteryType_QNAME = new QName("", "battery_type");
    private final static QName _So_QNAME = new QName("", "so");
    private final static QName _Camera_QNAME = new QName("", "camera");
    private final static QName _Dimensions_QNAME = new QName("", "dimensions");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Smartphone }
     * 
     */
    public Smartphone createSmartphone() {
        return new Smartphone();
    }

    /**
     * Create an instance of {@link Screen }
     * 
     */
    public Screen createScreen() {
        return new Screen();
    }

    /**
     * Create an instance of {@link Communication }
     * 
     */
    public Communication createCommunication() {
        return new Communication();
    }

    /**
     * Create an instance of {@link Smartphones }
     * 
     */
    public Smartphones createSmartphones() {
        return new Smartphones();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "wifi")
    public JAXBElement<String> createWifi(String value) {
        return new JAXBElement<String>(_Wifi_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "color")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createColor(String value) {
        return new JAXBElement<String>(_Color_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "weight")
    public JAXBElement<String> createWeight(String value) {
        return new JAXBElement<String>(_Weight_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "processor")
    public JAXBElement<String> createProcessor(String value) {
        return new JAXBElement<String>(_Processor_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "frequency")
    public JAXBElement<String> createFrequency(String value) {
        return new JAXBElement<String>(_Frequency_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "network")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createNetwork(String value) {
        return new JAXBElement<String>(_Network_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "autonomy")
    public JAXBElement<String> createAutonomy(String value) {
        return new JAXBElement<String>(_Autonomy_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "size")
    public JAXBElement<String> createSize(String value) {
        return new JAXBElement<String>(_Size_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "bluetooth")
    public JAXBElement<String> createBluetooth(String value) {
        return new JAXBElement<String>(_Bluetooth_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "price")
    public JAXBElement<String> createPrice(String value) {
        return new JAXBElement<String>(_Price_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "battery_type")
    public JAXBElement<String> createBatteryType(String value) {
        return new JAXBElement<String>(_BatteryType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "so")
    public JAXBElement<String> createSo(String value) {
        return new JAXBElement<String>(_So_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "camera")
    public JAXBElement<String> createCamera(String value) {
        return new JAXBElement<String>(_Camera_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "dimensions")
    public JAXBElement<String> createDimensions(String value) {
        return new JAXBElement<String>(_Dimensions_QNAME, String.class, null, value);
    }

}
