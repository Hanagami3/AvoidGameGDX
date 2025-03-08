package be.hanagami.obstacleAvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import be.hanagami.obstacleAvoid.screen.GameScreen;
import be.hanagami.obstacleAvoid.screen.GameScreenOld;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class ObstacleAvoidGame extends Game {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new GameScreen());
    }
}
