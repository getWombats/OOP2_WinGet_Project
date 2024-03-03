package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.util.Objects;

public class Message
{
    private static final Image windowTitleIcon = new Image(Objects.requireNonNull(Message.class.getResourceAsStream(ResourceProvider.ICONS_ROOT + "windowIcon16x16.png")));
    private static final WindowManager windowManager = App.getAppInstance().getAppWindowManager();

    public static ButtonBar.ButtonData showConfirmationDialog(String dialogText, String titleText)
    {
        ButtonType confirmButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", cancelButton, confirmButton);
        alert.setTitle(titleText);
        alert.setHeaderText(dialogText);
        alert.initStyle(StageStyle.DECORATED);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(windowTitleIcon);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Message.class.getResource("/CSS/DarkTheme.css").toExternalForm());

        // Positioning
        alert.setX(windowManager.getStage().getX() + (windowManager.getStage().getWidth()/2) - (dialogPane.getWidth()/2));
        alert.setY(windowManager.getStage().getY() + 30);

        alert.showAndWait();
        return alert.getResult().getButtonData();
    }

    public static ButtonBar.ButtonData showErrorDialog(String errorMessage)
    {
        ButtonType confirmButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.ERROR,"", confirmButton);
        alert.setTitle("Error");
        alert.setHeaderText(errorMessage);
        alert.initStyle(StageStyle.DECORATED);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(windowTitleIcon);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Message.class.getResource("/CSS/DarkTheme.css").toExternalForm());

        // Positioning
        alert.setX(windowManager.getStage().getX() + (windowManager.getStage().getWidth()/2) - (dialogPane.getWidth()/2));
        alert.setY(windowManager.getStage().getY() + 30);

        alert.showAndWait();
        return alert.getResult().getButtonData();
    }

    public static void showNotification(String title, String message)
    {
        Notifications.create()
                .title(title)
                .text(message)
                .action()
                .position(Pos.TOP_CENTER)
                .hideAfter(Duration.seconds(2))
                .darkStyle()
//                .graphic(notificationOk)
                .hideCloseButton()
                .show();
    }
}
