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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Shape;

public class Player {
    private float x = 20;
    private float y = 300;
    private static int playerSize = 80;
    private float moveSpeed = 250;
    private int direction = 0;
    private boolean moving = false;
    private float aniCounter = 0;
    private float frameTime = 0.2f;
    private int frame = 0;
    private boolean dPad;

    private Texture playerSheet = new Texture("Player.png");
    private TextureRegion[] playerImg = new TextureRegion[12];

    private TouchPad touch;

    public Player()
    {
        //breaks up playerSheet into TextureRegions
        for(int i = 0; i <= 11; i++){
            if(i < 3)
                playerImg[i] = new TextureRegion(playerSheet, i*playerSize, 0, playerSize, playerSize);
            else if (i < 6)
                playerImg[i] = new TextureRegion(playerSheet, (i-3)*playerSize, playerSize, playerSize, playerSize);
            else if(i < 9)
                playerImg[i] = new TextureRegion(playerSheet, (i-6)*playerSize, 2*playerSize, playerSize, playerSize);
            else
                playerImg[i] = new TextureRegion(playerSheet, (i-9)*playerSize, 3*playerSize, playerSize, playerSize);
            playerImg[i].flip(false, true);
        }

        //determines if desktop or android
        switch(Gdx.app.getType()){
            case Android:
                dPad = true;
                touch = new TouchPad(0, 400);
                break;
            case Desktop:
                dPad = false;
                break;
            default:
                break;
        }
    }

    public void update(OrthographicCamera camera)
    {
        if(dPad){
            switch(touch.getInput(camera)){
                case 0:
                    x += moveSpeed * Gdx.graphics.getDeltaTime();
                    direction = 0;
                    moving = true;
                    break;
                case 1:
                    y += moveSpeed * Gdx.graphics.getDeltaTime();
                    direction = 1;
                    moving = true;
                    break;
                case 2:
                    x -= moveSpeed * Gdx.graphics.getDeltaTime();
                    direction = 2;
                    moving = true;
                    break;
                case 3:
                    y -= moveSpeed * Gdx.graphics.getDeltaTime();
                    direction = 3;
                    moving = true;
                    break;
                case 4:
                    moving = false;
                    aniCounter = 0;
                    break;
            }
        }
        else{
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                x += moveSpeed * Gdx.graphics.getDeltaTime();
                direction = 0;
                moving = true;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                y += moveSpeed * Gdx.graphics.getDeltaTime();
                direction = 1;
                moving = true;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                x -= moveSpeed * Gdx.graphics.getDeltaTime();
                direction = 2;
                moving = true;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                y -= moveSpeed * Gdx.graphics.getDeltaTime();
                direction = 3;
                moving = true;
            }
            else{
                moving = false;
                aniCounter = 0;
            }
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font)
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

