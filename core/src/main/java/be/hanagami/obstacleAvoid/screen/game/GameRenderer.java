package be.hanagami.obstacleAvoid.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import be.hanagami.obstacleAvoid.assets.AssetDescriptors;
import be.hanagami.obstacleAvoid.assets.RegionNames;
import be.hanagami.obstacleAvoid.config.GameConfig;
import be.hanagami.obstacleAvoid.entity.Background;
import be.hanagami.obstacleAvoid.entity.Obstacle;
import be.hanagami.obstacleAvoid.entity.Player;
import be.hanagami.obstacleAvoid.util.GdxUtils;
import be.hanagami.obstacleAvoid.util.ViewportUtils;
import be.hanagami.obstacleAvoid.util.debug.DebugCameraController;

public class GameRenderer implements Disposable {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private TextureRegion playerRegion;
    private TextureRegion obstacleRegion;
    private TextureRegion backgroundRegion;

    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
        this.controller = controller;
        this.assetManager = assetManager;
        this.batch = batch;
        init();
    }

    private void init(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        font = assetManager.get(AssetDescriptors.FONT);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        playerRegion = gamePlayAtlas.findRegion(RegionNames.PLAYER);
        obstacleRegion = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);
        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

//        playerTexture = assetManager.get(AssetDescriptors.PLAYER);
//        obstacleTexture = assetManager.get(AssetDescriptors.OBSTACLE);
//        backgroundTexture = assetManager.get(AssetDescriptors.BACKGROUND);

    }

    public void render(float delta){
        batch.totalRenderCalls = 0;

        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        if (Gdx.input.isTouched() && !controller.isGameOver()){
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            System.out.println("screenTouch= " + screenTouch);
            System.out.println("worldTouch= " + worldTouch);

            Player player = controller.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);
        }

        GdxUtils.clearScreen();

        renderGameplay();

        renderUI();

        renderDebug();

        // System.out.println("totalRenderCalls= " + batch.totalRenderCalls);
    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Background background = controller.getBackground();
        batch.draw(
            backgroundRegion,
            background.getX() , background.getY(),
            background.getWidth() , background.getHeight()
        );

        Player player = controller.getPlayer();
        batch.draw(
            playerRegion,
            player.getX(), player.getY(),
            player.getWidth() , player.getHeight()
        );

        for (Obstacle obstacle : controller.getObstacles()){
            batch.draw(
                obstacleRegion,
                obstacle.getX() , obstacle.getY(),
                obstacle.getWidth() , obstacle.getHeight()
            );
        }


        batch.end();
    }

    private void renderUI() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);

        font.draw(
            batch,
            livesText,
            20,
            GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE:" + controller.getDisplayScore();
        layout.setText(font, scoreText);

        font.draw(
            batch,
            scoreText,
            GameConfig.HUD_WIDTH - layout.width - 20,
            GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

//        float lineX = Obstacle.SIZE / 2f;
//        renderer.setColor(Color.BLUE);
//        renderer.line(lineX, 0, lineX, GameConfig.WORLD_HEIGHT);

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        Player player = controller.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = controller.getObstacles();

        for (Obstacle obstacle : obstacles){
            obstacle.drawDebug(renderer);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelParUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
