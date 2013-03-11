package smoke11.pudparser;
/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class Tile {
    public String PudID;
    public int ID;
    public String Name; //name of tile, with of this type
    public int Size; //size of rect
    public int OffsetX; //how get to this tile from spritesheet from left top point
    public int OffsetY; //how get to this tile from spritesheet from left top point
    public Tile(int id, String pudid, String name, int size, int offx, int offy)
    {
        ID=id;
        PudID = pudid;
        Name = name;
        Size = size;
        OffsetX=offx;
        OffsetY=offy;
    }
}
