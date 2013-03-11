package smoke11.wc2utils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: nao
 * Date: 07.03.13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class PudParser {
    //info data
    public String pudTitle, pudDesc, terrainType;
    public int dimX, dimY, numberofUnitsOnMap;
    public boolean customUnitData, customUpgradeData;
    int numberOfBytes = 0, read=0;
    //map data
    private byte[][][] mapDataTiles; //x,y,word
    private String[] unitTilesString; //indexes - number of unit, values separated by ';': x;y;unittype;owner;word-if gold mine or oil well, contains 2500 * this otherwise 0 passive 1 active
    public Tile[][] mapTiles;
    public Tile[][] unitTiles;
    public void getMapDataFromFile(File file)
    {
        FileInputStream fin = null;
        if(dimX ==0|| dimY ==0)
            getInfoFromFile(file);
        try {
            fin = new FileInputStream(file);
            DataInputStream din = new DataInputStream(fin);
            byte[] _actualtitle = new byte[4];     //name of actual section
            byte[] _actuallength = new byte[4];    // length of actual section
            byte[][][] _maptiles = new byte[dimX][dimY][2];
            boolean readmaptiles=false, readunitdata=false;
            String actualTitleText ="";
            long actuallength=0;
            while(!readmaptiles||!readunitdata)
            {
                readBytes(4, _actualtitle, din);
                readBytes(4, _actuallength, din);
                actuallength=pack(_actuallength[3],_actuallength[2],_actuallength[1],_actuallength[0]);
                actualTitleText=new String(_actualtitle,"UTF-8");
                if(actualTitleText.equalsIgnoreCase("MTXM"))    //read Section 'MTXM', tiles map
                {
                    readBytes(dimX, dimY,2,_maptiles, din);
                    mapDataTiles = _maptiles;
                    readmaptiles=true;
                }
                else if(actualTitleText.equalsIgnoreCase("UNIT"))  //read UNIT section
                {
                    unitTilesString = new String [numberofUnitsOnMap];
                     byte[] unitdata;
                     int[] numberofbytesforinfo=new int[]{2,2,1,1,2};
                     int count=0;
                     for (int i=0;i<numberofUnitsOnMap;i++)
                     {
                         unitdata = new byte[(2+2+1+1+2)];
                         readBytes((2+2+1+1+2),unitdata,din);
                         String s = new String();
                         count=0;
                         for(int z : numberofbytesforinfo)
                         {
                             for (int n=count;n<count+z;n++)
                             {
                                 if(count>3)
                                    s+=String.format("%02X", unitdata[n]);
                                 else     //no need to convert coordinates (x,y) to hex
                                 {
                                     int v=unitdata[n];
                                     s+=String.valueOf(v);
                                 }
                             }
                             s+=";";
                             count+=z;
                         }
                         unitTilesString[i]=s;
                     }
                    readunitdata=true;
                }
                else //skip rest of data
                {
                    din.skipBytes((int)actuallength);
                    numberOfBytes+=(int)actuallength;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public void getInfoFromFile(File file)
    {
        try {
            pudTitle = file.getName().substring(0,file.getName().indexOf('.'));
            FileInputStream fin = new FileInputStream(file);
            DataInputStream din = new DataInputStream(fin);
            byte[] _actualtitle = new byte[4];     //name of actual section
            byte[] _actuallength = new byte[4];                // length of actual section
            byte[] _desc = new byte[32], _dim = new byte[4], _terraintype = new byte[2], _customunit = new byte[2], _customupgrade = new byte[2];      //info data stored in bytearrays
            boolean readDesc=false, readDim=false, readTerrainType=false, readCustomUnitData=false, readCustomUpgradeData=false, readUnitData=false;
            String actualTitleText ="";
            long actuallength=0;
            while(!readDesc||!readDim||!readTerrainType||!readCustomUnitData||!readCustomUpgradeData||!readUnitData)
            {
             /*    http://cade.datamax.bg/war2x/pudspec.html
The pud format consist of many sections, all sections start the same way:
        4 char          name of section
        long            length of the data (excluding header info)
        so its 8 bytes of skip
            */
                readBytes(4, _actualtitle, din);
                readBytes(4, _actuallength, din);
                actuallength=pack(_actuallength[3],_actuallength[2],_actuallength[1],_actuallength[0]);
                actualTitleText=new String(_actualtitle,"UTF-8");

                if(actualTitleText.equalsIgnoreCase("DESC"))  //read DESC section
                {
                    readBytes(32,_desc, din);
                    pudDesc =new String(_desc,"UTF-8");
                    readDesc=true;
                }
                else if(actualTitleText.equalsIgnoreCase("DIM "))  //read DIM section
                {
                    readBytes(4,_dim, din);
                    dimX =Math.abs(_dim[0]);
                    dimY =Math.abs(_dim[2]);
                    readDim=true;

                }
                else if(actualTitleText.equalsIgnoreCase("ERA ")||actualTitleText.equalsIgnoreCase("ERAX")) //read ERA / ERAX section
                {
                    readBytes(2,_terraintype, din);
                    switch (_terraintype[0])
                    {
                        case 0:
                            terrainType ="Forest";
                            break;
                        case 1:
                            terrainType ="Winter";
                            break;
                        case 2:
                            terrainType ="Wasteland";
                            break;
                        case 3:
                            terrainType ="Swamp";
                            break;
                        default:
                            terrainType ="Forest";
                            break;

                    }
                    readTerrainType=true;
                }
                else if(actualTitleText.equalsIgnoreCase("UDTA"))    //read UDTA section
                {
                    readBytes(2,_customunit, din);
                    customUnitData =convertIntToBoolean(_customunit[0]);
                    readCustomUnitData=true;
                    //skip rest of section
                    din.skipBytes((int)actuallength-2);//skip rest of section
                    numberOfBytes+=(int)actuallength;
                }
                else if(actualTitleText.equalsIgnoreCase("UGRD"))    //read UGRD section
                {
                    readBytes(2,_customupgrade, din);
                    customUpgradeData =convertIntToBoolean(_customupgrade[0]);
                    readCustomUpgradeData=true;
                    //skip rest of section
                    din.skipBytes((int)actuallength-2);
                    numberOfBytes+=(int)actuallength;
                }
                else if(actualTitleText.equalsIgnoreCase("UNIT"))  //read UNIT section
                {
                    numberofUnitsOnMap = (int)(actuallength/(2+2+1+1+2));
                    readUnitData=true;
                }
                else //skip rest of data
                {
                    din.skipBytes((int)actuallength);
                    numberOfBytes+=(int)actuallength;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public void prepareTiles(Tile[][][] terrainTilesInfo, Tile[][] unitTilesInfo)
    {
        String s1="", s2="";
        int x=0,y=0;
        Integer i=0;
        mapTiles = new Tile[mapDataTiles.length][mapDataTiles[0].length];
        unitTiles = new Tile[mapDataTiles.length][mapDataTiles[0].length];
        //terrain
        for (x=0;x< mapDataTiles.length;x++)
        {
            for(y=0;y< mapDataTiles[0].length;y++)
            {
                i=0;
                s1=String.format("%02X", mapDataTiles[x][y][1]);
                s2=String.format("%02X", mapDataTiles[x][y][0]);
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
        for (String unit : unitTilesString)
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
    public void readBytes(int numOfBytes, byte[] destinationArray, DataInputStream din)
    {
        int read=numOfBytes;
        try {
            din.read(destinationArray,0,read);
            numberOfBytes+=read;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public void readBytes(int numOfBytesX, int numOfBytesY, byte[][] destinationArray, DataInputStream din)
    {
        int read=numOfBytesX*numOfBytesY, x=0;
        try {
            for (x=0;x<numOfBytesX;x++)
                din.read(destinationArray[x],0,numOfBytesY);
            numberOfBytes+=read;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public void readBytes(int numOfBytesX, int numOfBytesY, int numOfBytesZ, byte[][][] destinationArray, DataInputStream din)
    {
        int read=numOfBytesX*numOfBytesY*numOfBytesZ, x=0,y=0;
        try {
            for (x=0;x<numOfBytesX;x++)
                for (y=0;y<numOfBytesY;y++)
                 din.read(destinationArray[y][x],0,numOfBytesZ); //for some reason switching index (x with y) repairs problem
            numberOfBytes+=read;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public static long pack(int c1, int c2, int c3, int c4)
    {
        return ((0xFFL & c1) << 24) | ((0xFFL & c2) << 16) | ((0xFFL & c3) << 8) | (0xFFL & c4);
    }
    public boolean convertIntToBoolean(int intValue)
    {
        return (intValue != 0);
    }

}
