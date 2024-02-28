package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Util.StageAndSceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ch.hftm.oop2_winget_project.Util.ResourceProvider;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.hftm.oop2_winget_project.Persistence.BatchFileCreator.createInstallScript;


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
    private TableView<PackageList> tableView_packageLists;
    @FXML
    private TableColumn<PackageList, String> column_name;
    @FXML
    private TableColumn<PackageList, Integer> column_size;
    @FXML
    private ComboBox<String> comboBox_filter;
    @FXML
    private TextField textfield_filter;

    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.

        // Set up the cell value factories for each column
        column_name.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        column_size.setCellValueFactory(cellData -> cellData.getValue().getFXSize().asObject());

        // Bind the data of listManager to the TableView
        tableView_packageLists.setItems(listManager.getFXLists());

        setUpDoubleClickOnRow();

        // Delete button countdown
        countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), this::deletePackageList));
        countdownTimer.setCycleCount(1); // Only run once
        countdownTimer.setOnFinished(event -> countdownTimer.stop()); // Stop the timer when finished

        // Initialize filter components
        comboBox_filter.getItems().addAll("All Attributes", "Name", "Size");
        comboBox_filter.setValue("All Attributes");
        textfield_filter.textProperty().addListener((observable, oldValue, newValue) -> filterList());
        comboBox_filter.valueProperty().addListener((observable, oldValue, newValue) -> filterList());
    }

    private void deletePackageList(ActionEvent actionEvent) {
        PackageList selectedPackageList = tableView_packageLists.getSelectionModel().getSelectedItem();
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
    private void buttoncreateBatchFile_onAction(){
        PackageList selectedPackageList = tableView_packageLists.getSelectionModel().getSelectedItem();
        if (selectedPackageList != null) {
            createInstallScript(selectedPackageList);
            System.out.println("Batch file created for: " + selectedPackageList.getName());
        } else {
            System.out.println("No PackageList selected.");
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
        tableView_packageLists.setRowFactory(tv -> {
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

    private void filterList() {
        String filterText = textfield_filter.getText().toLowerCase();
        String filterAttribute = comboBox_filter.getValue();

        if (filterText.isEmpty() || filterAttribute == null) {
            tableView_packageLists.setItems(ListManager.getInstance().getFXLists());
            return;
        }

        Stream<PackageList> pkgListStream = ListManager.getInstance().getFXLists().stream();
        Predicate<PackageList> filterPredicate = pkgList -> {
            switch (filterAttribute) {
                case "All Attributes":
                    return (pkgList.getName() + " " + pkgList.getSize()).toLowerCase().contains(filterText);
                case "Name":
                    return pkgList.getName().toLowerCase().contains(filterText);
                case "Size":
                    return String.valueOf(pkgList.getSize()).toLowerCase().contains(filterText);
                default:
                    return false;
            }
        };

        ObservableList<PackageList> filteredList = pkgListStream.filter(filterPredicate).collect(Collectors.toCollection(FXCollections::observableArrayList));

//        if(filteredList.isEmpty()){
//            setTableViewPlaceholder("No Packages found", false);
//        }

        tableView_packageLists.setItems(filteredList);
    }
}