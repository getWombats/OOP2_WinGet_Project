package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Features.WinGetQuery;
import ch.hftm.oop2_winget_project.Utils.StageAndSceneManager;
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
        WinGetQuery.setWinGetLanguage();
        StageAndSceneManager.loadFxmlToBorderPaneCenter(mainWindowBorderPane, "Views/SearchPackages");
    }
}