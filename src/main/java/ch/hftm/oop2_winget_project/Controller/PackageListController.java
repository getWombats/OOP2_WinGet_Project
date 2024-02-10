package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.PackageList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PackageListController {

    private ListManager listManager;
    private PackageList currentPackageList;

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
        column_name.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        column_id.setCellValueFactory(cellData -> cellData.getValue().packageIDProperty());
        column_version.setCellValueFactory(cellData -> cellData.getValue().packageVersionProperty());
        column_source.setCellValueFactory(cellData -> cellData.getValue().packageSourceProperty());
    }
}