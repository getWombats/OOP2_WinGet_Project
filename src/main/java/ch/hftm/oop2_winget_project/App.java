package ch.hftm.oop2_winget_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1180, 480);
        stage.setTitle("WinGet Project OOP2 blubb");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}