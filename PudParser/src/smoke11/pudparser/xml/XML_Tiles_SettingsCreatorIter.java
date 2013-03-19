package smoke11.pudparser.xml;
/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XML_Tiles_SettingsCreatorIter {
    public static int main(String argv[]) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("TerrainTiles");

            Element tile;
            Attr attr;
            Element name, size, offsetx, offsety;
            String[][][] PudIDs = new String[3][][];  //for each typeoftiles(summer,winter,wasteland) for each tile, for each version
            String[] names;
            String sizeOfTile = "32";
            String[][][] OffsetsX  = new String[3][][];  //summer,winter,wasteland
            String[][][] OffsetsY = new String[3][][];  //summer,winter,wasteland

            PudIDs[0] = new String[][]{
              new String[]{"0010","0011","0012","0013"}
            };
            doc.appendChild(rootElement);
            doc.createComment("================\r\n");
            doc.createComment("SOLID TILES CREATED manually   http://cade.datamax.bg/war2x/pudspec.html - Appendix D: General map tiles\r\n");
            doc.createComment("================\r\n");

            //<editor-fold desc="Description">
            //LightWater0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0010");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light water0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("165"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //LightWater1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0011");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light water1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //LightWater1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0012");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light water2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //LightWater3
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0013");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light water3"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("231"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);


            //DarkWater0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0020");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark water0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("264"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);


            //DarkWater1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0021");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark water1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //DarkWater2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0022");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark water2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //DarkWater3
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0023");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark water3"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("330"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);


            //LightCoast0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0030");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light coast0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("363"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //LightCoast1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0031");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light coast1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("396"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);

            //LightCoast2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0032");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light coast2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("429"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("561"));
            tile.appendChild(offsety);


            //DarkCoast0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0040");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark coast0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("99"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);

            //DarkCoast1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0041");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark coast1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("132"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);

            //DarkCoast2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0042");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark coast2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("165"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);

            //LightGround0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0050");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light ground0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("462"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);

            //LightGround1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0051");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light ground1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("495"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);

            //LightGround2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0052");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid light ground2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("462"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("594"));
            tile.appendChild(offsety);


            //DarkGround0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0060");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark ground0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("99"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("627"));
            tile.appendChild(offsety);

            //DarkGround1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0061");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark ground1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("132"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("627"));
            tile.appendChild(offsety);

            //DarkGround2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0062");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid dark ground2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("99"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("627"));
            tile.appendChild(offsety);

            //Forest0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0070");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid forest0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("363"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsety);

            //Forest1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0071");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid forest1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("429"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsety);

            //Forest2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0072");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid forest2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("462"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsety);


            //Mountains0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0080");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid mountains0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("231"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsety);

            //Mountains1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0081");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid mountains1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsety);

            //Mountains2
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0082");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid mountains2"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("198"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsety);

            //Mountains3
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0083");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid mountains3"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("264"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("297"));
            tile.appendChild(offsety);

            //HumanWall0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("0090");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid human wall0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("528"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("0"));
            tile.appendChild(offsety);

            //HumanWall1
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("00b0");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid human wall1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("462"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("33"));
            tile.appendChild(offsety);


            //OrcWall0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("00a0");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid orc wall0"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("495"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("33"));
            tile.appendChild(offsety);

            //OrcWall0
            tile = doc.createElement("Tile");
            rootElement.appendChild(tile);
            //id
            attr = doc.createAttribute("PudID");
            attr.setValue("00C0");
            tile.setAttributeNode(attr);
            //name
            name = doc.createElement("Name");
            name.appendChild(doc.createTextNode("solid orc wall1"));
            tile.appendChild(name);
            //size
            size = doc.createElement("Size");
            size.appendChild(doc.createTextNode("32"));
            tile.appendChild(size);
            //offsetx
            offsetx = doc.createElement("OffsetX");
            offsetx.appendChild(doc.createTextNode("429"));
            tile.appendChild(offsetx);
            //offsety
            offsety = doc.createElement("OffsetY");
            offsety.appendChild(doc.createTextNode("66"));
            tile.appendChild(offsety);
            //</editor-fold>


            doc.createComment("================\r\n");
            doc.createComment("Boundry TILES CREATED WITH ITERATIONS    http://cade.datamax.bg/war2x/pudspec.html - Appendix D: General map tiles\r\n");
            doc.createComment("================\r\n");
            //<editor-fold desc="Description">
            //32 tiles for boundry, some of tiles have three versions(for example: 0122,0142,0192,01B2), rest has 2 versions
            String[] prefixes = new String[]{"filled", "clear"};
            //TODO: make accurate names
            String[] sufixes = new String[]{"upper left","upper right","upper half","lower left","left half","lower right","upper left, lower right"};
            String[] typesOfTiles = new String[]{"dark water and water","water and coast","dark coast and coast","mount and coast","coast and grass","dark grass and grass","forest and grass","human wall","orc wall"};
            ArrayList<int[]> numberOfVersionsList = new ArrayList<int[]>(); //foreach type of tile, there is other number of version for each prefix*sufix sorted by using order
            numberOfVersionsList.add(new int[]{2,2,3,2,3,2,1,2,2,3,1,3,1,1});  //dark water and water - working
            numberOfVersionsList.add(new int[]{2,2,3,2,3,1,2,2,1,3,2,3,2,2});  //water and coast  - working
            numberOfVersionsList.add(new int[]{1,2,3,2,3,2,1,1,2,3,1,3,1,1});  //dark coast and coast - working
            numberOfVersionsList.add(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});  //mount and coast - not working  added in next section
            numberOfVersionsList.add(new int[]{2,2,3,2,3,2,1,2,2,3,1,3,2,2});  //coast and grass    - working
            numberOfVersionsList.add(new int[]{2,2,3,2,3,2,2,2,2,3,2,3,2,2});  //dark grass and grass - working
            numberOfVersionsList.add(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});  //forest and grass  - not working
            numberOfVersionsList.add(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});  //human wall - not working
            numberOfVersionsList.add(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});  //orc wall  - not working

            int spriteDimensionX = 594; //last usable offset
            int spriteDimensionY = 627; //last usable offset
            int[] startPositionX = new int[]{495,528,297,297,132,330,231,561,528}; //start offset for first tile from type for x
            int[] startPositionY = new int[]{495,330,297,231,462,396,165,0,33}; //start offset for first tile from type for y
            int actualX, actualY;

            //writing data
            for (int n=0;n<typesOfTiles.length;n++) //for every type of tiles
            {
                if(typesOfTiles[n]=="mount and coast"
                        ||typesOfTiles[n]=="forest and grass"
                        ||typesOfTiles[n]=="human wall"
                        ||typesOfTiles[n]=="orc wall") //skip this step if its broken order type of tile
                    continue;
                actualX=startPositionX[n];
                actualY=startPositionY[n];
                for (int n2=0;n2<prefixes.length;n2++)  //first for filled then clear prefix
                {
                    for (int n3=0;n3<sufixes.length;n3++) //for every angle of tiles sufix
                    {
                        for (int i=0;i<numberOfVersionsList.get(n)[(sufixes.length*n2)+n3];i++) //for each version
                        {
                            tile = doc.createElement("Tile");
                            rootElement.appendChild(tile);
                            //id
                            attr = doc.createAttribute("PudID");
                            attr.setValue("0"+(n+1)+n3+i);
                            if(n2==1)
                            {
                                char c=(char)(97+(n3-3));
                                if(n3<3)
                                    attr.setValue("0"+(n+1)+(7+n3)+i);
                                else
                                    attr.setValue("0"+(n+1)+Character.toString(c)+i);  //chars

                            }
                            tile.setAttributeNode(attr);
                            //name
                            name = doc.createElement("Name");
                            name.appendChild(doc.createTextNode(typesOfTiles[n]+" "+prefixes[n2]+" "+sufixes[n3]));
                            tile.appendChild(name);
                            //size
                            size = doc.createElement("Size");
                            size.appendChild(doc.createTextNode("32"));
                            tile.appendChild(size);
                            //offsetx
                            if(actualX>spriteDimensionX)
                            {
                                actualX=0;
                                actualY+=33;
                            }
                            offsetx = doc.createElement("OffsetX");
                            offsetx.appendChild(doc.createTextNode(String.valueOf(actualX)));
                            tile.appendChild(offsetx);
                            actualX+=33;
                            //offsety
                            offsety = doc.createElement("OffsetY");
                            offsety.appendChild(doc.createTextNode(String.valueOf(actualY)));
                            tile.appendChild(offsety);
                        }

                    }

                }

            }
            //</editor-fold>

            doc.createComment("================\r\n");
            doc.createComment("rest of Boundry TILES CREATED with iterations - because of lack of order");     //TODO: someday make use of alternative version for tiles from this method
            doc.createComment("================\r\n");
            //<editor-fold desc="Description">
            //mount and coast and forest and grass
            int numberOfSprites=42;
            int[][] allOffsetX = new int[4][numberOfSprites];
            int[][] allOffsetY = new int[4][numberOfSprites];
            for(int n=0;n<4;n++)
            {
                if(n==0)
                {
                    actualX=startPositionX[3];
                    actualY=startPositionY[3];
                }
                else if(n==1)
                {
                    actualX=startPositionX[6];
                    actualY=startPositionY[6];
                }
                else if(n==2)
                {
                    actualX=startPositionX[7];
                    actualY=startPositionY[7];
                }
                else
                {
                    actualX=startPositionX[8];
                    actualY=startPositionY[8];
                }
                for (int i=0;i<numberOfSprites;i++)    //making array with all sprites postion
                {
                    if(actualX>spriteDimensionX)
                    {
                        actualX=0;
                        actualY+=33;
                    }
                   allOffsetX[n][i] = actualX;
                   allOffsetY[n][i] = actualY;
                    actualX+=33;
                }
                //list with spriteposition sorted by id
                int[][] sortedOffsetindex; //for now just 1 version for each type
                sortedOffsetindex = new int[][]{
                        {8,0,22,29,7,30,12,2,11,1,10,4,6,3}, //mount and coast
                        {8,0,22,5,7,36,9,2,11,1,10,4,6,3}, //forest and grass
                        {0,1,2,3,4,6,7,8,9,10,12,13,14,15}, //human wall
                        {0,1,2,3,4,6,7,8,9,10,12,13,14,15} //orc wall
                };
                 //next for each id take proper spriteposition
                for(int i=0;i<14;i++)
                {
                    for(int v=0;v<3;v++) //versions
                    {
                        char c=(char)(97+(i-10));

                        tile = doc.createElement("Tile");
                        rootElement.appendChild(tile);
                        //id
                        attr = doc.createAttribute("PudID");
                        int y=3+1;
                        if(n>0)
                            y=5+n+1;
                        if(i<10)
                            attr.setValue("0"+y+i+v);
                        else
                            attr.setValue("0"+y+Character.toString(c)+v);  //chars
                        tile.setAttributeNode(attr);
                        //name
                        name = doc.createElement("Name");
                        name.appendChild(doc.createTextNode(typesOfTiles[y-1]+" "+prefixes[i/7]+" "+sufixes[i%7]));
                        tile.appendChild(name);
                        //size
                        size = doc.createElement("Size");
                        size.appendChild(doc.createTextNode("32"));
                        tile.appendChild(size);
                        //offsetx
                        offsetx = doc.createElement("OffsetX");
                        offsetx.appendChild(doc.createTextNode(String.valueOf(allOffsetX[n][sortedOffsetindex[n][i]])));
                        tile.appendChild(offsetx);
                        //offsety
                        offsety = doc.createElement("OffsetY");
                        offsety.appendChild(doc.createTextNode(String.valueOf(allOffsetY[n][sortedOffsetindex[n][i]])));
                        tile.appendChild(offsety);
                    }
                }
            }
            //</editor-fold>


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

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");
            return 1;

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            return -1;
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
            return -1;
        }
    }
}
