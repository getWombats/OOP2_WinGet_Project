package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Model.WindowManager;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import ch.hftm.oop2_winget_project.Util.ListProvider;
import ch.hftm.oop2_winget_project.Util.QueryType;
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
        new Thread(() -> {
            try
            {
                // Preload installed packages
                WinGetQuery query = new WinGetQuery();
                query.queryToList(QueryType.LIST, "", ListProvider.getInstalledPackageList());
//                Thread.sleep(200);
            }
            catch (IOException | InterruptedException ex) // catch (IOException | InterruptedException ex)
            {
                System.out.println(ex.getMessage());
            }

            Platform.runLater(() -> {
                Stage stage = new Stage();

                // WindowManager instance for Window Actions
                WindowManager windowManager = new WindowManager(stage);

                // Pass new windowManager object to app instance
                App.getAppInstance().setWindowManager(windowManager);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceProvider.FXML_ROOT + ResourceProvider.MAINWINDOW_VIEW_NAME));
                Parent root;
                try
                {
                    root = fxmlLoader.load();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }

                Scene scene = new Scene(root, windowManager.getDEFAULT_WINDOW_WIDTH(), windowManager.getDEFAULT_WINDOW_HEIGHT());

                // Set Taskbar Icon
                stage.getIcons().add(ResourceProvider.getTaskbarIcon());

                // Borderless Window style
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);

                // Pass new stage object to app instance
                App.setAppStage(stage);

                // Show mainWindow
                stage.show();
//                ResizeHelper.addResizeListener(stage); // ResizeHelper not implemented

                // Close splash screen
                splashScreenRootPane.getScene().getWindow().hide();
            });
        }).start();
    }
}
