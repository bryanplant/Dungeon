package com.plant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGen {
    private Tile tile[][];
    private int width;
    private int height;

    Random rand = new Random();

    public void newMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        tile = new Tile[width][height];
        for(int i = 0; i < width; i ++)
        {
            for(int j = 0; j < height; j++)
            {
                tile[i][j] = new Tile();
            }
        }
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(i == 0 || j == 0 || i == width-1 || j == height-1)
                    tile[i][j].setType(tile[i][j].WALL);
                else
                    tile[i][j].setType(tile[i][j].EMPTY);
            }
        }
        writeToFile();
    }

    private void writeToFile() {
        try {
            File file = new File("map.txt");

            if (!file.exists()) {
                boolean newFile = file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(int j = 0; j < height; j ++){
                for(int i = 0; i < width; i ++){
                    bw.write(tile[i][j].getType()+"");
                    if(i < (width-1))
                        bw.write(" ");
                }
                bw.newLine();
            }
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
