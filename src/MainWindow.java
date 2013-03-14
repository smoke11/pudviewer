/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */

import smoke11.pudparser.PudParser;
import smoke11.pudparser.SpritesheetParser;
import smoke11.pudparser.xml.XMLPudSettingsReader;
import smoke11.pudparser.xml.XML_Tiles_SettingsCreatorIter;
import smoke11.pudparser.xml.XML_Units_SettingsCreatorIter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class MainWindow implements IToolboxListenerMainWindow {

        private MapViewPanel mapViewPanel;
        private boolean firstTimeOpen = true;
        private boolean movingPanelInsteadCamera = false;  //if true panel will be moved insted drawing again (with diff camera offset) which should be faster but its buggy
        private String mainDir="C:\\Users\\nao\\Documents\\JavaProjects\\pudviewer\\datafiles\\";//"C:\\Documents and Settings\\nobody_traveler\\My Documents\\datafiles\\"; //use this to change path to files of this program
        private void createAndShowGUI() {
            if(XMLPudSettingsReader.class.getProtectionDomain().getCodeSource().getLocation().getPath().contains(".jar"))//if it is stand alone, make console window
            {
            //Create and set up the debug window.
            JTextArea ta = new JTextArea();
            ta.setSize(100,400);
            TextAreaOutputStream taos = new TextAreaOutputStream( ta, Integer.MAX_VALUE );
            PrintStream ps = new PrintStream( taos );
            System.setOut( ps );
            System.setErr( ps );
            JFrame frame2 = new JFrame ("Debug Console");
            frame2.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            JScrollPane jpane = new JScrollPane( ta );
            jpane.setSize(100,400);
            frame2.getContentPane().add ( jpane );
            frame2.setVisible (true);
            frame2.setSize(new Dimension(200, 400));
            frame2.setLocation(830,0);
            }

            //Create and set up the main window.
            JFrame frame = new JFrame("Warcraft 2 Map Viewer");
            Container contentPane = frame.getContentPane();
            if(movingPanelInsteadCamera)
                contentPane.setLayout(new FlowLayout());
            else
                contentPane.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(700,500));
            frame.pack();
            frame.setLocation(0,0); //napotrzeby dopasowawywania tilesow
            frame.setVisible(true);

            mapViewPanel = new MapViewPanel(frame.getSize(), movingPanelInsteadCamera);

            ToolboxPanel toolbox = new ToolboxPanel(new Dimension(frame.getSize().width,50));
            toolbox.addEventListener(mapViewPanel);
            toolbox.addEventListener(this);

            frame.getContentPane().add(toolbox,BorderLayout.PAGE_START);
            frame.getContentPane().add(mapViewPanel,BorderLayout.CENTER);

            //Display the window.
            //frame.setUndecorated(true);
            //frame.setOpacity(0.6f);

        }

        public void run(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                    File XMLfile = new File(mainDir+"settings.xml");
                    if(true)//!XMLfile.exists()) //TODO: when not needed debug, make checking if file exists
                    {
                        XMLSettingsCreator.main(new String[]{mainDir});
                    }
                    Dimension mapdim = loadMap();
                    mapViewPanel.setSize(mapdim);
                }
            });

        }
    private void readSettings()
    {
        FileOpenPanel f = new FileOpenPanel();
        File XMLfile = new File(mainDir+"settings.xml");
        if(!XMLfile.exists())
        {
            f.openXMLFile(mainDir);
            XMLfile = f.OpenedXMLFile;
        }
        if(XMLfile !=null)
        {
            File settingsxml, terraintiles, unittiles;
            settingsxml = XMLfile;
            XMLSettingsReader.main(new String[]{XMLfile.getPath()});
            unittiles = new File(XMLSettingsReader.Dirs[1]);
            terraintiles = new File(XMLSettingsReader.Dirs[2]);
            if(true)//!unittiles.exists())  TODO: For now making with each run new xml, for test. after this, removeit
            {
                int result = XML_Units_SettingsCreatorIter.main(new String[]{XMLSettingsReader.Dirs[1]});
            }
            if(!terraintiles.exists())
            {
                int result = XML_Tiles_SettingsCreatorIter.main(new String[]{XMLSettingsReader.Dirs[2]});
            }


            if(XMLPudSettingsReader.main(new String[]{XMLSettingsReader.Dirs[2], XMLSettingsReader.Dirs[1]})!=0)
                System.out.println("There is something wrong with XMLPudSettingsReader");

            try {
                BufferedImage[] spritesheets = new BufferedImage[]{
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/humanbuildingssummer.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/orcbuildingssummer.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/footman.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/grunt.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/peasant.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/peon.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/ballista.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/catapult.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/knight.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/ogre.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/archer.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/axethrower.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/mage.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/deathknight.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/knight.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/ogre.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/dwarves.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/goblins.png"))
                };
                String[] recogniseWith = new String[]{  //using it for telling SpritesheetParser to take specific sprites from specific sheets. i.e. "Human" means take all tiles which hase human in name for this spritesheet (for this it will be all human buildings
                        "Human",
                        "Orc",
                        "Footman",
                        "Grunt",
                        "Peasant",
                        "Peon",
                        "Ballista",
                        "Catapult",
                        "Knight",
                        "Ogre",
                        "Archer",
                        "Axethrower",
                        "Mage",
                        "Death Knight",
                        "Paladin",
                        "Ogre-Mage",
                        "Dwarves",
                        "Goblin Sapper"
                };
                String[] ignoreIfHave = new String[]{ //sometimes there is a need to ignore some word in name. i.e. there is orc unit - Ogre and there is building Orc Ogre Mound. so there its needed to ignore word Orc for unit because it will choose building or vice versa
                //if empty, ignore this
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Death", //Human unit Knight ignore Death Knight
                        "Orc",   // Orc unit Ogre ignore orc bulding
                        "",
                        "",
                        "Tower", //human unit mage ignore human building
                        "",
                        "",
                        "Orc",  // Orc unit Ogre ignore orc bulding
                        "",
                        "Alchemist" // orc unit goblin sapper ignore orc building
                };
                BufferedImage[] unitTiles = SpritesheetParser.cutSpriteSheet(spritesheets, XMLPudSettingsReader.UnitTiles, recogniseWith, ignoreIfHave); //=  SpritesheetParser.cutSpriteSheet(ImageIO.read(new File(resultdir + "sprites/human/humanbuildingssummer.png")), ImageIO.read(new File(resultdir + "sprites/orc/orcbuildingssummer.png")), "Human", XMLPudSettingsReader.unitTilesString);
                mapViewPanel.setImages(unitTiles,SpritesheetParser.cutSpriteSheet(ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "summertiles.png")), XMLPudSettingsReader.SortedTerrainTiles));
            }
            catch (IOException e) {
                System.out.println("Can`t read summertiles.png");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
    private Dimension loadMap()
    {
        FileOpenPanel f = new FileOpenPanel();

        if(firstTimeOpen)
            readSettings();

        f.openMapFile(XMLSettingsReader.Dirs[0]);
        if(f.OpenedMapFile !=null)
        {

            PudParser p;
            p = new PudParser();
            p.getMapDataFromFile(f.OpenedMapFile);
            p.prepareTiles(XMLPudSettingsReader.SortedTerrainTiles, XMLPudSettingsReader.UnitTiles);
            mapViewPanel.prepareTiles(p.mapTiles,p.unitTiles);
            firstTimeOpen=false;
            mapViewPanel.repaint();
            return new Dimension(p.dimX *32,p.dimY *32);

        }
        return null;
    }
    @Override
    public void loadMap(ToolboxEvents e) {
        loadMap();
    }

}

