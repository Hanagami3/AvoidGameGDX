package be.hanagami.obstacleAvoid.commun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.config.DifficultyLevel;

public class GameManager {

//    private static final GameManager INSTANCE = new GameManager();
//
//    public static GameManager getInstance(){
//        return INSTANCE;
//    }

    public static final GameManager INSTANCE = new GameManager();

    private static final String  HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "diffficutly";

    private Preferences PREFS;
    private int highScore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getSimpleName());
        highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
        String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    public void updateHighScore(int score){
        if (score < highScore){
            return;
        }
        highScore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highScore);
        PREFS.flush();
    }

    public String getHighScoreString(){
        return String.valueOf(highScore);
    }

    public DifficultyLevel getDifficultyLevel(){
        return difficultyLevel;
    }

    public void updateDifficulty(DifficultyLevel newDifficultyLevel){

        if (difficultyLevel == newDifficultyLevel){
            return;
        }
        difficultyLevel = newDifficultyLevel;
        PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
        PREFS.flush();
    }
}
