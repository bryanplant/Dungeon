package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.plant.Game;
import com.plant.Map;
import com.plant.Player;

public class PlayState extends State {
    private Player player;
    private Map map;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        player = new Player();
        map = new Map("map.txt");

        camera.setToOrtho(true, Game.WIDTH, Game.HEIGHT);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    protected void update(float dt) {
        player.update(dt, camera, map);
        camera.update();
    }

    @Override
    protected void render(SpriteBatch batch, BitmapFont font) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        batch.begin();
        map.draw(batch);
        player.draw(batch, font);
        batch.end();
    }

    @Override
    protected void dispose() {
        player.dispose();
        map.dispose();
    }

    @Override
    protected void resize(int width, int height) {
        viewport.update(width, height);
    }
}
