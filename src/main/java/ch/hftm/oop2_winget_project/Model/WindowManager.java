package ch.hftm.oop2_winget_project.Model;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowManager
{
    private final int DEFAULT_WINDOW_WIDTH = 1345;
    private final int DEFAULT_UPDATE_VIEW_WINDOW_WIDTH = 1485;
    private final int DEFAULT_WINDOW_HEIGHT = 725;
    private final int DEFAULT_SPLASHSCREEN_WIDTH = 470;
    private final int DEFAULT_SPLASHSCREEN_HEIGHT = 255;
    private double xOffset = 0;
    private double yOffset = 0;
    private double tempLastWindowPosition_X;
    private double tempLastWindowPosition_Y;
    private double tempLastWindowWidth;
    private double tempLastWindowHeight;
    private boolean isFullscreen;
    private Rectangle2D primaryScreenBounds;
    private Stage stage;


    public WindowManager(Stage stage)
    {
        this.stage = stage;
        this.primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    }

    public int getDEFAULT_WINDOW_WIDTH()
    {
        return DEFAULT_WINDOW_WIDTH;
    }

    public int getDEFAULT_UPDATE_VIEW_WINDOW_WIDTH()
    {
        return DEFAULT_UPDATE_VIEW_WINDOW_WIDTH;
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

    public boolean isWindowFullscreen(){
        return isFullscreen;
    }

    public void maximizeWindow()
    {
        saveLastWindowPosition();
        saveLastWindowSize();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getMaxX());
        stage.setHeight(primaryScreenBounds.getMaxY());
        isFullscreen = true;
    }

    public void minimizeWindow()
    {
        stage.setIconified(true);
    }

    public void restoreWindow()
    {
        stage.setX(getTempLastWindowPosition_X());
        stage.setY(getTempLastWindowPosition_Y());
        stage.setWidth(getTempLastWindowWidth());
        stage.setHeight(getTempLastWindowHeight());
        isFullscreen = false;
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
