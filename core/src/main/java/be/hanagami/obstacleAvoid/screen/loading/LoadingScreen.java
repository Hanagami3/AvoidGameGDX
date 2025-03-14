package be.hanagami.obstacleAvoid.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.assets.AssetDescriptors;
import be.hanagami.obstacleAvoid.config.GameConfig;
import be.hanagami.obstacleAvoid.screen.game.GameScreen;
import be.hanagami.obstacleAvoid.screen.menu.MenuScreen;
import be.hanagami.obstacleAvoid.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    private static final Logger log = new Logger (LoadingScreen.class.getName(), Logger.DEBUG);

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60;


    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        //log.debug("show");
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY);
//        assetManager.load(AssetDescriptors.UI);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.HIT_SOUND);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        //log.debug("before end");
        renderer.end();
        //log.debug("after end");

        if (changeScreen){
//            game.setScreen(new GameScreen(game));
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        //log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        //log.debug("dispose");
        renderer.dispose();
        //renderer = null;
    }

    private void update(float delta){
       // waitMillis(400);
         progress = assetManager.getProgress();

        if (assetManager.update()){
            waitTime -= delta;

            if (waitTime <= 0){
                //game.setScreen(new GameScreen(game));
                changeScreen = true;
            }
        }
    }

    private void draw(){
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(
            progressBarX,
            progressBarY,
            progress * PROGRESS_BAR_WIDTH,
            PROGRESS_BAR_HEIGHT
        );

    }

    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
