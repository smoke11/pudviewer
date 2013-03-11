import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

            fXmlFile = new File(args[0]); //settings.xml

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("File");
            Names = new String[nList.getLength()];
            Dirs=new String[nList.getLength()];
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name = eElement.getAttribute("Name");
                    System.out.println(name);
                    Names[temp]=name;
                    String dir = eElement.getAttribute("Dir");
                    System.out.println("Dir: " +dir);
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
