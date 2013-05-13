/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import smoke11.DebugView;
import smoke11.wc2utils.FileOpenPanel;
import smoke11.wc2utils.PudParser;
import smoke11.wc2utils.xml.XMLPudSettingsReader;
import smoke11.wc2utils.xml.XML_StaticUnits_SettingsCreatorIter;
import smoke11.wc2utils.xml.XML_Tiles_SettingsCreatorIter;


public class MainWindow implements IToolboxListenerMainWindow {

        private MapViewPanel mapViewPanel;
        private boolean firstTimeOpen = true;
        private String mainDir="D:\\datafiles\\"; //use this to change path to files of this program
        private void createAndShowGUI() {
            DebugView.setDebugLevel(DebugView.DEBUGLVL_MOREINFO);
            if(XMLPudSettingsReader.class.getProtectionDomain().getCodeSource().getLocation().getPath().contains(".jar"))//if it is stand alone, make console window
            {
                DebugView.createWindow(830, 0, 200, 400, DebugView.DEBUGLVL_MOREINFO);
            }

            //Create and set up the main window.
            JFrame frame = new JFrame("Warcraft 2 Map Viewer");
            Container contentPane = frame.getContentPane();
            contentPane.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(700,500));
            frame.pack();
            frame.setLocation(0,0); //napotrzeby dopasowawywania tilesow
            frame.setVisible(true);

            mapViewPanel = new MapViewPanel(frame.getSize());

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
                    if(mainDir=="")
                    {
                        FileOpenPanel f = new FileOpenPanel();
                        mainDir = f.getPathToJar();

                    }
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
            File terraintiles, unittiles;
            XMLSettingsReader.main(new String[]{XMLfile.getPath()});
            unittiles = new File(XMLSettingsReader.Dirs[1]);
            terraintiles = new File(XMLSettingsReader.Dirs[2]);
            if(true)//!unittiles.exists())  TODO: For now making with each run new xml, for test. after this, removeit
            {
                int result = XML_StaticUnits_SettingsCreatorIter.main(new String[]{XMLSettingsReader.Dirs[1]});
            }
            if(true)//!terraintiles.exists()) TODO: For now making with each run new xml, for test. after this, removeit
            {
                int result = XML_Tiles_SettingsCreatorIter.main(new String[]{XMLSettingsReader.Dirs[2]});
            }
            XMLPudSettingsReader.main(new String[]{XMLSettingsReader.Dirs[2], XMLSettingsReader.Dirs[1]});


            try {
                BufferedImage[] spritesheets = new BufferedImage[]{
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/humanbuildingssummer.png")), //buildings
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/orcbuildingssummer.png")),     //buildings
                        //units
                            //land
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
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/goblins.png")),
                            //water
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/humanoiltanker.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/orcoiltanker.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/humantransport.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/orctransport.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/elvendestroyer.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/trolldestroyer.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/battleship.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/juggernaught.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/gnomishsubmarine.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/giantturtle.png")),
                            //air
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/gnomishflyingmachine.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/goblinzeppelin.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "human/gryphonrider.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "orc/dragon.png")),
                        //misc
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "misc.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "misc.png")),
                        ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "critters.png"))


                };
                String[] recogniseWith = new String[]{  //using it for telling SpritesheetParser to take specific sprites from specific sheets. i.e. "Human" means take all tiles which hase human in name for this spritesheet (for this it will be all human buildings
                        "Human",  //buildings
                        "Orc",   //buildings
                        //units
                            //land
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
                        "Goblin Sapper",
                            //water
                        "Human Oil Tanker",
                        "Orc Oil Tanker",
                        "Human Transport",
                        "Orc Transport",
                        "Elven Destroyer",
                        "Troll Destroyer",
                        "Battleship",
                        "Juggernaught",
                        "Gnomish Submarine",
                        "Giant Turtle",
                            //air
                        "Gnomish Flying Machine",
                        "Goblin Zepplin",
                        "Gryphon Rider",
                        "Dragon",
                        //misc
                        "Gold Mine",
                        "Oil Patch",
                        "Critter"
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
                        "Alchemist", // orc unit goblin sapper ignore orc building
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Roost", //orc unit dragon ignore orc building
                        "",
                        "Tanker;Well" //misc building oil patch ignore orc and human units
                };
                BufferedImage[] unitTiles = SpritesheetParser.cutSpriteSheet(spritesheets, XMLPudSettingsReader.UnitTiles, recogniseWith, ignoreIfHave); //=  SpritesheetParser.cutSpriteSheet(ImageIO.read(new File(resultdir + "sprites/human/humanbuildingssummer.png")), ImageIO.read(new File(resultdir + "sprites/orc/orcbuildingssummer.png")), "Human", XMLPudSettingsReader.unitTilesString);
                mapViewPanel.setImages(unitTiles,SpritesheetParser.cutSpriteSheet(ImageIO.read(new File(XMLSettingsReader.Dirs[3] + "summertiles.png")), XMLPudSettingsReader.SortedTerrainTiles));
            }
            catch (IOException e) {
                DebugView.writeDebug(DebugView.DEBUGLVL_ERRORS, this.getClass().getName(), "Dirs: ");
                for (int i=0; i<XMLSettingsReader.Dirs.length;i++)
                    DebugView.writeDebug(DebugView.DEBUGLVL_ERRORS, this.getClass().getName(), (i + 1) + ". " + XMLSettingsReader.Dirs[i]);
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

