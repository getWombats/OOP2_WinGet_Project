package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Util.StageAndSceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.scene.control.*;
import javafx.util.Duration;


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
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        System.out.println("Hello");
        listSizeColumn.setCellValueFactory(cellData -> cellData.getValue().getFXSize().asObject());

        // Bind the data of listManager to the TableView
        listManagerTableView.setItems(listManager.getFXLists());

        setUpDoubleClickOnRow();

        // Delete button countdown
        countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), this::deletePackageList));
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
                    // Switch to PackageListView
                    try {
                        StageAndSceneManager.loadFxmlToBorderPaneLeft(App.GetMainWindowController().getMainWindowBorderPane(), ResourceProvider.PACKAGELIST_VIEW_NAME);  // Gib mir das BorderPane
                    } catch(Exception ex) {
                        System.out.println(ex.getCause());
                        System.out.println(ex.getLocalizedMessage());
                        ex.printStackTrace();
                    }
                }
            });
            return row;
        });

    }
}