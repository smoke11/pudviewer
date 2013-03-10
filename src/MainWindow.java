/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */

import smoke11.wc2utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class MainWindow implements ToolboxListenerMainWindow{

        private MapViewPanel mapViewPanel;
        private boolean firstTimeOpen = true;
        private void createAndShowGUI() {
            if(XMLSettingsReader.class.getProtectionDomain().getCodeSource().getLocation().getPath().contains(".jar"))//if it is stand alone, make console window
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
            contentPane.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(800,600));
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

        public void Run(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                    initialize();
                }
            });

        }
    private void initialize()   //TODO: MAKE XML WITH SETTINGS
    {
        FileOpenPanel f = new FileOpenPanel();
        f.openFile();
        if(f.OpenedFile !=null)
        {
            File terraintiles, unittiles;
            PudParser p;
            p = new PudParser();
            p.getMapDataFromFile(f.OpenedFile);


            if(firstTimeOpen)
            {
                String fulldir = XMLSettingsReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String dironly = fulldir.substring(0, fulldir.lastIndexOf("/") + 1);
                String resultdir;
                if(fulldir.contains(".jar".toLowerCase()))  //to fix bug with name of jar when running from jar
                    resultdir = (dironly);
                else
                    resultdir = (fulldir);
                terraintiles = new File(resultdir+"terrain_tiles.xml");
                unittiles = new File(resultdir+"unit_tiles.xml");
                if(!terraintiles.exists())
                {
                    int result = XML_Tiles_SettingsCreatorIter.main(null);
                }
                if(true)//!unittiles.exists())  TODO: For now making with each run new xml, for test. after this, removeit
                {
                    int result = XML_Units_SettingsCreatorIter.main(null);
                }

                if(XMLSettingsReader.main(new String[]{resultdir+"terrain_tiles.xml",resultdir+"unit_tiles.xml"})!=0)
                    System.out.println("There is something wrong with XMLSettingsReader");

                try {
                    BufferedImage[] spritesheets = new BufferedImage[]{
                            ImageIO.read(new File(resultdir + "sprites/human/humanbuildingssummer.png")),
                            ImageIO.read(new File(resultdir + "sprites/orc/orcbuildingssummer.png")),
                            ImageIO.read(new File(resultdir + "sprites/human/footman.png")),
                            ImageIO.read(new File(resultdir + "sprites/orc/grunt.png"))
                    };
                    String[] recogniseWith = new String[]{  //using it for telling SpritesheetParser to take specific sprites from specific sheets. i.e. "Human" means take all tiles which hase human in name for this spritesheet (for this it will be all human buildings
                            "Human",
                            "Orc",
                            "Footman",
                            "Grunt"
                    };

                    Image[] unitTiles = SpritesheetParser.CutSpriteSheet(spritesheets,recogniseWith,XMLSettingsReader.UnitTiles); //=  SpritesheetParser.CutSpriteSheet(ImageIO.read(new File(resultdir + "sprites/human/humanbuildingssummer.png")), ImageIO.read(new File(resultdir + "sprites/orc/orcbuildingssummer.png")), "Human", XMLSettingsReader.UnitTiles);
                    mapViewPanel.setImages(unitTiles,SpritesheetParser.CutSpriteSheet(ImageIO.read(new File(resultdir+"sprites/summertiles.png")), XMLSettingsReader.SortedTerrainTiles));
                }
                catch (IOException e) {
                    System.out.println("Can`t read summertiles.png");
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

                mapViewPanel.prepareTiles(p.TerrainTiles,p.UnitTiles,XMLSettingsReader.SortedTerrainTiles, XMLSettingsReader.UnitTiles);
            }
            firstTimeOpen=false;
            mapViewPanel.repaint();




    }

    @Override
    public void loadMap(ToolboxEvents e) {
        initialize();
    }
}

