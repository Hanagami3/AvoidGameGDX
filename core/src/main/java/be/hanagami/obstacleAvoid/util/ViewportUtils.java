package be.hanagami.obstacleAvoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.jdi.IntegerValue;

public class ViewportUtils {

    private static final Logger log = new Logger (ViewportUtils.class.getName(), Logger.DEBUG);
    public static final int DEFAULT_CELL_SIZE = 1;

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer){
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize){
        if (viewport == null){
            throw new IllegalArgumentException("viewport param is required. ");
        }

        if (renderer == null){
            throw new IllegalArgumentException("renderer param is required. ");
        }

        if (cellSize < DEFAULT_CELL_SIZE){
            cellSize = DEFAULT_CELL_SIZE;
        }

        //renderer.getColor().cpy();
        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        for (int x = -doubleWorldWidth; x < doubleWorldWidth; x+=cellSize){
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }
        for (int y = -doubleWorldHeight; y < doubleWorldHeight; y+=cellSize){
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }

        renderer.setColor(Color.RED);
        renderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        renderer.setColor(Color.GREEN);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);

        renderer.end();

        renderer.setColor(oldColor);

    }

    public static void debugPixelParUnit(Viewport viewport){
        if (viewport == null){
            throw new IllegalArgumentException("viewport param is required.");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("xPPU= " + xPPU + "| yPPU= " + yPPU);
    }

    private ViewportUtils() {
    }
}
