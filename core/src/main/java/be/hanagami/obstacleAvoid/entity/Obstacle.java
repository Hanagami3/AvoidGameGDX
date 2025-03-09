package be.hanagami.obstacleAvoid.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import be.hanagami.obstacleAvoid.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {

    private static final float BOUNDS_RADIUS =0.3f;
    public static final float SIZE = 2 * BOUNDS_RADIUS;

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;



    public Obstacle() {
       super(BOUNDS_RADIUS);
    }


    public void update(){
        setY(getY() - ySpeed);
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds, this.getBounds());

//        if (overlaps){
//            hit = true;
//        }
        hit = overlaps;

        return  overlaps;
    }

    public boolean isNotHit(){
        return !hit;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        hit = false;
    }
}
