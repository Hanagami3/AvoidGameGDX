package be.hanagami.obstacleAvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;

import be.hanagami.obstacleAvoid.screen.GameScreen;
import be.hanagami.obstacleAvoid.screen.GameScreenOld;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ObstacleAvoidGame extends Game {

    private AssetManager assetManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
