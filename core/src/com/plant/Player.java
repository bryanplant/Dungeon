package com.plant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;
import java.util.Vector;

public class Player {
    private int x = 100;
    private int y = 100;
    private static int size = 80;
    private float moveSpeed = 250;
    private int direction = 0;
    private boolean moving = false;
    private float aniCounter = 0;
    private float frameTime = 0.2f;
    private int frame = 0;
    private boolean dPad;
    private int[] moveCounter;
    private Vector<Bullet> b;

    private Texture playerSheet = new Texture("Player.png");
    private TextureRegion[] playerImg = new TextureRegion[12];

    private TouchPad touch;

    public Player()
    {
        //breaks up playerSheet into TextureRegions
        for(int i = 0; i <= 11; i++){
            if(i < 3)
                playerImg[i] = new TextureRegion(playerSheet, i*size, 0, size, size);
            else if (i < 6)
                playerImg[i] = new TextureRegion(playerSheet, (i-3)*size, size, size, size);
            else if(i < 9)
                playerImg[i] = new TextureRegion(playerSheet, (i-6)*size, 2*size, size, size);
            else
                playerImg[i] = new TextureRegion(playerSheet, (i-9)*size, 3*size, size, size);
            playerImg[i].flip(false, true);
        }

        //determines if desktop or android
        switch(Gdx.app.getType()){
            case Android:
                dPad = true;
                touch = new TouchPad();
                break;
            case Desktop:
                dPad = false;
                break;
            default:
                break;
        }

        moveCounter = new int[4];
        b = new Vector<Bullet>(0);
    }

    public void update(float dt, OrthographicCamera camera, Map map)
    {
        move(dt, camera, map);
        if(dPad)
            touch.update(camera);
        shoot(dt);
    }

    private void move(float dt, OrthographicCamera camera, Map map)
    {
        int smallest[] = new int[2];

        //android
        if(dPad){
            switch(touch.getInput(camera)){
                case 0:
                    smallest[0] = 0;
                    break;
                case 1:
                    smallest[0] = 1;
                    break;
                case 2:
                    smallest[0] = 2;
                    break;
                case 3:
                    smallest[0] = 3;
                    break;
                default:
                    smallest[0] = -1;
                    break;
            }
        }

        //computer
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                moveCounter[0]++;
            } else {
                moveCounter[0] = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                moveCounter[1]++;
            } else {
                moveCounter[1] = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                moveCounter[2]++;
            } else {
                moveCounter[2] = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                moveCounter[3]++;
            } else {
                moveCounter[3] = 0;
            }

            //determines which direction was pressed last
            smallest = new int[]{-1, 999999999}; //{key #, time held}
            for (int i = 0; i < 4; i++) {
                if (moveCounter[i] < smallest[1] && moveCounter[i] != 0) {
                    smallest[0] = i;
                    smallest[1] = moveCounter[i];
                }
            }
        }

        Rectangle check1;
        Rectangle check2;
        switch (smallest[0]) {
            case 0:
                check1 = new Rectangle(((x+size+(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), (y/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());
                check2 = new Rectangle(((x+size+(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), ((y+size)/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());

                if (!checkTileCollision(map, check1, (int)(x+(moveSpeed*dt)), y) && !checkTileCollision(map, check2, (int)(x+(moveSpeed*dt)), y)) {
                    x += moveSpeed * dt;
                    moving = true;
                }
                else {
                    x = (int)check1.x - size;
                    moving = false;
                }
                direction = 0;
                break;
            case 1:
                check1 = new Rectangle((x/map.getTileSize())*map.getTileSize(), ((y+size+(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());
                check2 = new Rectangle(((x+size)/map.getTileSize())*map.getTileSize(), ((y+size+(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());

                if (!checkTileCollision(map, check1, x, (int)(y+(moveSpeed*dt))) && !checkTileCollision(map, check2, x, (int)(y+(moveSpeed*dt)))) {
                    y += moveSpeed * dt;
                    moving = true;
                }
                else {
                    y = (int)check1.y - size;
                    moving = false;
                }
                direction = 1;
                break;
            case 2:
                check1 = new Rectangle(((x-(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), (y/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());
                check2 = new Rectangle(((x-(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), ((y+size)/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());

                if (!checkTileCollision(map, check1, (int)(x-(moveSpeed*dt)), y) && !checkTileCollision(map, check2, (int)(x-(moveSpeed*dt)), y)) {
                    x -= moveSpeed * dt;
                    moving = true;
                }
                else {
                    x = (int)check1.x + map.getTileSize();
                    moving = false;
                }
                direction = 2;
                break;
            case 3:
                check1 = new Rectangle((x/map.getTileSize())*map.getTileSize(), ((y-(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());
                check2 = new Rectangle(((x+size)/map.getTileSize())*map.getTileSize(), ((y-(int)(moveSpeed*dt))/map.getTileSize())*map.getTileSize(), map.getTileSize(), map.getTileSize());

                if (!checkTileCollision(map, check1, x, (int)(y-(moveSpeed*dt))) && !checkTileCollision(map, check2, x, (int)(y-(moveSpeed*dt)))) {
                    y -= moveSpeed * dt;
                    moving = true;
                }
                else {
                    y = (int)check1.y + map.getTileSize();
                    moving = false;
                }
                direction = 3;
                break;
            default:
                moving = false;
                aniCounter = 0;
                break;
        }

        //manages camera position
        camera.position.set(x, y, 0);
        if(x < Game.WIDTH/2)
            camera.position.set(Game.WIDTH/2, camera.position.y, 0);
        else if(x > map.getWidth() - Game.WIDTH/2)
            camera.position.set(map.getWidth() - Game.WIDTH/2, camera.position.y, 0);
        if(y < Game.HEIGHT/2)
            camera.position.set(camera.position.x, Game.HEIGHT/2, 0);
        else if(y > map.getHeight() - Game.HEIGHT/2)
            camera.position.set(camera.position.x, map.getHeight() - Game.HEIGHT/2, 0);
    }

    public boolean checkTileCollision(Map map, Rectangle check, int x, int y)
    {
        if(!map.getTile((int)check.x/map.getTileSize(), (int)check.y/map.getTileSize()).isSolid())
            return false;

        Rectangle playerBox = new Rectangle(x, y, size, size);

        return playerBox.overlaps(check);
    }

    public void shoot(float dt)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            b.add(new Bullet(x, y, direction, 20));
        }
        Iterator<Bullet> i = b.iterator();
        while (i.hasNext()) {
            i.next().update(dt);
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font, ShapeRenderer shape)
    {
        if(dPad)
            touch.draw(batch, font);

        if(moving) {
            if(aniCounter <= frameTime)
                frame = 1;
            else if(aniCounter <= 2*frameTime)
                frame = 0;
            else if(aniCounter <= 3*frameTime)
                frame = 2;
            else if(aniCounter <= 4*frameTime)
                frame = 0;
        }
        else {
            frame = 0;
        }

        if(direction == 0) {
            batch.draw(playerImg[frame], x, y);
        }
        else if(direction == 1) {
            batch.draw(playerImg[3 + frame], x, y);
        }
        else if(direction == 2) {
            batch.draw(playerImg[6 + frame], x, y);
        }
        else if(direction == 3) {
            batch.draw(playerImg[9 + frame], x, y);
        }

        aniCounter += Gdx.graphics.getDeltaTime();
        if((aniCounter/frameTime) >= 4)
            aniCounter = 0;

        Iterator<Bullet> i = b.iterator();
        while (i.hasNext()) {
            i.next().draw(shape);
        }
    }

    public void dispose()
    {
        playerSheet.dispose();
        touch.dispose();
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getMoveSpeed() {
        return moveSpeed;
    }
    public int getDirection() {
        return direction;
    }
    public boolean isMoving() {
        return moving;
    }
    public float getAniCounter() {
        return aniCounter;
    }


}

