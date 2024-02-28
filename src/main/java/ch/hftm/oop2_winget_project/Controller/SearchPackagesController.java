package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.*;
import ch.hftm.oop2_winget_project.Persistence.Serializer;
import ch.hftm.oop2_winget_project.Util.*;
import ch.hftm.oop2_winget_project.Api.IControllerBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchPackagesController implements IControllerBase, Initializable
{
    @FXML
    private TableView<WinGetPackage> tableView;
    @FXML
    private TableColumn<WinGetPackage, Boolean> column_favourite;
    @FXML
    private TableColumn<WinGetPackage, String> column_name;
    @FXML
    private TableColumn<WinGetPackage, String> column_id;
    @FXML
    private TableColumn<WinGetPackage, String> column_source;
    @FXML
    private TableColumn<WinGetPackage, String> column_version;
    @FXML
    private TableColumn<WinGetPackage, Void> column_button_install;
    @FXML
    private TextField keywordTextField;
    @FXML
    private Label tableViewPlaceholderLabel;
    @FXML
    private VBox placeholderContent;
    @FXML
    private ComboBox<PackageList> comboBox_selectPackageList; // The comboBox for PackageList selection
    @FXML
    private Button button_addPackageToList;
    private boolean isThreadWorking;
    private PackageList selectedPackageList; // Stores the selected PackageList from the comboBox.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        placeholderContent = new VBox(10);
        tableViewPlaceholderLabel = new Label();
        setTableViewPlaceholder("Enter search keyword", false);

        addButtonToTableView();
        addFavoriteCheckboxToTableView();
        setTableViewData();
        setTableViewSource();
        registerInputServices();
        initialize_comboBox_PackageList();
    }

    @FXML
    private void testButtonClick()
    {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.show();
        // Test message
        Message msg = new Message();
        msg.show(Alert.AlertType.INFORMATION, "title", "headertext", "message");
        // Test notification
//        Message notify = new Message();
//        notify.showNotification("title", "message");
    }

    @FXML
    private void searchButtonClick()
    {
        searchPackages();
    }

    @Override
    public void setTableViewData()
    {
//        this.favoriteColumn.setCellFactory(CheckBoxTableCellTest.forTableColumn(favoriteColumn)); // Test with checkbox
//        this.favoriteColumn.setCellValueFactory(cellData -> cellData.getValue().isFavoriteProperty()); // Test with checkbox
        this.column_name.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        this.column_id.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        this.column_version.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        this.column_source.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }

    @Override
    public void setTableViewSource()
    {
        this.tableView.setItems(PackageList.getSearchPackageList());
    }

    @Override
    public void refreshTableViewContent()
    {
        tableView.setItems(null);
        setTableViewSource();
    }

    private void registerInputServices()
    {
        // Register event for enter key
        keywordTextField.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER )
            {
                searchPackages();
            }
        } );

        // Set input validation
        keywordTextField.setTextFormatter(InputValidator.createValidator());
    }

    @Override
    public WinGetPackage getObjectFromSelection()
    {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void addButtonToTableView()
    {
        Callback<TableColumn<WinGetPackage, Void>, TableCell<WinGetPackage, Void>> cellFactory = new Callback<>()
        {
            @Override
            public TableCell<WinGetPackage, Void> call(final TableColumn<WinGetPackage, Void> param)
            {
                Label installedLabel = new Label("installed");
                final TableCell<WinGetPackage, Void> cell = new TableCell<>()
                {
                    private final Button btn = new Button("Install");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            data.setInstalled(true); // Set package as installed
                            btn.setDisable(true); // Disables button when clicked

                            isThreadWorking = true;
                            setGraphic(new ProgressBar());
                            new Thread(() -> {
                                try
                                {
                                    installPackage(data.getId());
                                }
                                catch (IOException ex)
                                {
                                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                                }

                                Platform.runLater(() -> {
                                    // Update list
                                    PackageList.getInstalledPackageList().add(data);
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
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            if(data.isInstalled()) {
                                // Set cell content when package is installed already
                                setGraphic(installedLabel);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    }
                };
                return cell;
            }
        };
        column_button_install.setCellFactory(cellFactory);
    }

//    private void addFavoriteCheckboxToTableView()
//    {
//        favoriteColumn.setCellFactory(column -> new CheckBoxTableCell<>()
//        {
//            @Override
//            public void updateItem(Boolean item, boolean empty)
//            {
//                super.updateItem(item, empty);
//
//                if (empty)
//                {
//                    setGraphic(null);
//                }
//                else
//                {
//                    CheckBox checkBox = new CheckBox();
//                    WinGetPackage model = getTableView().getItems().get(getIndex());
//
//                    if(model.isFavorite())
//                    {
//                        checkBox.setSelected(true);
//                    }
//
//                    checkBox.selectedProperty().setValue(model.isFavorite()); // Bind checkbox state to item value
//                    checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
//                        model.setFavorite(isNowSelected);
//
//                        if (isNowSelected)
//                        {
//                            onFavoriteCheckboxChecked(model);
//                        }
//                        else
//                        {
//                            onFavoriteCheckboxUnchecked(model);
//                        }
//                    });
//                    setGraphic(checkBox);
//                }
//            }
//        });
//    }

    private void addFavoriteCheckboxToTableView() {
        column_favourite.setCellFactory(column -> new CheckBoxTableCell<WinGetPackage, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    WinGetPackage model = getTableView().getItems().get(getIndex());
                    // Disable the checkbox if no package list is selected
                    checkBox.setDisable(selectedPackageList == null);

                    // Check if the package is in the selected favorite list and update the checkbox.
                    checkBox.setSelected(selectedPackageList != null && selectedPackageList.getFXPackages().stream().anyMatch(pkg -> pkg.getId().equals(model.getId())));
                    // Listener
                    checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                        if (selectedPackageList != null) {
                            if (isNowSelected) {
                                selectedPackageList.addPackage(model);
                                Serializer.serializeListManager();
                            } else {
                                selectedPackageList.removePackage(model);
                                Serializer.serializeListManager();
                            }
                        }
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }

    private void searchPackages()
    {
        String searchKeyword = keywordTextField.getText().trim();

        if (!searchKeyword.isEmpty() && !isThreadWorking)
        {
            if (tableView.getItems() != null)
            {
                tableView.getItems().clear();
            }

            setTableViewPlaceholder("Searching packages", true);

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery(QueryType.SEARCH);

                try
                {
                    query.queryToList(searchKeyword);
                }
                catch (IOException | InterruptedException ex)
                {
                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                }

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
                        query.CreatePackageList(PackageList.getSearchPackageList());
                        refreshTableViewContent();
                    }
                    else
                    {
                        tableViewPlaceholderLabel.setText("No packages found");
                    }
                    isThreadWorking = false;
                });
            }).start();
        }
    }

    private void installPackage(String packageId) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("winget", QueryType.INSTALL.toString(), packageId, "--accept-package-agreements", "--accept-source-agreements");
        processBuilder.redirectErrorStream(true);
        processBuilder.start();
    }

    public void initialize_comboBox_PackageList() {
        ListManager listManager = ListManager.getInstance();
        // Set the ObservableList from ListManager as the ComboBox items.
        comboBox_selectPackageList.setItems(listManager.getFXLists());
        // Define how the items are displayed in the ComboBox
        comboBox_selectPackageList.setCellFactory(lv -> new ListCell<PackageList>() {
            @Override
            protected void updateItem(PackageList pkgList, boolean empty) {
                super.updateItem(pkgList, empty);
                setText(empty ? "" : pkgList.getName());
            }
        });

        // Displays the selected item in the comboBox when it is not expanded.
        comboBox_selectPackageList.setConverter(new StringConverter<PackageList>() {
            @Override
            public String toString(PackageList object) {
                return (object != null ? object.getName() : "");
            }
            // This method is not needed but must be overwritten. It would be used to recognize a users typed input to select a packageList.
            @Override
            public PackageList fromString(String string) {
                return null;
            }
        });

        // Listener: Updates the tableView based on the selected packageList in the combobox. (Because of the Favourite checkbox)
        comboBox_selectPackageList.valueProperty().addListener((obs, oldVal, newVal) -> {
            selectedPackageList = newVal;
            tableView.refresh();
        });
    }

    @FXML
    private void button_addPackageToList(ActionEvent event)
    {
//        WinGetPackage selectedPackage = tableView.getSelectionModel().getSelectedItem();
//        PackageList selectedList = comboBox_selectPackageList.getSelectionModel().getSelectedItem();
//
//        if (selectedPackage != null && selectedList != null)
//        {
//            // Check if the selected package is already in the selected list
//            boolean packageExists = selectedList.getFXPackages().stream()
//                    .anyMatch(pkg -> pkg.getId().equals(selectedPackage.getId()));
//
//            if (!packageExists)
//            {
//                // Add the package to the list
//                selectedList.addPackage(selectedPackage);
//                System.out.println("Package added to list: " + selectedPackage.getName());
//            }
//            else
//            {
//                System.out.println("Package already exists in the list: " + selectedPackage.getName());
//            }
//        }
//        else
//        {
//            // Handle cases where nothing is selected
//            System.out.println("No package or list selected.");
//        }
    }

    private void setTableViewPlaceholder(String labelText, boolean showProgressIndicator){
        tableViewPlaceholderLabel.setText(labelText);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(showProgressIndicator);

        placeholderContent = new VBox(10);
        placeholderContent.getChildren().addAll(progressIndicator, tableViewPlaceholderLabel);
        placeholderContent.setAlignment(Pos.CENTER);

        tableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefWidth(newVal.doubleValue());
        });
        tableView.heightProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefHeight(newVal.doubleValue());
        });

        tableView.setPlaceholder(placeholderContent);
    }
}
