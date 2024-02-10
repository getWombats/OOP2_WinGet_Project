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
    private TableColumn<WinGetPackage, String> column_version;


    public void setCurrentPackageList(PackageList packageList) {
        this.currentPackageList = packageList;
        tableView_Packages.setItems(packageList.getPackages());
        // Initialize cell value factories, assuming WinGetPackage has appropriate properties
        column_name.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
//        column_version.setCellValueFactory(cellData -> cellData.getValue().versionProperty());
    }

    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.
        currentPackageList = listManager.getSelectedPackageList();
    }
}