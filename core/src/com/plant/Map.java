package com.plant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {

    private Tile[][] tile;
    private int tileWidth;
    private int tileHeight;
    private int tileSize = 100;
    private int padding = 1;
    private int width;
    private int height;
    private Texture tileSheet = new Texture("tiles.png");
    private TextureRegion[] tileImg = new TextureRegion[4];

    public Map(String fileName)
    {
        loadMap(fileName);
        printMap();

        tileImg[0] = new TextureRegion(tileSheet, 0, 0, tileSize, tileSize);
        tileImg[1] = new TextureRegion(tileSheet, tileSize+padding, 0, tileSize, tileSize);
        tileImg[2] = new TextureRegion(tileSheet, 0, tileSize+padding, tileSize, tileSize);
        tileImg[3] = new TextureRegion(tileSheet, tileSize+padding, tileSize+padding, tileSize, tileSize);
    }

    private void loadMap(String fileName)
    {
        FileHandle file = Gdx.files.internal(fileName);
        String s = file.readString();

        tileWidth = s.indexOf("\n")/2;
        tileHeight = s.length()/s.indexOf("\n");

        tile = new Tile[tileHeight][tileWidth];
        for(int j = 0; j < tileHeight; j++)
        {
            for(int i = 0; i < tileWidth; i++)
            {
                tile[j][i] = new Tile();
            }
        }

        int character = 0;
        for(int i = 0; i < tileHeight; i++)
        {
            for(int j = 0; j < tileWidth; j++)
            {
                tile[j][i].setType(s.charAt(character));

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
                System.out.print(tile[i][j].getType() + " ");
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
                switch(tile[i][j].getType())
                {
                    case '0':
                        batch.draw(tileImg[0], i*tileSize, j*tileSize);
                        break;
                    case '1':
                        batch.draw(tileImg[1], i*tileSize, j*tileSize);
                        break;
                    case '2':
                        batch.draw(tileImg[2], i*tileSize, j*tileSize);
                        break;
                    case '3':
                        batch.draw(tileImg[3], i*tileSize, j*tileSize);
                        break;

                }
            }
        }
    }

    public void dispose()
    {
        tileSheet.dispose();
    }

    public Tile getTile(int x, int y)
    {
        return tile[x][y];
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileSize()
    {
        return tileSize;
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
