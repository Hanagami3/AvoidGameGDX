package be.hanagami.obstacleAvoid.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;

import be.hanagami.obstacleAvoid.ObstacleAvoidGame;
import be.hanagami.obstacleAvoid.assets.AssetDescriptors;
import be.hanagami.obstacleAvoid.assets.RegionNames;
import be.hanagami.obstacleAvoid.commun.GameManager;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log = new Logger (HighScoreScreen.class.getName(), Logger.DEBUG);

    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
//        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI);
//        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion background = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
//        TextureRegion panelRegion = uiAtlas.findRegion(RegionNames.PANEL);
//
//        TextureRegion backRegion = uiAtlas.findRegion(RegionNames.BACK);
//        TextureRegion backPressedRegion = uiAtlas.findRegion(RegionNames.BACK_PRESSED);
//
//        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        table.setBackground(new TextureRegionDrawable(background));

        Label highScoreText = new Label("HIGHSCORE", uiskin);

        String highScoreString = GameManager.INSTANCE.getHighScoreString();
        Label highScoreLabel = new Label(highScoreString, uiskin);

//        ImageButton backButton = new ImageButton(
//            new TextureRegionDrawable(backRegion),
//            new TextureRegionDrawable(backPressedRegion)
//        );

        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

//        Table contentTable = new Table();
        Table contentTable = new Table(uiskin);
        contentTable.defaults().pad(20);
//        contentTable.setBackground(new TextureRegionDrawable(panelRegion));
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void back() {
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }
}
