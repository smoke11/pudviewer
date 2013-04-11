package smoke11.pudparser.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import smoke11.DebugView;
import smoke11.pudparser.Tile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class XMLPudSettingsReader {
    public static Tile[][][] SortedTerrainTiles;
    public static Tile[][] UnitTiles;
    public static int main(String[] args){ //public static void readXMLSettings() {

        try {
            File fXmlFile;

            String s = args[0];
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, XMLPudSettingsReader.class.getSimpleName(), "Reading file: "+s);
            fXmlFile = new File(args[0]); //terrain_tiles

            //<editor-fold desc="Description">
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            SortedTerrainTiles = new Tile[10][14][16];
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("Tile");


            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Current Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String pudid = eElement.getAttribute("PudID");
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),pudid);
                    if(pudid.length()<3)
                        DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Problem with XMLPudSettingsReader: PUDid too short");
                    String name;
                    name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Name: " + name);
                    int size =  Integer.parseInt(eElement.getElementsByTagName("Size").item(0).getTextContent());
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Size: " + size);
                    int offx =  Integer.parseInt(eElement.getElementsByTagName("OffsetX").item(0).getTextContent());
                    int offy =  Integer.parseInt(eElement.getElementsByTagName("OffsetY").item(0).getTextContent());
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"OffsetX: " + offx);
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"OffsetY: " + offy);
                    Tile tile = new Tile(temp,pudid,name,size,offx,offy);
                    int[] parsedpudid = parsePudid(pudid);
                    SortedTerrainTiles[parsedpudid[1]][parsedpudid[2]][parsedpudid[3]]=tile;
                    if(pudid.substring(3).equalsIgnoreCase("0"))
                    {
                        for (int i=0;i<16;i++)        //becuase, no matter how many there is versions of tile, there can be 16 diff versions by id, so its needed to make it, even if it use 1 sprite for all 16 versions
                        {
                            String newpudid=pudid.substring(0, 3);
                            if(i<10)
                                newpudid+=String.valueOf(i);
                            else
                                newpudid+=String.valueOf((char)('a'+(i-10)));
                            tile = new Tile(temp,newpudid,name,size,offx,offy);
                            SortedTerrainTiles[parsedpudid[1]][parsedpudid[2]][i]=tile;
                        }
                    }

                }
            }
            //</editor-fold>
            s = args[1];
            DebugView.writeDebug(DebugView.DEBUGLVL_LESSINFO, XMLPudSettingsReader.class.getSimpleName(),s);
            fXmlFile = new File(args[1]); //unit_tiles

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            UnitTiles = new Tile[7][16]; //from 0 to 6 and from 0 to f

            doc.getDocumentElement().normalize();
            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Root element :" + doc.getDocumentElement().getNodeName());
            nList = doc.getElementsByTagName("Unit");

            DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(), "----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Current Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String pudid = eElement.getAttribute("PudID");
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),pudid);
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"Name: " + name);
                    int size=-1, sizeX, sizeY;
                    sizeX =  Integer.parseInt(eElement.getElementsByTagName("SizeX").item(0).getTextContent());
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"SizeX: " + sizeX);
                    sizeY =  Integer.parseInt(eElement.getElementsByTagName("SizeY").item(0).getTextContent());
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"SizeX: " + sizeY);
                    int offx =  Integer.parseInt(eElement.getElementsByTagName("OffsetX").item(0).getTextContent());
                    int offy =  Integer.parseInt(eElement.getElementsByTagName("OffsetY").item(0).getTextContent());
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"OffsetX: " + offx);
                    DebugView.writeDebug(DebugView.DEBUGLVL_MOREINFO, XMLPudSettingsReader.class.getSimpleName(),"OffsetY: " + offy);
                    Tile tile;
                    if(size==-1)
                        tile = new Tile(temp,pudid,name,sizeX,sizeY,offx,offy);
                    else
                        tile = new Tile(temp,pudid,name,size,offx,offy);
                    int[] parsedpudid = parsePudid(pudid);
                    UnitTiles[parsedpudid[0]][parsedpudid[1]]=tile;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }
    private static int[] parsePudid(String pudid)
    {
        int[] result = new int[pudid.length()];
        for (int i=0; i<pudid.length();i++)
        {
            char c = pudid.charAt(i);
            c=Character.toLowerCase(c);
            if(c>='a')
            {
                result[i]=Integer.parseInt(String.valueOf(10+(c-'a')));
            }
            else
                result[i]=Integer.parseInt(String.valueOf(c));
        }
        return result;
    }
}
