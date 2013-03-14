package smoke11.pudparser.xml;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import smoke11.pudparser.Tile;

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

           // System.out.println(fulldir);
           // System.out.println(dironly);
            String s = args[0];
            System.out.println(s);
            fXmlFile = new File(args[0]); //terrain_tiles

            //<editor-fold desc="Description">
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            SortedTerrainTiles = new Tile[10][14][16];
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("Tile");


            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String pudid = eElement.getAttribute("PudID");
                    System.out.println(pudid);
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    System.out.println("Name: " + name);
                    int size =  Integer.parseInt(eElement.getElementsByTagName("Size").item(0).getTextContent());
                    System.out.println("Size: " + size);
                    int offx =  Integer.parseInt(eElement.getElementsByTagName("OffsetX").item(0).getTextContent());
                    int offy =  Integer.parseInt(eElement.getElementsByTagName("OffsetY").item(0).getTextContent());
                    System.out.println("OffsetX: " + offx);
                    System.out.println("OffsetY: " + offy);
                    Tile tile = new Tile(temp,pudid,name,size,offx,offy);
                    int[] parsedpudid = parsePudid(pudid);
                    SortedTerrainTiles[parsedpudid[1]][parsedpudid[2]][parsedpudid[3]]=tile;
                    if(pudid.substring(3).equalsIgnoreCase("0"))
                    {
                        for (int i=0;i<16;i++)        //becuase, no matter how many there is versions of tile, there can be 16 diff versions by id, so its needed to make it, even if it use 1 sprite for all 16 versions
                        {
                            String newpudid=pudid.substring(0,2);
                            newpudid+=i;
                            tile = new Tile(temp,newpudid,name,size,offx,offy);
                            SortedTerrainTiles[parsedpudid[1]][parsedpudid[2]][i]=tile;
                        }
                    }

                }
            }
            //</editor-fold>
            s = args[1];
            System.out.println(s);
            fXmlFile = new File(args[1]); //unit_tiles

            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            UnitTiles = new Tile[7][16]; //from 0 to 6 and from 0 to f

            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            nList = doc.getElementsByTagName("Unit");


            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String pudid = eElement.getAttribute("PudID");
                    System.out.println(pudid);
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    System.out.println("Name: " + name);
                    int size=-1, sizeX=-1, sizeY=-1;
                    if((name.contains("Human")||name.contains("Orc")||name.contains("Gold Mine")||name.contains("Oil Patch"))&&(!name.contains("Oil Tanker")&&!name.contains("Transport")))   //TODO: make buildings have sizex and sizey so ignore this ifs
                    {
                        size =   Integer.parseInt(eElement.getElementsByTagName("Size").item(0).getTextContent());
                        System.out.println("Size: " + size);
                    }
                    else
                    {
                        sizeX =  Integer.parseInt(eElement.getElementsByTagName("SizeX").item(0).getTextContent());
                        System.out.println("SizeX: " + sizeX);
                        sizeY =  Integer.parseInt(eElement.getElementsByTagName("SizeY").item(0).getTextContent());
                        System.out.println("SizeX: " + sizeY);
                    }
                    int offx =  Integer.parseInt(eElement.getElementsByTagName("OffsetX").item(0).getTextContent());
                    int offy =  Integer.parseInt(eElement.getElementsByTagName("OffsetY").item(0).getTextContent());
                    System.out.println("OffsetX: " + offx);
                    System.out.println("OffsetY: " + offy);
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
