package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Util.StageAndSceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ch.hftm.oop2_winget_project.Controller.MainWindowController;
import java.io.IOException;


public class ListManagerController {

    private ListManager listManager;
    private Timeline countdownTimer;
    @FXML
    private Button button_createPackageList;
    @FXML
    private Button button_deletePackageList;
    @FXML
    private TextField textfield_PackageListName;


    @FXML
    private TableView<PackageList> listManagerTableView;
    @FXML
    private TableColumn<PackageList, String> nameColumn;
    @FXML
    private TableColumn<PackageList, Integer> listSizeColumn;

    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.

        // Set up the cell value factories for each column
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        listSizeColumn.setCellValueFactory(cellData -> cellData.getValue().getSizeProperty().asObject());

        // Bind the data of listManager to the TableView
        listManagerTableView.setItems(listManager.getLists());

        setUpDoubleClickOnRow();

        // Delete button countdown
        countdownTimer = new Timeline(new KeyFrame(Duration.seconds(3), this::deletePackageList));
        countdownTimer.setCycleCount(1); // Only run once
        countdownTimer.setOnFinished(event -> countdownTimer.stop()); // Stop the timer when finished
    }

    private void deletePackageList(ActionEvent actionEvent) {
        PackageList selectedPackageList = listManagerTableView.getSelectionModel().getSelectedItem();
        if (selectedPackageList != null) {
            listManager.deletePackageList(selectedPackageList);
        }
    }

    @FXML
    private void createPackageListButton_onAction(){
        String name = textfield_PackageListName.getText();
        if (!name.isBlank()) {
            listManager.createPackageList(name);
            System.out.println("Created new PackageList: " + name);
        }
    }

    @FXML
    private void deletePackageListButton_onAction(){
//        PackageList selectedPackageList = listManagerTableView.getSelectionModel().getSelectedItem();
//        if (selectedPackageList != null) {
//            listManager.deletePackageList(selectedPackageList);
//        }
    }
    @FXML
    private void deletePackageListButton_MousePressed(){
        countdownTimer.play();
    }

    @FXML
    private void deletePackageListButton_MouseReleased(){
        countdownTimer.stop();
    }


//    Event Handlers / Listeners
    private void setUpDoubleClickOnRow() {
        listManagerTableView.setRowFactory(tv -> {
            TableRow<PackageList> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    listManager.setSelectedPackageList(row.getItem());
                    System.out.println(listManager.getSelectedPackageList().toString());
                }
            });
            return row;
        });
    }

//    private BorderPane load1()
//    {
//        try
//        {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("PackageList.fxml"));
//            Parent root = loader.load();
//            MainWindowController controller = loader.getController();
//            return controller.getMainWindowBorderPane();
//        }
//        catch(IOException ex)
//        {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }


//    public void loadPackageListView(String fxmlPath) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/ListManagerView.fxml"));
//            Parent listManagerView = loader.load();
//            ListManagerController listManagerController = loader.getController();
//            listManagerController.setMainWindowController(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}