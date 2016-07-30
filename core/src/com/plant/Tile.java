package com.plant;

public class Tile {
    private char type;
    private boolean solid;

    public static final char EMPTY = '0';
    public static final char WALL = '1';

    public char getType()
    {
        return type;
    }

    public boolean isSolid()
    {
        return solid;
    }

    public void setType(char type)
    {
        this.type = type;
        if(type == WALL) {
            solid = true;
        }
        else {
            solid = false;
        }
    }
}
