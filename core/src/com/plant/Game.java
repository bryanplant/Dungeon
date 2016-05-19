package com.plant;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import states.GameStateManager;
import states.PlayState;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Dungeon";

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;
	private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(true);
		shape = new ShapeRenderer();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new PlayState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch, font);
	}

	public void resize(int width, int height) {
		gsm.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shape.dispose();
	}
}

