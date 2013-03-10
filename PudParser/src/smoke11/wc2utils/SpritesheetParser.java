package smoke11.wc2utils;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 09.03.13
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class SpritesheetParser {
    public static Image[] CutSpriteSheet(BufferedImage spritesheet,  Tile[][][] tiles)
    {
        Image crop;
        Image[] sprites = new Image[tiles.length*tiles[0].length*tiles[0][0].length];
        int lastID=-1;
        for (int i1=0;i1<tiles.length;i1++)
        {
            for (int i2=0;i2<tiles[0].length;i2++)
            {
                for (int i3=0;i3<tiles[0][0].length;i3++)
                {
                    if(tiles[i1][i2][i3]!=null)
                    {
                        if(lastID!=tiles[i1][i2][i3].ID)
                        {
                            lastID=tiles[i1][i2][i3].ID;
                            sprites[lastID]=spritesheet.getSubimage(tiles[i1][i2][i3].OffsetX,tiles[i1][i2][i3].OffsetY, tiles[i1][i2][i3].Size,tiles[i1][i2][i3].Size);

                        }
                    }
                }

            }

        }
        return sprites;
    }
    public static Image[] CutSpriteSheet(BufferedImage spritesheet1, BufferedImage spritesheet2, String ifThisContainsUseFirstImage, Tile[][] tiles)
    {
        Image crop;
        Image[] sprites = new Image[tiles.length*tiles[0].length];
        int lastID=-1;
        for (int i1=0;i1<tiles.length;i1++)
        {
            for (int i2=0;i2<tiles[0].length;i2++)
            {
                if(tiles[i1][i2]!=null)
                {
                    if(lastID!=tiles[i1][i2].ID)
                    {
                        lastID=tiles[i1][i2].ID;
                        if(tiles[i1][i2].Name.contains(ifThisContainsUseFirstImage))
                            sprites[lastID]=spritesheet1.getSubimage(tiles[i1][i2].OffsetX,tiles[i1][i2].OffsetY, tiles[i1][i2].Size,tiles[i1][i2].Size);
                        else
                            sprites[lastID]=spritesheet2.getSubimage(tiles[i1][i2].OffsetX,tiles[i1][i2].OffsetY, tiles[i1][i2].Size,tiles[i1][i2].Size);
/*                            File outputfile = new File(SpritesheetParser.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"saved.png");
                            try {
                                ImageIO.write(spritesheet.getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].Size, tiles[i1][i2].Size), "png", outputfile);
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }*/

                    }
                }


            }

        }
        return sprites;
    }
    public static Image[] CutSpriteSheet(BufferedImage spritesheet,  Tile[][] tiles)
    {
        Image crop;
        Image[] sprites = new Image[tiles.length*tiles[0].length];
        int lastID=-1;
        for (int i1=0;i1<tiles.length;i1++)
        {
            for (int i2=0;i2<tiles[0].length;i2++)
            {
                    if(tiles[i1][i2]!=null)
                    {
                        if(lastID!=tiles[i1][i2].ID)
                        {
                            lastID=tiles[i1][i2].ID;
                            sprites[lastID]=spritesheet.getSubimage(tiles[i1][i2].OffsetX,tiles[i1][i2].OffsetY, tiles[i1][i2].Size,tiles[i1][i2].Size);
/*                            File outputfile = new File(SpritesheetParser.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"saved.png");
                            try {
                                ImageIO.write(spritesheet.getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].Size, tiles[i1][i2].Size), "png", outputfile);
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }*/

                        }
                    }


            }

        }
        return sprites;
    }
    public static Image[] CutSpriteSheet(BufferedImage spritesheet,  Tile[] tiles)
    {
        Image crop;
        Image[] sprites = new Image[tiles.length];
        int i = 0;
         for (Tile tile : tiles)
         {
             sprites[i]=spritesheet.getSubimage(tile.OffsetX,tile.OffsetY, tile.Size,tile.Size);
             i++;
         }
         return sprites;
    }
}
