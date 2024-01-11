package ch.hftm.oop2_winget_project;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.WinGetSettings;
import ch.hftm.oop2_winget_project.Service.StageAndSceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application
{
    private static WinGetSettings winGetSettings;
    private static ListManager listManager;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(StageAndSceneManager.getFxmlRootDirectory() + "MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.initStyle(StageStyle.UNDECORATED); // Window not dragable, implementation of WindowManager class pending
//        stage.setX((windowManager.getPrimaryScreenBounds().getWidth() / 2) - (windowManager.getDEFAULT_SPLASHSCREEN_WIDTH() / 2.0)); // implement WindowManager class
//        stage.setY((windowManager.getPrimaryScreenBounds().getHeight() / 2) - (windowManager.getDEFAULT_SPLASHSCREEN_HEIGHT() / 2.0)); // implement WindowManager class
        stage.setScene(scene);
        stage.getIcons().add(StageAndSceneManager.getTaskbarIcon());
        winGetSettings = new WinGetSettings();
        winGetSettings.setWinGetLanguage();
        listManager = new ListManager();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    public static WinGetSettings getWinGetSettings()
    {
        return winGetSettings;
    }

    public static ListManager getListManager()
    {
        return listManager;
    }
}