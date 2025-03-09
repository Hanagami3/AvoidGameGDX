package be.hanagami.obstacleAvoid.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.assets.AssetDescriptors;
import be.hanagami.obstacleAvoid.entity.Obstacle;

public class GameScreen implements Screen {

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;
    private GameController controller;
    private GameRenderer renderer;

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }


    @Override
    public void show() {

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.BACKGROUND);
        assetManager.load(AssetDescriptors.OBSTACLE);
        assetManager.load(AssetDescriptors.PLAYER);

        assetManager.finishLoading();

        controller = new GameController();
        renderer = new GameRenderer(controller, assetManager);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
