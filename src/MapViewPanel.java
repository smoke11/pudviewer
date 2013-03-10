import smoke11.wc2utils.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class MapViewPanel extends JPanel implements ToolboxListenerMapPanel {
    private Image[] terrainSprites;
    private Image[] unitSprites;
    private Tile[][] mapTiles;
    private Tile[][] unitTiles;
    private int cameraOffsetX=0,cameraOffsetY=0;
    private int mouseLastX=0,mouseLastY=0;
    public boolean drawText = false;
    public boolean realTimeMoving = true;
    private boolean mouseClicked=false;
    public MapViewPanel(Dimension d)
    {
        this.setPreferredSize(d);
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
                    cameraOffsetX+=e.getX()-mouseLastX;
                    cameraOffsetY+=e.getY()-mouseLastY;
                    System.out.println("Camera Offset: "+cameraOffsetX+", "+cameraOffsetY);
                    makeRepaint();
                    mouseLastX=e.getX();
                    mouseLastY=e.getY();
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
    public void setImages(Image[] unitsSprites, Image[] tileSprites)
    {
        terrainSprites =tileSprites;
        unitSprites=unitsSprites;
    }
    public void prepareTiles(byte[][][] MapDataTiles, String[] UnitData, Tile[][][] terrainTilesInfo, Tile[][] unitTilesInfo) //TODO: this logic should be in pudparser
    {
        String s1="", s2="";
        int x=0,y=0;
        Integer i=0;
        mapTiles = new Tile[MapDataTiles.length][MapDataTiles[0].length];
        unitTiles = new Tile[MapDataTiles.length][MapDataTiles[0].length];
        //terrain
        for (x=0;x<MapDataTiles.length;x++)
        {
            for(y=0;y<MapDataTiles[0].length;y++)
            {
                i=0;
                s1=String.format("%02X", MapDataTiles[x][y][1]);
                s2=String.format("%02X", MapDataTiles[x][y][0]);
                int i2,i3,i4;
                i2=Integer.parseInt(s1.substring(1));
                char c = Character.toLowerCase(s2.charAt(0));
                if(c>='a')
                    i3=(10+(c-'a'));
                else
                    i3=Integer.parseInt(s2.substring(0,1));
                c = Character.toLowerCase(s2.charAt(1));
                if(c>='a')
                    i4=(10+(c-'a'));
                else
                    i4=Integer.parseInt(s2.substring(1));
                mapTiles[x][y]=terrainTilesInfo[i2][i3][i4];

            }
        }
        //buildings
        for (String unit : UnitData)
        {
            String[] info = unit.split(";");
            int coorX =Integer.parseInt(info[0])/10; //because second byte adds 0
            int coorY =Integer.parseInt(info[1])/10;
            String ID = info[2];
            int i1,i2;
            char c = Character.toLowerCase(ID.charAt(0));
            if(c>='a')
                i1=(10+(c-'a'));
            else
                i1=Integer.parseInt(ID.substring(0,1));
            c = Character.toLowerCase(ID.charAt(1));
            if(c>='a')
                i2=(10+(c-'a'));
            else
                i2=Integer.parseInt(ID.substring(1,2));
            unitTiles[coorX][coorY]=unitTilesInfo[i1][i2];
        }

    }
    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);    // paints background
        if(mapTiles!=null)
        {
            int x=0,y=0;
            Font f = new Font("serif", Font.PLAIN, 10);
            g.setFont(f);
            for (x=0;x<mapTiles.length;x++)
            {
                for(y=0;y<mapTiles[0].length;y++)
                {
                    if(mapTiles[x][y]!=null)
                    {
                        g.drawImage(terrainSprites[mapTiles[x][y].ID], cameraOffsetX+x*32, cameraOffsetY+y*32, this);
                    }
                    else
                    {
                            g.setColor(Color.BLACK);
                            g.fillRect(cameraOffsetX+x*32, cameraOffsetY+y*32,32,32);
                            g.setColor(Color.CYAN);
                            g.drawString(mapTiles[x][y].PudID,cameraOffsetX+4+x*32,cameraOffsetY+10+y*32);
                    }
                    if(drawText)
                    {
                        g.setColor(Color.CYAN);
                        g.drawString(mapTiles[x][y].PudID,cameraOffsetX+5+x*32,cameraOffsetY+10+y*32);
                        if(unitTiles[x][y]!=null)
                        {
                            g.setColor(Color.RED);
                            g.drawString(unitTiles[x][y].PudID,cameraOffsetX+5+x*32,cameraOffsetY+20+y*32);
                        }
                    }
                }
            }
            for (x=0;x<mapTiles.length;x++)   //make this as list to draw
            {
                for(y=0;y<mapTiles[0].length;y++)
                {
                    if(unitTiles[x][y]!=null)
                        g.drawImage(unitSprites[unitTiles[x][y].ID], cameraOffsetX+x*32, cameraOffsetY+y*32, this);


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

