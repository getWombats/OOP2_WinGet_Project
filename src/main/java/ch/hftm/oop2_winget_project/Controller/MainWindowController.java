package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import ch.hftm.oop2_winget_project.Util.StageAndSceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable
{
    @FXML
    private BorderPane mainWindowBorderPane;
    @FXML
    private ToggleButton maximizeWindowButton;
    @FXML
    private AnchorPane titleBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        registerInputServices();
        // Start with search packages view
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.SEARCHPACKAGES_VIEW_NAME);
    }

    @FXML
    private void menuSearchButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.SEARCHPACKAGES_VIEW_NAME);
    }

    @FXML
    private void menuUpdateButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.UPGRADEPACKAGES_VIEW_NAME);
    }

    @FXML
    private void menuInstalledPackagesButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.INSTALLEDPACKAGES_VIEW_NAME);
    }

    @FXML
    private void menuFavouritePackagesButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.INSTALLEDPACKAGES_VIEW_NAME);
    }


    @FXML
    private void menuListManagerButtonClick()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.LISTMANAGER_VIEW_NAME);
    }



    @FXML
    private void minimizeWindowButtonClick()
    {
        App.getAppInstance().getAppWindowManager().minimizeWindow();
    }

    @FXML
    private void maximizeWindowButtonClick()
    {
        if(maximizeWindowButton.isSelected())
        {
            App.getAppInstance().getAppWindowManager().maximizeWindow();
        }
        else
        {
            App.getAppInstance().getAppWindowManager().restoreWindow();
        }
    }

    @FXML
    private void closeWindowButtonClick()
    {
        App.getAppStage().close();
    }

    private void registerInputServices()
    {
        // Listen for Window movement on titleBar
        titleBar.setOnMouseDragged(event -> {
            if(App.getAppStage().getMaxWidth() == App.getAppInstance().getAppWindowManager().getPrimaryScreenBounds().getWidth())
            {
                setMaximizeWindowButtonToggle(false);
                App.getAppInstance().getAppWindowManager().restoreWindow();
            }
            else
            {
                App.getAppStage().setX(event.getScreenX() - App.getAppInstance().getAppWindowManager().getxOffset());
                App.getAppStage().setY(event.getScreenY() - App.getAppInstance().getAppWindowManager().getyOffset());
            }
        });

        // Doubleclick on titleBar maximize / reset Window size
        titleBar.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
            {
                if(!getMaximizeWindowButtonToggle())
                {
                    App.getAppInstance().getAppWindowManager().maximizeWindow();
                    setMaximizeWindowButtonToggle(true);
                }
                else
                {
                    setMaximizeWindowButtonToggle(false);
                    App.getAppInstance().getAppWindowManager().restoreWindow();
                }
            }
        });

        // Mouse Event to make Window movable
        titleBar.setOnMousePressed(event -> {
            if(App.getAppStage().getMaxWidth() != App.getAppInstance().getAppWindowManager().getPrimaryScreenBounds().getWidth())
            {
                App.getAppInstance().getAppWindowManager().setxOffset(event.getSceneX());
                App.getAppInstance().getAppWindowManager().setyOffset(event.getSceneY());
            }
        });

        // Listen for Screen changes and set active Screen
        App.getAppStage().xProperty().addListener((observable, oldValue, newValue) -> {
            Screen newScreen = Screen.getScreensForRectangle(newValue.doubleValue(), App.getAppInstance().getAppWindowManager().getPrimaryScreenBounds().getMinY(), 1, 1).get(0);
            App.getAppInstance().getAppWindowManager().setPrimaryScreenBounds(newScreen.getVisualBounds());
        });
    }
    private void setMaximizeWindowButtonToggle(Boolean toggle)
    {
        maximizeWindowButton.setSelected(toggle);
    }
    private boolean getMaximizeWindowButtonToggle()
    {
        return maximizeWindowButton.isSelected();
    }

    public BorderPane getMainWindowBorderPane() {
        return mainWindowBorderPane;
    }


    public void switchMainWindowToPackageList()
    {
        StageAndSceneManager.loadFxmlToBorderPaneLeft(mainWindowBorderPane, ResourceProvider.PACKAGELIST_VIEW_NAME);
    }
}