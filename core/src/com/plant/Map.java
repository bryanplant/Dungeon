package com.plant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {

    private char[][] tile;
    private int tileWidth;
    private int tileHeight;
    private int tileSize = 100;
    private int width;
    private int height;
    private Texture tileSheet = new Texture("tiles.png");
    private TextureRegion[] tileImg = new TextureRegion[4];

    public Map(String fileName)
    {
        loadMap(fileName);
        printMap();

        tileImg[0] = new TextureRegion(tileSheet, 0, 0, tileSize, tileSize);
        tileImg[1] = new TextureRegion(tileSheet, tileSize, 0, tileSize, tileSize);
        tileImg[2] = new TextureRegion(tileSheet, 0, tileSize, tileSize, tileSize);
        tileImg[3] = new TextureRegion(tileSheet, tileSize, tileSize, tileSize, tileSize);
    }

    private void loadMap(String fileName)
    {
        FileHandle file = Gdx.files.internal(fileName);
        String s = file.readString();

        tileWidth = s.indexOf("\n")/2;
        tileHeight = s.length()/s.indexOf("\n");

        tile = new char[tileHeight][tileWidth];

        int character = 0;
        for(int i = 0; i < tileHeight; i++)
        {
            for(int j = 0; j < tileWidth; j++)
            {
                tile[i][j] = s.charAt(character);

                character+=2;
            }
            character++;
        }

        width = tileWidth*tileSize;
        height = tileHeight*tileSize;
    }

    private void printMap()
    {
        for(int i = 0; i < tileHeight; i++)
        {
            for(int j = 0; j < tileWidth; j++)
            {
                System.out.print(tile[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void draw(SpriteBatch batch)
    {
        for(int i = 0; i < tileHeight; i++)
        {
            for(int j = 0; j < tileWidth; j++)
            {
                switch(tile[i][j])
                {
                    case '0':
                        batch.draw(tileImg[0], j*tileSize, i*tileSize);
                        break;
                    case '1':
                        batch.draw(tileImg[1], j*tileSize, i*tileSize);
                        break;
                    case '2':
                        batch.draw(tileImg[2], j*tileSize, i*tileSize);
                        break;
                    case '3':
                        batch.draw(tileImg[3], j*tileSize, i*tileSize);
                        break;

                }
            }
        }
    }

    public void dispose()
    {
        tileSheet.dispose();
    }

    public char[][] getTile()
    {
        return tile;
    }

    public int geTtileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
