package ch.hftm.oop2_winget_project;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.WinGetSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    private static WinGetSettings winGetSettings;
    private static ListManager listManager;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("WinGet Project");
        stage.setScene(scene);
        stage.setResizable(true);
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