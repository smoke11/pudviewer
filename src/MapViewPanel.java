
import smoke11.pudparser.Tile;
import sun.text.CodePointIterator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class MapViewPanel extends JPanel implements IToolboxListenerMapPanel {   //http://stackoverflow.com/questions/148478/java-2d-drawing-optimal-performance
    private BufferedImage[] terrainSprites;
    private BufferedImage[] unitSprites;
    private Tile[][] mapTiles;
    private Tile[][] unitTiles;
    private int cameraOffsetX=0,cameraOffsetY=0;
    private int mouseLastX=0,mouseLastY=0; //used for cameraoffset
    private int actualMouseX=0, actualMouseY=0;
    private boolean drawText = false;
    private boolean realTimeMoving = true;
    private boolean movingPanelInsteadCamera = true;  //if true panel will be moved instead drawing again (with diff camera offset) which should be faster but its buggy
    private boolean drawTileBoxAlways =false;//used for drawing tile size boxed which follows mouse cursor
    private boolean mouseClicked=false;
    private JPanel panel;   //for moving panel
    public MapViewPanel(Dimension d, boolean fastermovingcamera)
    {
        this.setPreferredSize(d);
        panel=this;
        movingPanelInsteadCamera=fastermovingcamera;

        this.setBackground(Color.orange);
        super.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(":"+getClass().getName()+"MOUSE_RELEASED_EVENT:");
                if(!realTimeMoving)
                {
                    cameraOffsetX+=e.getX()-mouseLastX;
                    cameraOffsetY+=e.getY()-mouseLastY;
                    System.out.println("Camera Offset: "+cameraOffsetX+", "+cameraOffsetY);
                    if(!drawTileBoxAlways) //if not drawing TileBox that means we need to specify when repaint, because when drawing TileBox its repaiting all the time
                        makeRepaint();
                }
                mouseClicked=false;
            }
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(":"+getClass().getName()+"MOUSE_PRESSED_EVENT:");
                    mouseLastX=e.getX();
                    mouseLastY=e.getY();
                mouseClicked=true;
                if(!drawTileBoxAlways) //draw tile box if clicking
                    repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //System.out.println(":"+getClass().getName()+"MOUSE_EXITED_EVENT:");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println(":"+getClass().getName()+"MOUSE_ENTER_EVENT:");
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(":"+getClass().getName()+"MOUSE_CLICK_EVENT:");

            }
        });
        //this.setOpaque(false);
        super.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(realTimeMoving)
                {
                    actualMouseX=e.getX();
                    actualMouseY=e.getY();
                    if(movingPanelInsteadCamera)
                    {
                        int x = (int)panel.getLocation().getX();
                        int y = (int)panel.getLocation().getY();

                        panel.setLocation(x+actualMouseX-mouseLastX,y+actualMouseY-mouseLastY);
                        System.out.println("Panel position: "+(int)panel.getLocation().getX()+", "+(int)panel.getLocation().getY());
                    }
                    else
                    {
                        cameraOffsetX+=actualMouseX-mouseLastX;
                        cameraOffsetY+=actualMouseY-mouseLastY;
                        System.out.println("Camera Offset: "+cameraOffsetX+", "+cameraOffsetY);
                    }
                    mouseLastX=actualMouseX;
                    mouseLastY=actualMouseY;
                    if(!drawTileBoxAlways) //if not drawing TileBox that means we need to specify when repaint, because when drawing TileBox its repaiting all the time
                        makeRepaint();

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                actualMouseX=e.getX();
                actualMouseY=e.getY();
                if(drawTileBoxAlways)
                    makeRepaint();
            }
        });

        if(!drawTileBoxAlways) //if not drawing TileBox that means we need to specify when repaint, because when drawing TileBox its repaiting all the time
            makeRepaint();
    }
    private void makeRepaint()
    {
        super.repaint();
    }
    public void setImages(BufferedImage[] unitsSprites, BufferedImage[] tileSprites)
    {
        terrainSprites =tileSprites;
        unitSprites=unitsSprites;
    }
    public void prepareTiles(Tile[][] MapTiles, Tile[][] UnitTiles)
    {

        mapTiles = MapTiles;
        unitTiles = UnitTiles;

    }
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);    // paints background
        if(mapTiles!=null)
        {

            Graphics2D g2d = (Graphics2D) g;

            int x=0,y=0;
            Font f = new Font("serif", Font.PLAIN, 10);
            g2d.setFont(f);
            for (x=0;x<mapTiles.length;x++)
            {
                for(y=0;y<mapTiles[0].length;y++)
                {
                    if(mapTiles[x][y]!=null)
                    {
                        g2d.drawImage(terrainSprites[mapTiles[x][y].ID], cameraOffsetX+x*32, cameraOffsetY+y*32, this);
                    }
                    else
                    {
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(cameraOffsetX+x*32, cameraOffsetY+y*32,32,32);
                        g2d.setColor(Color.CYAN);
                        g2d.drawString(mapTiles[x][y].PudID,cameraOffsetX+4+x*32,cameraOffsetY+10+y*32);
                    }

                }
            }
            for (x=0;x<mapTiles.length;x++)   //make this as list to draw
            {
                for(y=0;y<mapTiles[0].length;y++)
                {
                    if(unitTiles[x][y]!=null)
                    {
                        if(unitTiles[x][y].Name.contains("Human")||unitTiles[x][y].Name.contains("Orc")) //all building have race in name. if building draw normally
                            g2d.drawImage(unitSprites[unitTiles[x][y].ID], cameraOffsetX+x*32, cameraOffsetY+y*32, this);
                        else //if not building calculate real drawing x,y for unit and draw
                        {
                            Point truepos = calculateUnitDrawingPosition(x,y,1,unitSprites[unitTiles[x][y].ID]);
                            g2d.drawImage(unitSprites[unitTiles[x][y].ID], cameraOffsetX+truepos.x, cameraOffsetY+truepos.y, this);
                        }
                    }
                    if(drawTileBoxAlways)
                    {
                        int tilex = (actualMouseX-cameraOffsetX)/32;
                        int tiley = (actualMouseY-cameraOffsetY)/32;
                        g2d.setColor(Color.WHITE);
                        g2d.drawRect(cameraOffsetX+tilex*32,cameraOffsetY+tiley*32,32,32);
                    }
                    else if(mouseClicked)//draw tile box only if mouse was clicked
                    {
                        int tilex = (actualMouseX-cameraOffsetX)/32;
                        int tiley = (actualMouseY-cameraOffsetY)/32;
                        g2d.setColor(Color.WHITE);
                        g2d.drawRect(cameraOffsetX+tilex*32,cameraOffsetY+tiley*32,32,32);
                    }
                    if(drawText)
                    {
                        g2d.setColor(Color.CYAN);
                        g2d.drawString(mapTiles[x][y].PudID,cameraOffsetX+5+x*32,cameraOffsetY+10+y*32);
                        if(unitTiles[x][y]!=null)
                        {
                            g2d.setColor(Color.RED);
                            g2d.drawString(unitTiles[x][y].PudID,cameraOffsetX+5+x*32,cameraOffsetY+20+y*32);
                        }
                    }

                }
            }

        }
    }

    private Point calculateUnitDrawingPosition(int tileX, int tileY, int howManyTilesUse, BufferedImage unitSprite) //howManyTilesUse, for most of units its 1(1x1) (for air and water its 2x2), its how much space it can take
    {
         Point result = new Point(tileX*32,tileY*32);
        int spritesizex = result.x+unitSprite.getWidth();
        int tilesizex = result.x+howManyTilesUse*32;
         int spritesizey = result.y+unitSprite.getHeight();
         int tilesizey = result.y+howManyTilesUse*32;
         int newx=tileX*32,newy=tileY*32;
        if(spritesizex>tilesizex)//if sprite taking more space that tile can offer, move starting x
            newx=result.x-(spritesizex-tilesizex)/2;
         if(spritesizey>tilesizey)//if sprite taking more space that tile can offer, move starting y
            newy=result.y-(spritesizey-tilesizey);
         return new Point(newx,newy); //returning point is alredy multiply by 32
    }
    @Override
    public void showID(ToolboxEvents e) {
        drawText =!drawText;
        System.out.println("Drawing id info: "+drawText);
        if(!drawTileBoxAlways)   //if not drawing TileBox that means we need to specify when repaint, because when drawing TileBox its repaiting all the time
            makeRepaint();
    }

    @Override
    public void drawTilebox(ToolboxEvents e) {
       drawTileBoxAlways=!drawTileBoxAlways;
    }
}

