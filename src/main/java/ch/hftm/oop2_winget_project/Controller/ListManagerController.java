package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Util.ListManager;
import ch.hftm.oop2_winget_project.Util.PackageList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class ListManagerController {

    @FXML
    private Button createPackageListButton;
    @FXML
    private Button deletePackageListButton;


    @FXML
    private TableView<PackageList> listManagerTableView;
    @FXML
    private TableColumn<PackageList, String> nameColumn;
    @FXML
    private TableColumn<PackageList, Integer> listSizeColumn;
//    @FXML
//    private void initialize() {
//    }
    @FXML
    private void createPackageListButton_onAction(){

    }
    @FXML
    private void deletePackageListButton_onAction(){
    }
}
