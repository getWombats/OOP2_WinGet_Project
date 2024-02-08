package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Util.ListManager;
import ch.hftm.oop2_winget_project.Util.PackageList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class ListManagerController {

    private ListManager listManager;
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
        listManager = new ListManager();

        // Set up the cell value factories for each column
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        listSizeColumn.setCellValueFactory(cellData -> cellData.getValue().getSizeProperty().asObject());

        // Bind the data of listManager to the TableView
        listManagerTableView.setItems(listManager.getLists());
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
        PackageList selectedPackageList = listManagerTableView.getSelectionModel().getSelectedItem();
        if (selectedPackageList != null) {
            listManager.deletePackageList(selectedPackageList);
        }
    }
}
