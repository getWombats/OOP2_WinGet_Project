package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Model.Message;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Model.WindowManager;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable
{
    @FXML
    private Pane splashScreenRootPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        applicationLoad();
    }

    private void applicationLoad()
    {
        WinGetQuery installedPackages = new WinGetQuery(QueryType.LIST);
        WinGetQuery availableUpdates = new WinGetQuery(QueryType.UPGRADE);
        new Thread(() -> {
            try
            {
                // Preload installed packages
                installedPackages.queryToList("");
                availableUpdates.queryToList("");
            }
            catch (InterruptedException | IOException ex)
            {
                Message.showErrorDialog(ex.getMessage());
            }

            Platform.runLater(() -> {
                installedPackages.CreatePackageList(PackageList.getInstalledPackageList());
                availableUpdates.CreateUpdateList(PackageList.getUpgradePackageList());
                Stage stage = new Stage();

                // WindowManager instance for Window Actions
                WindowManager windowManager = new WindowManager(stage);

                // Pass new windowManager object to app instance
                App.getAppInstance().setWindowManager(windowManager);

                FXMLLoader fxmlLoader;
                Parent root;
                try
                {
                    fxmlLoader = new FXMLLoader(getClass().getResource(ResourceProvider.FXML_ROOT + ResourceProvider.MAINWINDOW_VIEW_NAME));
                    root = fxmlLoader.load();

                    Scene scene = new Scene(root, windowManager.getDEFAULT_WINDOW_WIDTH(), windowManager.getDEFAULT_WINDOW_HEIGHT());

                    // Set Taskbar Icon
                    stage.getIcons().add(ResourceProvider.getTaskbarIcon());

                    // Borderless Window style
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);

                    // Pass new stage object to app instance
                    App.setAppStage(stage);
                    App.setMainWindowControllerInstance(fxmlLoader.getController());

                    // Show mainWindow
                    stage.show();

                    // Close splash screen
                    splashScreenRootPane.getScene().getWindow().hide();
                }
                catch (IOException ex)
                {
                    Message.showErrorDialog(ex.getMessage());
                }
            });
        }).start();
    }
}
