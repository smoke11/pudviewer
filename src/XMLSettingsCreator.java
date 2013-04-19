import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import smoke11.DebugView;

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
            String[] names = new String[]{"data_files","unit_tiles","terrain_tiles","sprites","maps"};
            String[] dirs = new String[]{argv[0],
                    argv[0]+"unit_tiles.xml",
                    argv[0]+"terrain_tiles.xml",
                    argv[0]+"sprites\\",
                    argv[0]+"test maps\\"
            };


            Element tile;
            Attr attr;
            for (int i=0;i<names.length;i++)
            {
            tile = doc.createElement("File");
            rootElement.appendChild(tile);

            attr = doc.createAttribute("Name");
            attr.setValue(names[i]);
            tile.setAttributeNode(attr);

            attr = doc.createAttribute("Dir");
            attr.setValue(dirs[i]);
            tile.setAttributeNode(attr);

            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

                File f=new File(argv[0]+"settings.xml");
            if(f.exists())
            {
                f.delete();
            }
            StreamResult result;

                result = new StreamResult(new File(argv[0]+"settings.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, XMLSettingsCreator.class.getSimpleName(), "File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            //return -1;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            //return -1;
        }
    }
}
