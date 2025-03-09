package be.hanagami.obstacleAvoid.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import be.hanagami.obstacleAvoid.config.GameConfig;

public class Player extends GameObjectBase{

    public Player() {
       super(GameConfig.PLAYER_BOUNDS_RADIUS);
       setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
}
