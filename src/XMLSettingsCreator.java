import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import smoke11.wc2utils.XMLSettingsReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: nobody_traveler
 * Date: 11/03/13
 * Time: 09:42
 * To change this template use File | Settings | File Templates.
 */
public class XMLSettingsCreator {

    public static void main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("MainSettings");
            doc.appendChild(rootElement);

            //LightWater0
            Element tile = doc.createElement("File");
            rootElement.appendChild(tile);
            //id
            Attr attr = doc.createAttribute("Dir");
            attr.setValue("0010");
            tile.setAttributeNode(attr);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            String fulldir = XMLSettingsCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String dironly = fulldir.substring(0, fulldir.lastIndexOf("/") + 1);
            File f;
            if(fulldir.contains(".jar".toLowerCase()))
                f=new File(dironly+"settings.xml");
            else
                f=new File(fulldir+"settings.xml");
            if(f.exists())
            {
                f.delete();
            }
            StreamResult result;

            System.out.println(fulldir);
            System.out.println(dironly);
            if(fulldir.contains(".jar".toLowerCase()))  //to fix bug with name of jar when running from jar
                result = new StreamResult(new File(dironly+"settings.xml")); //TODO: move naming to mainwindow class, and here get it from args, as it is in readerxml
            else
                result = new StreamResult(new File(fulldir+"settings.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            //return -1;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            //return -1;
        }
    }
}
