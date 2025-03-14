package be.hanagami.obstacleAvoid.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.screen.menu.MenuScreen;

public class GameScreen implements Screen {

    private static final Logger log = new Logger (GameScreen.class.getName(), Logger.DEBUG);

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
        log.debug("show");
//        waitMillis(800);
//        assetManager.load(AssetDescriptors.FONT);
//        waitMillis(800);
//        assetManager.load(AssetDescriptors.GAME_PLAY);
//        waitMillis(800);
//
//
//        assetManager.finishLoading();

        controller = new GameController(game);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }


    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        // le faire ici pour être certain que la frame est terminée avec les update et donc éviter crash!!
        //ou switcher vers un autre écran alors que la frame n'est pas terminée
        if (controller.isGameOver()){
            game.setScreen(new MenuScreen(game));
        }
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
        log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
