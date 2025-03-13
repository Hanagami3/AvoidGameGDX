package be.hanagami.obstacleAvoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.assets.AssetDescriptors;
import be.hanagami.obstacleAvoid.assets.RegionNames;
import be.hanagami.obstacleAvoid.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    private static final Logger log = new Logger (MenuScreen.class.getName(), Logger.DEBUG);

    public MenuScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
        //TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        //TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //ImageButton playButton = createButton(uiAtlas, RegionNames.PLAY, RegionNames.PLAY_PRESSED);
        TextButton playButton = new TextButton("PLAY", uiskin);
        playButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        //ImageButton highScoreButton = createButton(uiAtlas, RegionNames.HIGH_SCORE, RegionNames.HIGH_SCORE_PRESSED);
        TextButton highScoreButton = new TextButton("HIGHSCORE", uiskin);
        highScoreButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        //ImageButton optionsButton = createButton(uiAtlas, RegionNames.OPTIONS, RegionNames.OPTIONS_PRESSED);
        TextButton optionsButton = new TextButton("OPTIONS", uiskin);
        optionsButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        TextButton quitButton = new TextButton("QUIT", uiskin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });


        Table buttonTable = new Table(uiskin);
        buttonTable.defaults().pad(20);
        //buttonTable.setBackground(new TextureRegionDrawable(panelRegion));
        buttonTable.setBackground(RegionNames.PANEL);

        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionsButton).row();
        buttonTable.add(quitButton).row();

        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }


    private void play() {
        log.debug("play()");
        game.setScreen(new GameScreen(game));
    }
    private void showHighScore() {
        log.debug("showHighScore()");
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions() {
        log.debug("showOption()");
        game.setScreen(new OptionsScreen(game));
    }
    private void quit() {
        log.debug("quit()");
        Gdx.app.exit();
    }

//    private static ImageButton createButton(TextureAtlas atlas, String upRegionName, String downRegionName){
//        TextureRegion upRegion = atlas.findRegion(upRegionName);
//        TextureRegion downRegion = atlas.findRegion(downRegionName);
//
//        return new ImageButton(
//            new TextureRegionDrawable(upRegion),
//            new TextureRegionDrawable(downRegion)
//        );
//    }
}
