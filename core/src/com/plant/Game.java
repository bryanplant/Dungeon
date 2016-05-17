package com.plant;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Dungeon";

	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;
	private OrthographicCamera camera;
	private Viewport viewport;

	private Player player;
	private Map map;

	@Override
	public void create () {
		//load pictures and set values
		player = new Player();
		map = new Map("map.txt");

		camera = new OrthographicCamera();
		camera.setToOrtho(true, WIDTH, HEIGHT);
		viewport = new StretchViewport(WIDTH, HEIGHT, camera);

		batch = new SpriteBatch();
		font = new BitmapFont(true);
		shape = new ShapeRenderer();
	}

	public void update() {
		player.update(camera);
	}

	@Override
	public void render () {
		update();

		//draw to screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		map.draw(batch);
		player.draw(batch, font);
		batch.end();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		//delete pictures
		player.dispose();
		map.dispose();
		batch.dispose();
		font.dispose();
		shape.dispose();
	}
}

