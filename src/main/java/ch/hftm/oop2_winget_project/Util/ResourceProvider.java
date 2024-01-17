package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.App;
import javafx.scene.image.Image;

import java.util.Objects;

public final class ResourceProvider
{
    public static final String FXML_ROOT = "/Views/";
    public static final String ICONS_ROOT = "/Icons/";
    public static final String MAINWINDOW_VIEW_NAME = "MainWindow.fxml";
    public static final String SEARCHPACKAGES_VIEW_NAME = "SearchPackages.fxml";
    public static final String INSTALLEDPACKAGES_VIEW_NAME = "InstalledPackages.fxml";
    public static final String UPGRADEPACKAGES_VIEW_NAME = "UpgradePackages.fxml";
    public static final String SPLASHSCREEN_VIEW_NAME = "SplashScreen.fxml";
    private static final Image taskbarIcon = new Image(Objects.requireNonNull(App.class.getResourceAsStream(ICONS_ROOT + "taskbarIcon_colorized.png")));
    public static Image getTaskbarIcon()
    {
        return taskbarIcon;
    }
}
