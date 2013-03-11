
import smoke11.pudparser.Tile;

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
    private int mouseLastX=0,mouseLastY=0;
    private boolean drawText = false;
    private boolean realTimeMoving = true;
    private boolean movingPanelInsteadCamera = true;  //if true panel will be moved insted drawing again (with diff camera offset) which should be faster but its buggy
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
                    int mouseX =e.getX();
                    int mouseY=e.getY();
                    if(movingPanelInsteadCamera)
                    {
                        int x = (int)panel.getLocation().getX();
                        int y = (int)panel.getLocation().getY();

                        panel.setLocation(x+mouseX-mouseLastX,y+mouseY-mouseLastY);
                        System.out.println("Panel position: "+(int)panel.getLocation().getX()+", "+(int)panel.getLocation().getY());
                    }
                    else
                    {
                        cameraOffsetX+=mouseX-mouseLastX;
                        cameraOffsetY+=mouseY-mouseLastY;
                        System.out.println("Camera Offset: "+cameraOffsetX+", "+cameraOffsetY);
                    }
                    mouseLastX=mouseX;
                    mouseLastY=mouseY;
                    makeRepaint();

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        this.repaint();
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
            for (x=0;x<mapTiles.length;x++)   //make this as list to draw
            {
                for(y=0;y<mapTiles[0].length;y++)
                {
                    if(unitTiles[x][y]!=null)
                        g2d.drawImage(unitSprites[unitTiles[x][y].ID], cameraOffsetX+x*32, cameraOffsetY+y*32, this);


                }
            }

        }
    }


    @Override
    public void buttonPressed(ToolboxEvents e) {
        drawText =!drawText;
        System.out.println("Drawing id info: "+drawText);
        super.repaint();
    }
}

