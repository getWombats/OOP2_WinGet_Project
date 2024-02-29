package ch.hftm.oop2_winget_project;

import ch.hftm.oop2_winget_project.Controller.MainWindowController;
import ch.hftm.oop2_winget_project.Model.*;
import ch.hftm.oop2_winget_project.Persistence.Serializer;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application
{
    private static App appInstance;
    private static Stage mainStage;
    private static MainWindowController mainWindowController;
    private WindowManager windowManager;
    private WinGetSettings winGetSettings;
    public ListManager listManager;
    public ListManagerDTO listManagerDTO;


    /*
    * Appication start order:
    * 1. Execution App class main() method, executes launch()
    * 2. Execution App class init() method
    * 3. Execution App class start() method
    * 4. Execution MainWindowController class initialize() -> or whatever fxml will be loaded first
    * */

    //Entry point for Java application. (main > init > start)
    public static void main(String[] args)
    {
        launch();
    }

    @Override // Initializes the JavaFX elements. (main > init > start)
    public void init()
    {
        // Set application instance
        appInstance = this;

        winGetSettings = new WinGetSettings();
        winGetSettings.setWinGetLanguage();

    }

    @Override // Starts up the JavaFX UI. (main > init > star)
    public void start(Stage stage) throws IOException
    {
        listManager = ListManager.getInstance(); // Instantiating ListManager
        listManagerDTO = ListManagerDTO.getInstance(); // Instantiating ListManager

        Serializer.deserializeListManager(); // Converts .ser into listManager

        windowManager = new WindowManager(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ResourceProvider.FXML_ROOT + ResourceProvider.SPLASHSCREEN_VIEW_NAME));
        Scene scene = new Scene(fxmlLoader.load());

        // Set styles
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX((windowManager.getPrimaryScreenBounds().getWidth() / 2) - (windowManager.getDEFAULT_SPLASHSCREEN_WIDTH() / 2.0));
        stage.setY((windowManager.getPrimaryScreenBounds().getHeight() / 2) - (windowManager.getDEFAULT_SPLASHSCREEN_HEIGHT() / 2.0));
        stage.setScene(scene);
        stage.getIcons().add(ResourceProvider.getTaskbarIcon());

        // Init stage object
        mainStage = stage;
        stage.show();
    }

    public static App getAppInstance()
    {
        return appInstance;
    }
    public static Stage getAppStage()
    {
        return mainStage;
    }
    public static void setAppStage(Stage stage)
    {
        mainStage = stage;
    }
    public void setWindowManager(WindowManager windowManager)
    {
        this.windowManager = windowManager;
    }
    public WindowManager getAppWindowManager()
    {
        return windowManager;
    }
    public WinGetSettings getWinGetSettings()
    {
        return winGetSettings;
    }

    public static void setMainWindowControllerInstance(MainWindowController ctrl) // Gib mir das BorderPane
    {
        mainWindowController = ctrl;
    }

    public static MainWindowController GetMainWindowController() // Gib mir das BorderPane
    {
        return mainWindowController;
    }
}