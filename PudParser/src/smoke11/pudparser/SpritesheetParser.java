package smoke11.pudparser;

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
    public static BufferedImage[] cutSpriteSheet(BufferedImage spritesheet,  Tile[][][] tiles)
    {
        BufferedImage[] sprites = new BufferedImage[tiles.length*tiles[0].length*tiles[0][0].length];
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
                            sprites[lastID]= toCompatibleImage(spritesheet.getSubimage(tiles[i1][i2][i3].OffsetX, tiles[i1][i2][i3].OffsetY, tiles[i1][i2][i3].SizeX, tiles[i1][i2][i3].SizeY));

                        }
                    }
                }

            }

        }
        return sprites;
    }
    public static BufferedImage[] cutSpriteSheet(BufferedImage[] spritesheets, Tile[][] tiles, String[] ifThisContainsUseImage, String[] ifThisContainsIgnore)
    {
        BufferedImage[] sprites = new BufferedImage[tiles.length*tiles[0].length];
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
                        for (int n=0;n<spritesheets.length;n++) //because there can be more words than spritesheets
                        {
                            if(tiles[i1][i2].Name.contains(ifThisContainsUseImage[n]))
                            {//using it for telling SpritesheetParser to take specific sprites from specific sheets. i.e. "Human" means take all tiles which hase human in name for this spritesheet (for this it will be all human buildings) Orc in ignore will mean that ignore all with Orc in name
                                if(ifThisContainsIgnore[n]=="")
                                    sprites[lastID]= toCompatibleImage(spritesheets[n].getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].SizeX, tiles[i1][i2].SizeY));
                                else //if there is more than one word to ignore
                                {
                                    int contains=0;
                                    String[] split = ifThisContainsIgnore[n].split(";");
                                    for (int z=0;z<split.length;z++)
                                    {
                                        if(tiles[i1][i2].Name.contains(split[z]))
                                            contains++;
                                    }
                                    if(contains==0)
                                        sprites[lastID]= toCompatibleImage(spritesheets[n].getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].SizeX, tiles[i1][i2].SizeY));

                                }
                            }
                        }

                    }
                }


            }

        }
        return sprites;
    }
    public static BufferedImage[] cutSpriteSheet(BufferedImage spritesheet1, BufferedImage spritesheet2, String ifThisContainsUseFirstImage, Tile[][] tiles)
    {
        BufferedImage[] sprites = new BufferedImage[tiles.length*tiles[0].length];
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
                            sprites[lastID]= toCompatibleImage(spritesheet1.getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].SizeX, tiles[i1][i2].SizeY));
                        else
                            sprites[lastID]= toCompatibleImage(spritesheet2.getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].SizeX, tiles[i1][i2].SizeY));
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
    public static BufferedImage[] cutSpriteSheet(BufferedImage spritesheet,  Tile[][] tiles)
    {
        BufferedImage[] sprites = new BufferedImage[tiles.length*tiles[0].length];
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
                            sprites[lastID]= toCompatibleImage(spritesheet.getSubimage(tiles[i1][i2].OffsetX, tiles[i1][i2].OffsetY, tiles[i1][i2].SizeX, tiles[i1][i2].SizeY));
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
    public static BufferedImage[] cutSpriteSheet(BufferedImage spritesheet, Tile[] tiles)
    {
        BufferedImage[] sprites = new BufferedImage[tiles.length];
        int i = 0;
         for (Tile tile : tiles)
         {
             sprites[i]= toCompatibleImage(spritesheet.getSubimage(tile.OffsetX, tile.OffsetY, tile.SizeX, tile.SizeY));
             i++;
         }
         return sprites;
    }
    private static BufferedImage toCompatibleImage(BufferedImage image) //http://stackoverflow.com/questions/196890/java2d-performance-issues
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

	/*
	 * if image is already compatible and optimized for current system
	 * settings, simply return it
	 */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }
}
