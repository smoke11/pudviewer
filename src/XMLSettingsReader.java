import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import smoke11.DebugView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 11.03.13
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public class XMLSettingsReader {
    public static String[] Names;
    public static String[] Dirs;
    public static int main(String[] args){ //public static void readXMLSettings() {

        try {
            File fXmlFile;
            String s =args[0];
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, XMLSettingsReader.class.getSimpleName(), "Reading File: " + s);
            fXmlFile = new File(s); //settings.xml

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLSettingsReader.class.getSimpleName(), "Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("File");
            Names = new String[nList.getLength()];
            Dirs=new String[nList.getLength()];
            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLSettingsReader.class.getSimpleName(), "----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLSettingsReader.class.getSimpleName(), "Current Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getAttribute("Name");
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLSettingsReader.class.getSimpleName(), "Name: " + name);
                    Names[temp]=name;
                    String dir = eElement.getAttribute("Dir");
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLSettingsReader.class.getSimpleName(), "Dir: " + dir);
                    Dirs[temp]=dir;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
}
