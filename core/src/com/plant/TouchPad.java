package com.plant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class TouchPad {
    private Vector3 touchPos;
    private Rectangle buttonUp;
    private Rectangle buttonRight;
    private Rectangle buttonDown;
    private Rectangle buttonLeft;
    private Texture dPad = new Texture("dPad.png");
    private TextureRegion dPadImg = new TextureRegion(dPad, 200, 200);
    private int x;
    private int y;

    public TouchPad(int x, int y)
    {
        this.x = x;
        this.y = y;
        touchPos = new Vector3();
        buttonUp = new Rectangle(x+75, y, 50, 75);
        buttonRight = new Rectangle(x+125, y+75, 75, 50);
        buttonDown = new Rectangle(x+75, y+125, 50, 75);
        buttonLeft = new Rectangle(x, y+75, 75, 50);

        dPadImg.flip(false, true);
    }

    public int getInput(OrthographicCamera camera)
    {
        if(Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

        }
        else {
            touchPos.set(0, 0, 0);
        }


        if(buttonRight.contains(touchPos.x, touchPos.y)) {
            return 0;
        }
        else if(buttonDown.contains(touchPos.x, touchPos.y)) {
            return 1;
        }
        else if(buttonLeft.contains(touchPos.x, touchPos.y)) {
            return 2;
        }
        else if(buttonUp.contains(touchPos.x, touchPos.y)) {
            return 3;
        }
        else {
            return 4;
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font)
    {
        batch.draw(dPad, x, y);
        font.draw(batch, Float.toString(touchPos.x) + ", " + Float.toString(touchPos.y), 0, 0);
        font.draw(batch, Float.toString(buttonUp.x) + ", " + Float.toString(buttonUp.y), 0, 15);
        font.draw(batch, Float.toString(x) + ", " + Float.toString(y), 0, 30);
    }



    public void changeLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Texture getImage()
    {
        return dPad;
    }

    public void dispose()
    {
        dPad.dispose();
    }
}

