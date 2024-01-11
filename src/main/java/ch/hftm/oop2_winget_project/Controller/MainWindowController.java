package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Service.StageAndSceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable
{
    @FXML
    private BorderPane mainWindowBorderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Start with search packages view
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, "Views/SearchPackages");
    }

    @FXML
    private void menuSearchButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, "Views/SearchPackages");
    }

    @FXML
    private void menuUpdateButtonClick()
    {
//        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, "Views/UpdatePackages");
    }

    @FXML
    private void menuInstalledPackagesButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, "Views/InstalledPackages");
    }
}