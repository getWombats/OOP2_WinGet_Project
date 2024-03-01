package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Persistence.Serializer;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Util.SourceType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
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
    private TableColumn<WinGetPackage, String> column_id;
    @FXML
    private TableColumn<WinGetPackage, String> column_name;
    @FXML
    private TableColumn<WinGetPackage, String> column_source;
    @FXML
    private TableColumn<WinGetPackage, String> column_version;
    @FXML
    private TableColumn<WinGetPackage, Void> column_install;
    @FXML
    private ComboBox<String> comboBox_filter;
    @FXML
    private TextField textField_filter;
    @FXML
    private Label label_title;

    private boolean isThreadWorking;


    @FXML
    private void initialize() {

        listManager = ListManager.getInstance(); //Getting the single instance of ListManager.
        currentPackageList = listManager.getSelectedPackageList();
        System.out.println(listManager.getSelectedPackageList());
        System.out.println("Packages in the list: " + currentPackageList.getFXPackages().size());

        label_title.setText(currentPackageList.getName());

        tableView_packages.setItems(currentPackageList.getFXPackages());

        initializeTableViewData();
        addButtonToTableView();
        setSourceColumnLabel();
        initializeFilter();
    }

    private void initializeTableViewData() {
        column_name.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        column_id.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        column_version.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        column_source.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }
        // Initialize filter components
    private void initializeFilter() {
        comboBox_filter.getItems().addAll("All Attributes", "Name", "ID", "Version", "Source");
        comboBox_filter.setValue("All Attributes");
        textField_filter.textProperty().addListener((observable, oldValue, newValue) -> filterList());
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
        Serializer.serializeListManager();
    }

    private void filterList() {
        String filterText = textField_filter.getText().toLowerCase();
        String selectedAttribute = comboBox_filter.getValue();

        if (filterText.isEmpty() || selectedAttribute == null) {
            tableView_packages.setItems(currentPackageList.getFXPackages());
            return;
        }

        Stream<WinGetPackage> pkgStream = currentPackageList.getFXPackages().stream();
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

    private void setSourceColumnLabel() {
        column_source.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Label sourceLabel = new Label();
                sourceLabel.getStyleClass().removeAll("label-ms-store", "label-winget");
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                }
                else if(item.equals(SourceType.MSSTORE.toString())){
                    sourceLabel.getStyleClass().add("label-ms-store");
                    sourceLabel.setText(SourceType.MSSTORE.toString());
                    setGraphic(sourceLabel);
                }
                else if(item.equals(SourceType.WINGET.toString())){
                    sourceLabel.getStyleClass().add("label-winget");
                    sourceLabel.setText(SourceType.WINGET.toString());
                    setGraphic(sourceLabel);
                }
                else {
                    Label nameLabel = new Label(item);
                    setGraphic(nameLabel);
                }
            }
        });
    }


//    @Override
    public void addButtonToTableView()
    {
        Callback<TableColumn<WinGetPackage, Void>, TableCell<WinGetPackage, Void>> cellFactory = new Callback<>()
        {
            @Override
            public TableCell<WinGetPackage, Void> call(final TableColumn<WinGetPackage, Void> param)
            {
                Label installedLabel = new Label("Installed");
                installedLabel.getStyleClass().removeAll("label-installed"); // Reset styles
                final TableCell<WinGetPackage, Void> cell = new TableCell<>()
                {
                    private final Button btn = new Button("Install");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage selectedItem = getTableView().getItems().get(getIndex());

                            btn.setDisable(true); // Disables button when clicked and package installs
                            isThreadWorking = true;
                            setGraphic(new ProgressBar());
                            new Thread(() -> {
                                try
                                {
                                    installPackage(selectedItem.getId());
                                }
                                catch (IOException ex)
                                {
                                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                                }

                                Platform.runLater(() -> {
                                    // Update list
                                    PackageList.getInstalledPackageList().add(selectedItem);
                                    selectedItem.setInstalled(true); // Set package as installed
                                    installedLabel.getStyleClass().add("label-installed");
                                    setGraphic(installedLabel);
                                    isThreadWorking = false;
                                });
                            }).start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            WinGetPackage selectedItem = getTableView().getItems().get(getIndex());
                            if(selectedItem.isInstalled()) {
                                // Set cell content when package is installed already
                                installedLabel.getStyleClass().add("label-installed");
                                setGraphic(installedLabel);
                            } else {
                                btn.getStyleClass().add("button-install");
                                setGraphic(btn);
                            }
                        }
                    }
                };
                return cell;
            }
        };
        column_install.setCellFactory(cellFactory);
    }

    private void installPackage(String packageId) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("winget.exe", QueryType.INSTALL.toString(), packageId, "--accept-package-agreements", "--accept-source-agreements");
        processBuilder.redirectErrorStream(true);
        processBuilder.start();
    }

}