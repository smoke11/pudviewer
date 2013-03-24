package smoke11.pudparser.xml;

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
import smoke11.DebugView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
public class XML_Units_SettingsCreatorIter {  //'thankfully' each of spritesheet is in diffrent size and order, so yeah, writing it all down :|
    public static int main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("unitTilesString");
            doc.appendChild(rootElement);
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
            doc.createComment("================\r\n");
            doc.createComment("BUILDINGS");
            doc.createComment("================\r\n");

            //<editor-fold desc="Description">
            //0-human summer
            String[] prefix = new String[]{"Human", "Orc", "Misc"};
            String[][] names = new String[4][];
            names[0]=new String[]{"Keep","Castle","Town Hall","Farm","Scout Tower","Guard Tower", "Cannon Tower","Blacksmith",
                    "Elven Lumber Mill","Stables","Church","Mage Tower", "Shipyard", "Foundry", "Refinery", "Barracks", "Gnomish Inventor","Oil Well","Gryphon Aviary"
            };
            names [1] = new String[]{"Stronghold","Fortress","Great Hall","Pig Farm","Scout Tower","Guard Tower", "Cannon Tower","Blacksmith",
                    "Troll Lumber Mill","Ogre Mound","Altar of Storms","Temple of the Damned", "Shipyard", "Foundry", "Refinery", "Barracks", "Goblin Alchemist","Oil Well","Dragon Roost"
            };
            names [2] = new String[]{"Gold Mine","Oil Patch"//,"Human Start", "Orc Start"  //TODO: add start points
            };
            String[][] ids = new String[4][];
            ids[0]=new String[]{"58","5a","4a","3a","40","60","62","52","4c","42","3e","50","48","4e","54","3c","44","56","46"};
            ids[1]= new String[]{"59","5b","4b","3b","41","61","63","53","4d","43","3f","51","49","4f","55","3d","45","57","47"};
            ids[2]= new String[]{"5c","5d"//,"5e","5f
             };
            int[][] allOffsetX = new int[4][];
            int[][] allOffsetY = new int[4][];
            //human
            allOffsetX[0]=new int[]{2,135,265,398,464,398,464,4,104,205,307,408,4,104,205,307,407,531,521};
            allOffsetY[0]=new int[]{4,4,4,4,4,134,134,264,264,264,264,264,458,458,458,458,458,4,302};
            //orc
            allOffsetX[1]=new int[]{135,397,139,337,542,411,477,106,503,307,307,106,108,502,310,107,503,505,307 };
            allOffsetY[1]=new int[]{1,1,538,590,590,590,590,330,231,328,228,129,428,330,429,229,134,429,131};
            //misc
            allOffsetX[2]=new int[]{13,14,};
            allOffsetY[2]=new int[]{11, 204,};
            int[][] sizes = new int[4][];
            sizes[0] = new int[]{128,128,128,64,64,64,64,96,96,96,96,96,96,96,96,96,96,96,96};
            sizes[1] = new int[]{128,128,128,64,64,64,64,96,96,96,96,96,96,96,96,96,96,96,96};
            sizes[2] = new int[]{96,94};
            for (int i=0;i<sizes[1].length;i++)
            {
                allOffsetY[1][i]+=13; //because my spritesheet was moved and x,y is broken
                allOffsetX[1][i]+=3;
            }
            for (int i1=0;i1<3;i1++)//for each style of tiles
            {
                for(int i2=0;i2<names[i1].length;i2++)//for each unit
                {

                    tile = doc.createElement("Unit");
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
                    size = doc.createElement("SizeX");
                    size.appendChild(doc.createTextNode(String.valueOf(sizes[i1][i2])));
                    tile.appendChild(size);
                    size = doc.createElement("SizeY");
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


            //</editor-fold>
            doc.createComment("================\r\n");
            doc.createComment("UNITS");
            doc.createComment("================\r\n");
            int[][] sizesX = new int[4][];
            int[][] sizesY = new int[4][];
            names = new String[4][];
            names[0]=new String[]{"Footman","Peasant","Ballista","Knight","Archer","Mage","Paladin","Dwarves", //land
                    "Human Oil Tanker", "Human Transport", "Elven Destroyer", "Battleship","Gnomish Submarine", //water
                    "Gnomish Flying Machine","Gryphon Rider"                                                    //air
            };
            names[1] = new String[]{"Grunt","Peon","Catapult","Ogre","Axethrower","Death Knight","Ogre-Mage","Goblin Sapper",//land
                    "Orc Oil Tanker", "Orc Transport", "Troll Destroyer", "Juggernaught", "Giant Turtle",                   //water
                    "Goblin Zepplin","Dragon"                                                                                //air

            };
            ids = new String[4][];
            ids[0]=new String[]{"00","02","04","06","08","0a","0c","0e","1a","1c","1e","20","26","28","2a"};
            ids[1]= new String[]{"01","03","05","07","09","0b","0d","0f","1b","1d","1f","21","27","29","2b"};
            allOffsetX = new int[4][];
            allOffsetY = new int[4][];
            //human
            allOffsetX[0]=new int[]{22,16,3,24,6,22,24,12,19,4,4,19,9,11,5};
            allOffsetY[0]=new int[]{10,8,0,9,11,7,9,7,0,0,5,6,13,11,12};
            //orc
            allOffsetX[1]=new int[]{22,14,7,12,21,23,12,12,12,9,15,6,7,3,5};
            allOffsetY[1]=new int[]{8,2,4,8,9,8,8,8,4,1,7,6,6,2,5};

            sizes = new int[4][];
            sizesX[0]=new int[]{31,26,58,34,40,31,34,34,29,60,51,52,55,60,79};
            sizesY[0]=new int[]{40,23,59,64,47,51,64,38,72,72,83,82,58,64,74};
            sizesX[1]=new int[]{38,29,47,50,34,29,50,34,45,53,61,88,72,66,80};
            sizesY[1]=new int[]{43,32,58,46,46,59,46,38,65,69,80,87,66,72,79};

            for (int i1=0;i1<2;i1++)//for each style of tiles
            {
                for(int i2=0;i2<sizesX[0].length;i2++)//for each unit
                {

                    tile = doc.createElement("Unit");
                    rootElement.appendChild(tile);
                    //id
                    attr = doc.createAttribute("PudID");
                    attr.setValue(ids[i1][i2]);  //chars
                    tile.setAttributeNode(attr);
                    //name
                    name = doc.createElement("Name");
                    name.appendChild(doc.createTextNode(names[i1][i2]));
                    tile.appendChild(name);
                    //size
                    size = doc.createElement("SizeX");
                    size.appendChild(doc.createTextNode(String.valueOf(sizesX[i1][i2])));
                    tile.appendChild(size);
                    size = doc.createElement("SizeY");
                    size.appendChild(doc.createTextNode(String.valueOf(sizesY[i1][i2])));
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
            File f=new File(argv[0]);
            if(f.exists())
            {
                f.delete();
            }
            StreamResult result;

            result = new StreamResult(new File(argv[0]));

                transformer.transform(source, result);

            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, XML_Units_SettingsCreatorIter.class.getSimpleName(), "File saved!");
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

