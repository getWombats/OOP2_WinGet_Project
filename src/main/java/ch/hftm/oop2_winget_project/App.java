package ch.hftm.oop2_winget_project;

import ch.hftm.oop2_winget_project.Models.WinGetSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    public static WinGetSettings winGetSettings;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("WinGet Project");
        stage.setScene(scene);
        stage.setResizable(false);

        winGetSettings = new WinGetSettings();
        winGetSettings.setWinGetLanguage();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}