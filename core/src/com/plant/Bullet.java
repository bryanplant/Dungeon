package com.plant;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bullet {
    int x;
    int y;
    int direction;
    int speed;

    public Bullet(int x, int y, int direction, int speed)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public void update(float dt)
    {
        switch(direction)
        {
            case 0:
                x+=speed*dt;
                break;
            case 1:
                y+=speed*dt;
                break;
            case 2:
                x-=speed*dt;
                break;
            case 3:
                y-=speed*dt;
                break;
            default:
                break;
        }
    }

    public void draw(ShapeRenderer shape)
    {

    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
