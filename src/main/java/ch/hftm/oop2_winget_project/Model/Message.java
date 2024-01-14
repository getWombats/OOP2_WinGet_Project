package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.App;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class Message
{
    private double centerStageX;
    private double centerStageY;
    private final WindowManager windowManager = App.getAppInstance().getAppWindowManager();
    private Alert alert;

    public Message()
    {

    }

    public void show(Alert.AlertType type, String title,String headerText ,String message)
    {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkTheme.css")).toExternalForm()); // nullPointer?
//        dialogPane.getStyleClass().add("myDialog");

        // Positioning
        centerStageX = (windowManager.getStage().getX() + windowManager.getStage().getMinWidth()/2);
        centerStageY = (windowManager.getStage().getY() + windowManager.getStage().getMinHeight()/2);
        alert.setX(centerStageX - dialogPane.getWidth()/2);
        alert.setY(centerStageY - dialogPane.getHeight()/2);

        alert.showAndWait();
    }

//    public void show(Alert.AlertType type, String title, String headerText , String message, ButtonType b1, ButtonType b2)
//    {
//        alert = new Alert(type, message ,b1, b2);
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
////        alert.setContentText(message);
//        alert.initStyle(StageStyle.UNDECORATED);
//
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkTheme.css")).toExternalForm());
//        dialogPane.getStyleClass().add("myDialog");
//
//        // Positioning
//        centerStageX = (windowManager.getStage().getX() + windowManager.getStage().getMinWidth()/2);
//        centerStageY = (windowManager.getStage().getY() + windowManager.getStage().getMinHeight()/2);
//        alert.setX(centerStageX - dialogPane.getWidth()/2);
//        alert.setY(centerStageY - dialogPane.getHeight()/2);
//
//        alert.showAndWait();
//    }

    public void showNotification(String title, String message)
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
