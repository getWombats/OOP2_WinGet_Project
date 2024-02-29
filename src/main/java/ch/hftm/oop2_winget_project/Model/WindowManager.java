package ch.hftm.oop2_winget_project.Model;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager
{
    private final int DEFAULT_WINDOW_WIDTH = 1340;
    private final int DEFAULT_WINDOW_HEIGHT = 700;
    private final int DEFAULT_SPLASHSCREEN_WIDTH = 470;
    private final int DEFAULT_SPLASHSCREEN_HEIGHT = 255;
    private double xOffset = 0;
    private double yOffset = 0;
    private double tempLastWindowPosition_X;
    private double tempLastWindowPosition_Y;
    private double tempLastWindowWidth;
    private double tempLastWindowHeight;
    private Rectangle2D primaryScreenBounds;
    private Stage stage;


    public WindowManager(Stage stage)
    {
        this.stage = stage;
        this.primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        this.stage.setMinWidth(DEFAULT_WINDOW_WIDTH);
//        this.stage.setMinHeight(DEFAULT_WINDOW_HEIGHT);
    }

    public int getDEFAULT_WINDOW_WIDTH()
    {
        return DEFAULT_WINDOW_WIDTH;
    }

    public int getDEFAULT_WINDOW_HEIGHT()
    {
        return DEFAULT_WINDOW_HEIGHT;
    }

    public int getDEFAULT_SPLASHSCREEN_WIDTH()
    {
        return DEFAULT_SPLASHSCREEN_WIDTH;
    }

    public int getDEFAULT_SPLASHSCREEN_HEIGHT()
    {
        return DEFAULT_SPLASHSCREEN_HEIGHT;
    }

    public double getTempLastWindowPosition_X()
    {
        return tempLastWindowPosition_X;
    }

    public void setTempLastWindowPosition_X(double tempLastWindowPosition_X)
    {
        this.tempLastWindowPosition_X = tempLastWindowPosition_X;
    }

    public double getTempLastWindowPosition_Y()
    {
        return tempLastWindowPosition_Y;
    }

    public void setTempLastWindowPosition_Y(double tempLastWindowPosition_Y)
    {
        this.tempLastWindowPosition_Y = tempLastWindowPosition_Y;
    }

    public double getTempLastWindowWidth()
    {
        return tempLastWindowWidth;
    }

    public void setTempLastWindowWidth(double tempLastWindowWidth)
    {
        this.tempLastWindowWidth = tempLastWindowWidth;
    }

    public double getTempLastWindowHeight()
    {
        return tempLastWindowHeight;
    }

    public void setTempLastWindowHeight(double tempLastWindowHeight)
    {
        this.tempLastWindowHeight = tempLastWindowHeight;
    }

    public double getxOffset()
    {
        return xOffset;
    }

    public void setxOffset(double xOffset)
    {
        this.xOffset = xOffset;
    }

    public double getyOffset()
    {
        return yOffset;
    }

    public void setyOffset(double yOffset)
    {
        this.yOffset = yOffset;
    }

    public Rectangle2D getPrimaryScreenBounds()
    {
        return primaryScreenBounds;
    }

    public void setPrimaryScreenBounds(Rectangle2D primaryScreenBounds)
    {
        this.primaryScreenBounds = primaryScreenBounds;
    }

    public Stage getStage()
    {
        return stage;
    }

    public void maximizeWindow()
    {
        saveLastWindowPosition();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setMaxWidth(primaryScreenBounds.getWidth());
        stage.setMinWidth(primaryScreenBounds.getWidth());
        stage.setMaxHeight(primaryScreenBounds.getHeight());
        stage.setMinHeight(primaryScreenBounds.getHeight());
    }

    public void minimizeWindow()
    {
        stage.setIconified(true);
    }

    public void restoreWindow()
    {
        stage.setX(getTempLastWindowPosition_X());
        stage.setY(getTempLastWindowPosition_Y());
        stage.setMaxWidth(DEFAULT_WINDOW_WIDTH);
        stage.setMaxHeight(DEFAULT_WINDOW_HEIGHT);
        stage.setMinWidth(DEFAULT_WINDOW_WIDTH);
        stage.setMinHeight(DEFAULT_WINDOW_HEIGHT);

//        stage.setMaxWidth(getTempLastWindowWidth());
//        stage.setMaxHeight(getTempLastWindowHeight());
//        stage.setMinWidth(getTempLastWindowWidth());
//        stage.setMinHeight(getTempLastWindowHeight());
    }

    private void saveLastWindowPosition()
    {
        setTempLastWindowPosition_X(stage.getX());
        setTempLastWindowPosition_Y(stage.getY());
    }

    private void saveLastWindowSize()
    {
        setTempLastWindowWidth(stage.getWidth());
        setTempLastWindowHeight(stage.getHeight());
    }
}
