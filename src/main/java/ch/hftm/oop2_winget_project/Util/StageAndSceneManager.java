package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Controller.MainWindowController;
import ch.hftm.oop2_winget_project.Model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public final class StageAndSceneManager
{
    public static Parent loadFXML(String fxmlname) throws IOException
    {
        FXMLLoader fxmlLoader;
        try
        {
            fxmlLoader = new FXMLLoader(App.class.getResource(ResourceProvider.FXML_ROOT + fxmlname));
            return fxmlLoader.load();
        }
        catch(IOException ex)
        {
            Message.showErrorDialog(ex.getMessage());
            return null;
        }
    }

    public static void loadFxmlToBorderPaneLeft(BorderPane borderPane, String fxmlName)
    {
        try
        {
            borderPane.setLeft(loadFXML(fxmlName));
        }
        catch(IOException ex)
        {
            Message.showErrorDialog(ex.getMessage());
        }
    }

    public static void setSceneRoot(Scene scene, String fxmlname)
    {
        try
        {
            scene.setRoot(loadFXML(fxmlname));
        }
        catch (IOException ex)
        {
            Message.showErrorDialog(ex.getMessage());
        }
    }

    public static void openNewWindow(String fxmlName, Modality windowType)
    {
        Parent root;
        try
        {
            root = loadFXML(fxmlName);
            Stage stage = new Stage();
            stage.initModality(windowType);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ex)
        {
            Message.showErrorDialog(ex.getMessage());
        }
    }

    public static void closeActiveWindow(ActionEvent event)
    {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
