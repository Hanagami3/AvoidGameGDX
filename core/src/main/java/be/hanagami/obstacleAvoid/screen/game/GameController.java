package be.hanagami.obstacleAvoid.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import be.hanagami.obstacleAvoid.config.DifficultyLevel;
import be.hanagami.obstacleAvoid.config.GameConfig;
import be.hanagami.obstacleAvoid.entity.Background;
import be.hanagami.obstacleAvoid.entity.Obstacle;
import be.hanagami.obstacleAvoid.entity.Player;

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    private Player player;
    private Array<Obstacle> obstacles = new Array<>();
    private Background background;
    private float obstacleTimer;
    private float scoreTimer;
    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;
    private Pool<Obstacle> obstaclePool;
    private final float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
    private final float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;


    public GameController() {
        init();
    }

    private void init() {
        player = new Player();

        player.setPosition(startPlayerX, startPlayerY);

        obstaclePool = Pools.get(Obstacle.class, 40);

        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
    }

    private void createNewObstacle(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0;
            float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;


            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }
    }

    public void update(float delta) {
        if (isGameOver()) {
            log.debug("GAME OVER!!");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if (isPLayerCollidingWithObstacle()) {
            log.debug("Collision detected");
            lives--;

            if (isGameOver()) {
                log.debug("GAME OVER!!!");
            } else {
                restart();
            }
        }
    }

    private void restart() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        player.setPosition(startPlayerX, startPlayerY);
    }


    private void updatePlayer() {
        //log.debug("playerX= " + player.getX() + "playerY= " + player.getY() );

        float xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setX(player.getX() + xSpeed);
        blockPlayerFromLeavingTheWorld();
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        createNewObstacle(delta);
        removePassedObstacles();
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            Obstacle first = obstacles.first();

            float minObstacleY = -GameConfig.OBSTACLE_SIZE;

            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void updateScore(float delta) {
        scoreTimer += delta;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5);
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(
                score,
                displayScore + (int) (60 + delta)
            );
        }
    }

    private boolean isPLayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() &&
                obstacle.isPlayerColliding(player)) {
                return true;
            }
        }
        return false;
    }

    private void blockPlayerFromLeavingTheWorld() {
        float playerX = MathUtils.clamp(
            player.getX(),
            0,
            GameConfig.WORLD_WIDTH - player.getWidth()
        );

//        if (playerX < player.getWidth() / 2){
//            playerX = player.getWidth() / 2;
//        } else if (playerX > GameConfig.WORLD_WIDTH - player.getWidth() / 2f) {
//            playerX = GameConfig.WORLD_WIDTH - player.getWidth() / 2f;
//        }
        player.setPosition(playerX, player.getY());
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

    public int getLives() {
        return lives;
    }

    public int getDisplayScore() {
        return displayScore;
    }
}
