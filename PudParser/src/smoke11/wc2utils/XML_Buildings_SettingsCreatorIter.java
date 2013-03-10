package smoke11.wc2utils;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 09.03.13
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
public class XML_Buildings_SettingsCreatorIter {  //'thankfully' each of spritesheet is in diffrent size and order, so yeah, writing it all down :|
    public static int main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("UnitTiles");
            doc.appendChild(rootElement);
            doc.createComment("================\r\n");
            doc.createComment("BUILDINGS");
            doc.createComment("================\r\n");
            Element tile;
            //id
            Attr attr;
            //name
            Element name;
            //size
            Element size;
            //offsetx
            Element offsetx;
            //offsety
            Element offsety;

            //0-human summer
            String[] prefix = new String[]{"Human", "Orc"};
            String[][] names = new String[4][];
            names[0]=new String[]{"Keep","Castle","Town Hall","Farm","Scout Tower","Guard Tower", "Cannon Tower","Blacksmith",
                    "Elven Lumber Mill","Stables","Church","Mage Tower", "Shipyard", "Foundry", "Refinery", "Barracks", "Gnomish Inventor","Oil Well","Gryphon Aviary"
            };
            names [1] = new String[]{"Stronghold","Fortress","Great Hall","Pig Farm","Scout Tower","Guard Tower", "Cannon Tower","Blacksmith",
                    "Troll Lumber Mill","Ogre Mound","Altar of Storms","Temple of the Damned", "Shipyard", "Foundry", "Refinery", "Barracks", "Goblin Alchemist","Oil Well","Dragon Roost"
            };
            String[][] ids = new String[4][];
            ids[0]=new String[]{"58","5a","4a","3a","40","60","62","52","4c","42","3e","50","48","4e","54","3c","44","56","46"};
            ids[1]= new String[]{"59","5b","4b","3b","41","61","63","53","4d","43","3f","51","49","4f","55","3d","45","57","47"};
            int[][] allOffsetX = new int[4][];
            int[][] allOffsetY = new int[4][];
            //human
            allOffsetX[0]=new int[]{2,135,265,398,464,398,464,4,104,205,307,408,4,104,205,307,407,531,521};
            allOffsetY[0]=new int[]{4,4,4,4,4,134,134,264,264,264,264,264,458,458,458,458,458,4,302};
            //orc
            allOffsetX[1]=new int[]{135,397,139,337,542,411,477,106,503,307,307,106,108,502,310,107,503,505,307 };
            allOffsetY[1]=new int[]{1,    1,538,590,590,590,590,330,231,328,228,129,428,330,429,229,134,429,131};
            int[][] sizes = new int[4][];
            sizes[0] = new int[]{128,128,128,64,64,64,64,96,96,96,96,96,96,96,96,96,96,96,96};
            sizes[1] = new int[]{128,128,128,64,64,64,64,96,96,96,96,96,96,96,96,96,96,96,96};
            for (int i=0;i<sizes[1].length;i++)
            {
                allOffsetY[1][i]+=13; //because my spritesheet was moved and x,y is broken
                allOffsetX[1][i]+=3;
            }
            for (int i1=0;i1<2;i1++)//for each style of tiles
            {
                for(int i2=0;i2<names[i1].length;i2++)//for each unit
                {

                    tile = doc.createElement("Building");
                    rootElement.appendChild(tile);
                    //id
                    attr = doc.createAttribute("PudID");
                    attr.setValue(ids[i1][i2]);  //chars
                    tile.setAttributeNode(attr);
                    //name
                    name = doc.createElement("Name");
                    name.appendChild(doc.createTextNode(prefix[i1]+" "+names[i1][i2]));
                    tile.appendChild(name);
                    //size
                    size = doc.createElement("Size");
                    size.appendChild(doc.createTextNode(String.valueOf(sizes[i1][i2])));
                    tile.appendChild(size);
                    //offsetx
                    offsetx = doc.createElement("OffsetX");
                    offsetx.appendChild(doc.createTextNode(String.valueOf(allOffsetX[i1][i2])));
                    tile.appendChild(offsetx);
                    //offsety
                    offsety = doc.createElement("OffsetY");
                    offsety.appendChild(doc.createTextNode(String.valueOf(allOffsetY[i1][i2])));
                    tile.appendChild(offsety);
                }
            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            String fulldir = XMLSettingsReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String dironly = fulldir.substring(0, fulldir.lastIndexOf("/") + 1);
            File f;
            if(fulldir.contains(".jar".toLowerCase()))
                f=new File(dironly+"unit_tiles.xml");
            else
                f=new File(fulldir+"unit_tiles.xml");
            if(f.exists())
            {
                f.delete();
            }
            StreamResult result;

            System.out.println(fulldir);
            System.out.println(dironly);
            if(fulldir.contains(".jar".toLowerCase()))  //to fix bug with name of jar when running from jar
                result = new StreamResult(new File(dironly+"unit_tiles.xml"));
            else
                result = new StreamResult(new File(fulldir+"unit_tiles.xml"));

                transformer.transform(source, result);

                System.out.println("File saved!");
            return 0;

        }
     catch (ParserConfigurationException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -1;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -2;
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return -3;
        }


    }
}

