package ch.hftm.oop2_winget_project.Service;

import ch.hftm.oop2_winget_project.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public final class StageAndSceneManager
{
    private static final String FXML_ROOT = "/Views/";
    private static final String ICONS_ROOT = "/Icons/";
    public static String getFxmlRootDirectory()
    {
        return FXML_ROOT;
    }
    private static final Image taskbarIcon = new Image(Objects.requireNonNull(App.class.getResourceAsStream(ICONS_ROOT + "taskbarIcon_colorized.png")));

    public static Parent loadFXML(String fxmlname) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(FXML_ROOT + fxmlname + ".fxml"));
        return fxmlLoader.load();
    }

    public static void loadFxmlToBorderPaneLeft(BorderPane borderPane, String fxmlName)
    {
        try
        {
            borderPane.setLeft(loadFXML(fxmlName));
        }
        catch(IOException e)
        {
            // Exception handler?
        }
    }

    public static void setSceneRoot(Scene scene, String fxmlname) throws IOException { scene.setRoot(loadFXML(fxmlname)); }

    public static void openNewWindow(String fxmlName, Modality windowType) throws IOException
    {
        Parent root;
        root = loadFXML(fxmlName);
        Stage stage = new Stage();
        stage.initModality(windowType);
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void closeActiveWindow(ActionEvent event)
    {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public static Image getTaskbarIcon()
    {
        return taskbarIcon;
    }
}
