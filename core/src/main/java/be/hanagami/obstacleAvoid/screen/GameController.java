package be.hanagami.obstacleAvoid.screen;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import be.hanagami.obstacleAvoid.config.DifficultyLevel;
import be.hanagami.obstacleAvoid.config.GameConfig;
import be.hanagami.obstacleAvoid.entity.Obstacle;
import be.hanagami.obstacleAvoid.entity.Player;

public class GameController {

    private static final Logger log = new Logger (GameController.class.getName(), Logger.DEBUG);

    private Player player;
    private Array<Obstacle> obstacles = new Array<>();
    private float obstacleTimer;
    private float scoreTimer;
    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;
    private Pool<Obstacle> obstaclePool;


    public GameController() {
        init();
    }

    private void init() {
        player = new Player();

        float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
        float startPlayerY = 1;

        player.setPosition(startPlayerX, startPlayerY);

        obstaclePool = Pools.get(Obstacle.class, 40);
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME){
            float min = Obstacle.SIZE / 2f;
            float max = GameConfig.WORLD_WIDTH - Obstacle.SIZE / 2f;


            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT ;

            Obstacle obstacle = obstaclePool.obtain();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;

        }
    }

    public void update(float delta){
        if (isGameOver()){
            log.debug("GAME OVER!!");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if (isPLayerCollidingWithObstacle()){
            log.debug("Collision detected");
            lives--;
        }
    }



    private void updatePlayer() {
        //log.debug("playerX= " + player.getX() + "playerY= " + player.getY() );
        player.update();
        blockPlayerFromLeavingTheWorld();
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles){
            obstacle.update();
        }
        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0){
            Obstacle first = obstacles.first();

            float minObstacleY =  - Obstacle.SIZE;

            if (first.getY() < minObstacleY){
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME){
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score){
            displayScore = Math.min(
                score,
                displayScore + (int) (60 + delta)
            );
        }
    }

    private boolean isPLayerCollidingWithObstacle() {
        for (Obstacle obstacle: obstacles){
            if (obstacle.isNotHit() &&
                obstacle.isPlayerColliding(player))
            {
                return true;
            }
        }
        return false;
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(
            player.getX(),
            player.getWidth() / 2f,
            GameConfig.WORLD_WIDTH - player.getWidth() / 2f
        );

//        if (playerX < player.getWidth() / 2){
//            playerX = player.getWidth() / 2;
//        } else if (playerX > GameConfig.WORLD_WIDTH - player.getWidth() / 2f) {
//            playerX = GameConfig.WORLD_WIDTH - player.getWidth() / 2f;
//        }

        player.setPosition(playerX, player.getY());
    }

    private boolean isGameOver() {
        return false;
        //return lives <= 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }
}
