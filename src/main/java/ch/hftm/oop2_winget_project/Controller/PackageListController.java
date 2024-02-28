package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PackageListController {

    private ListManager listManager;
    private PackageList currentPackageList;

    @FXML
    private Button button_removePackageFromList;
    @FXML
    private TableView<WinGetPackage> tableView_packages;
    @FXML
    private TableColumn<WinGetPackage, String> column_name;
    @FXML
    private TableColumn<WinGetPackage, String> column_id;
    @FXML
    private TableColumn<WinGetPackage, String> column_version;
    @FXML
    private TableColumn<WinGetPackage, String> column_source;
    @FXML
    private ComboBox<String> comboBox_filter;
    @FXML
    private TextField textfield_filter;


    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.
        currentPackageList = listManager.getSelectedPackageList();
        System.out.println(listManager.getSelectedPackageList());
        System.out.println("Packages in the list: " + currentPackageList.getFXPackages().size());
        tableView_packages.setItems(currentPackageList.getFXPackages());

        // Initialize cell value factories, assuming WinGetPackage has appropriate properties
        column_name.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        column_id.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        column_version.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        column_source.setCellValueFactory(cellData -> cellData.getValue().getFXSource());

        // Initialize filter components
        comboBox_filter.getItems().addAll("All Attributes", "Name", "ID", "Version", "Source");
        comboBox_filter.setValue("All Attributes");
        textfield_filter.textProperty().addListener((observable, oldValue, newValue) -> filterList());
        comboBox_filter.valueProperty().addListener((observable, oldValue, newValue) -> filterList());
    }


    @FXML
    private void button_removePackageFromList() {
        WinGetPackage selectedPackage = tableView_packages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            currentPackageList.removePackage(selectedPackage);
            System.out.println("Package removed: " + selectedPackage.getName());
        } else {
            System.out.println("No package selected or no current list available.");
        }
    }

    private void filterList() {
        String filterText = textfield_filter.getText().toLowerCase();
        String selectedAttribute = comboBox_filter.getValue();

        if (filterText.isEmpty() || selectedAttribute == null) {
            tableView_packages.setItems(PackageList.getInstalledPackageList());
            return;
        }

        Stream<WinGetPackage> pkgStream = PackageList.getInstalledPackageList().stream();
        Predicate<WinGetPackage> filterPredicate = pkg -> {
            switch (selectedAttribute) {
                case "All Attributes":
                    return (pkg.getName() + " " + pkg.getId() + " " + pkg.getSource() + " " + pkg.getVersion()).toLowerCase().contains(filterText);
                case "Name":
                    return pkg.getName().toLowerCase().contains(filterText);
                case "ID":
                    return pkg.getId().toLowerCase().contains(filterText);
                case "Version":
                    return pkg.getVersion().toLowerCase().contains(filterText);
                case "Source":
                    return pkg.getSource().toLowerCase().contains(filterText);
                default:
                    return false;
            }
        };

        ObservableList<WinGetPackage> filteredList = pkgStream.filter(filterPredicate).collect(Collectors.toCollection(FXCollections::observableArrayList));

//        if(filteredList.isEmpty()){
//            setTableViewPlaceholder("No Packages found", false);
//        }

        tableView_packages.setItems(filteredList);
    }

}