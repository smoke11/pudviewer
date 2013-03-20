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
            new String[]{"0010","0011","0012","0013"}, new String[]{"0020","0021","0022","0023"},
            new String[]{"0030","0031","0032"},        new String[]{"0040","0041","0042"},
            new String[]{"0050","0051","0052"},        new String[]{"0060","0061","0062"},
            new String[]{"0070","0071","0072"},        new String[]{"0080","0081","0082","0083"},
            new String[]{"0090","00b0"},               new String[]{"00a0","00c0"},
            };
            names = new String[]{
                    "solid light water",                        "solid dark water",
                    "solid light coast",                        "solid dark coast",
                    "solid light ground",                       "solid dark ground",
                    "solid forest",                             "solid mountains",
                    "solid human wall",                         "solid orc wall",
            };
            OffsetsX[0] = new String[][]{
            new String[]{"165","198","198","231"},  new String[]{"264","297","297","330"},
            new String[]{"363","396","429"},        new String[]{"99","132","165"},
            new String[]{"462","495","462"},        new String[]{"99","132","99"},
            new String[]{"363","429","462"},        new String[]{"231","198","198","264"},
            new String[]{"528","462"},              new String[]{"495","429"}
            };
            OffsetsY[0] = new String[][]{
            new String[]{"561","561","561","561"}, new String[]{"561","561","561","561"},
            new String[]{"561","561","561"},       new String[]{"594","594","594"},
            new String[]{"594","594","594"},       new String[]{"627","627","627"},
            new String[]{"198","198","198"},       new String[]{"297","297","297","297"},
            new String[]{"0","33"},                new String[]{"33","66"},
            };
            doc.appendChild(rootElement);
            doc.createComment("================\r\n");
            doc.createComment("SOLID TILES CREATED with iterations   http://cade.datamax.bg/war2x/pudspec.html - Appendix D: General map tiles\r\n");
            doc.createComment("================\r\n");

            //<editor-fold desc="Description">

            for (int i1=0;i1<1;i1++) // for each typeoftiles(summer,winter,wasteland)
            {
                for (int i2=0;i2<names.length;i2++)   // for each tile, for each version
                {
                    for (int i3=0;i3<OffsetsX[i1][i2].length;i3++)
                    {
                        tile = doc.createElement("Tile");
                        rootElement.appendChild(tile);
                        //id
                        attr = doc.createAttribute("PudID");
                        attr.setValue(PudIDs[i1][i2][i3]);
                        tile.setAttributeNode(attr);
                        //name
                        name = doc.createElement("Name");
                        name.appendChild(doc.createTextNode(names[i2]));
                        tile.appendChild(name);
                        //size
                        size = doc.createElement("Size");
                        size.appendChild(doc.createTextNode(sizeOfTile));
                        tile.appendChild(size);
                        //offsetx
                        offsetx = doc.createElement("OffsetX");
                        offsetx.appendChild(doc.createTextNode(OffsetsX[i1][i2][i3]));
                        tile.appendChild(offsetx);
                        //offsety
                        offsety = doc.createElement("OffsetY");
                        offsety.appendChild(doc.createTextNode(OffsetsY[i1][i2][i3]));
                        tile.appendChild(offsety);
                        //</editor-fold>
                    }
                }
            }


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
