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
    private int size = 100;
    private Texture tileSheet = new Texture("tiles.png");
    private TextureRegion[] tileImg = new TextureRegion[4];

    public Map(String fileName)
    {
        loadMap(fileName);
        printMap();

        tileImg[0] = new TextureRegion(tileSheet, 0, 0, size, size);
        tileImg[1] = new TextureRegion(tileSheet, size, 0, size, size);
        tileImg[2] = new TextureRegion(tileSheet, 0, size, size, size);
        tileImg[3] = new TextureRegion(tileSheet, size, size, size, size);
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
                        batch.draw(tileImg[0], i*size, j*size);
                        break;
                    case '1':
                        batch.draw(tileImg[1], i*size, j*size);
                        break;
                    case '2':
                        batch.draw(tileImg[2], i*size, j*size);
                        break;
                    case '3':
                        batch.draw(tileImg[3], i*size, j*size);
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
