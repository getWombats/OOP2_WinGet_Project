package ch.hftm.oop2_winget_project;

import ch.hftm.oop2_winget_project.Features.WinGetQuery;
import ch.hftm.oop2_winget_project.Utils.SystemLanguage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1180, 480);
        stage.setTitle("WinGet Project");
        stage.setScene(scene);
        Locale defaultLocale = Locale.getDefault();
        System.out.println(defaultLocale);
        String displayCountry = defaultLocale.getDisplayCountry();
        System.out.println(displayCountry);
        String language = defaultLocale.getLanguage();
        System.out.println(language);
        String country = defaultLocale.getCountry();
        System.out.println(country);

        WinGetQuery.setWinGetLanguage(); // Get system language and set
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}