package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.PackageList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PackageListController {

    private ListManager listManager;
    private PackageList currentPackageList;

    @FXML
    private Button button_removePackageFromList;
    @FXML
    private TableView<WinGetPackage> tableView_Packages;
    @FXML
    private TableColumn<WinGetPackage, String> column_name;
    @FXML
    private TableColumn<WinGetPackage, String> column_id;
    @FXML
    private TableColumn<WinGetPackage, String> column_version;
    @FXML
    private TableColumn<WinGetPackage, String> column_source;


    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.
        currentPackageList = listManager.getSelectedPackageList();
        System.out.println(listManager.getSelectedPackageList());
        System.out.println("Packages in the list: " + currentPackageList.getPackages().size());
        tableView_Packages.setItems(currentPackageList.getPackages());

        // Initialize cell value factories, assuming WinGetPackage has appropriate properties
        column_name.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        column_id.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        column_version.setCellValueFactory(cellData -> cellData.getValue().getVersionProperty());
        column_source.setCellValueFactory(cellData -> cellData.getValue().getSourceProperty());
    }


    @FXML
    private void button_removePackageFromList() {
        WinGetPackage selectedPackage = tableView_Packages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            currentPackageList.getPackages().remove(selectedPackage);
            System.out.println("Package removed: " + selectedPackage.getName());
        } else {
            System.out.println("No package selected or no current list available.");
        }
    }
}