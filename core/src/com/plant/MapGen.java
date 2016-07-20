package com.plant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGen {
    private int tile[][];
    private int width;
    private int height;

    Random rand = new Random();

    public void newMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        tile = new int[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                tile[i][j] = rand.nextInt(4);
            }
        }
        writeToFile();
    }

    private void writeToFile() {
        try {
            File file = new File("map.txt");

            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(int j = 0; j < height; j ++){
                for(int i = 0; i < width; i ++){
                    bw.write(tile[i][j]+"");
                    if(i < (height-1))
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
