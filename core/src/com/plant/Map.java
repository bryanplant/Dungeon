package com.plant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {

    private char[][] tile;
    private int width;
    private int height;
    private int blockSize = 100;
    private Texture tileSheet = new Texture("tiles.png");
    private TextureRegion[] tileImg = new TextureRegion[4];

    public Map(String fileName)
    {
        loadMap(fileName);
        printMap();

        tileImg[0] = new TextureRegion(tileSheet, 0, 0, blockSize, blockSize);
        tileImg[1] = new TextureRegion(tileSheet, blockSize, 0, blockSize, blockSize);
        tileImg[2] = new TextureRegion(tileSheet, 0, blockSize, blockSize, blockSize);
        tileImg[3] = new TextureRegion(tileSheet, blockSize, blockSize, blockSize, blockSize);
    }

    private void loadMap(String fileName)
    {
        FileHandle file = Gdx.files.internal(fileName);
        String s = file.readString();

        width = s.indexOf("\n")/2;
        height = s.length()/s.indexOf("\n");

        tile = new char[height][width];

        int character = 0;
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                tile[i][j] = s.charAt(character);

                character+=2;
            }
            character++;
        }
    }

    private void printMap()
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                System.out.print(tile[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void draw(SpriteBatch batch)
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                switch(tile[i][j])
                {
                    case '0':
                        batch.draw(tileImg[0], j*blockSize, i*blockSize);
                        break;
                    case '1':
                        batch.draw(tileImg[1], j*blockSize, i*blockSize);
                        break;
                    case '2':
                        batch.draw(tileImg[2], j*blockSize, i*blockSize);
                        break;
                    case '3':
                        batch.draw(tileImg[3], j*blockSize, i*blockSize);
                        break;

                }
            }
        }
    }

    public void dispose()
    {
        tileSheet.dispose();
    }
}
