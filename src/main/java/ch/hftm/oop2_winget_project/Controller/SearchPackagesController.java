package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Model.*;
import ch.hftm.oop2_winget_project.Util.*;
import ch.hftm.oop2_winget_project.Api.IControllerBase;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPackagesController implements IControllerBase, Initializable
{
    @FXML
    private TableView<WinGetPackage> searchTableView;
    @FXML
    private TableColumn<WinGetPackage, Boolean> favoriteColumn;
    @FXML
    private TableColumn<WinGetPackage, String> idColumn;
    @FXML
    private TableColumn<WinGetPackage, String> nameColumn;
    @FXML
    private TableColumn<WinGetPackage, String> sourceColumn;
    @FXML
    private TableColumn<WinGetPackage, String> versionColumn;
    @FXML
    private TableColumn<WinGetPackage, Void> actionColumn;
    @FXML
    private TextField keywordTextField;
    @FXML
    private Label tableViewPlaceholderLabel;
    @FXML
    private ComboBox<PackageList> comboBox_selectPackageList; // The comboBox for PackageList selection
    @FXML
    private Button button_addPackageToList;
    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();
        tableViewPlaceholderLabel.setText("Enter search keyword");
        searchTableView.setPlaceholder(tableViewPlaceholderLabel);

        addButtonToTableView();
        addFavoriteCheckboxToTableView();
        setTableViewData();
        setTableViewSource();
        registerInputServices();
        initialize_comboBox_selectPackageList();
    }

    @FXML
    private void testButtonClick()
    {
        // Test message
//        Message msg = new Message();
//        msg.show(Alert.AlertType.ERROR, "title", "headertext", "message");

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
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        this.versionColumn.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }

    @Override
    public void setTableViewSource()
    {
        this.searchTableView.setItems(PackageList.getSearchPackageList());
    }

    @Override
    public void refreshTableViewContent()
    {
        searchTableView.setItems(null);
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
        return searchTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void addButtonToTableView()
    {
        Callback<TableColumn<WinGetPackage, Void>, TableCell<WinGetPackage, Void>> cellFactory = new Callback<>()
        {
            @Override
            public TableCell<WinGetPackage, Void> call(final TableColumn<WinGetPackage, Void> param)
            {
                final TableCell<WinGetPackage, Void> cell = new TableCell<>()
                {
                    private final Button btn = new Button("Install");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            data.setInstalled(true); // Set package as installed
                            btn.setDisable(true); // Disables button when clicked
                            System.out.println(data.getName() + " [ID: " + data.getId() + "] will be installed..."); // Test, execute here 'winget install {packageId}'

                            // Update list
                            PackageList.getInstalledPackageList().add(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                        }
                        else
                        {
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            if(data.isInstalled())
                            {
                                // Set cell content when package is installed already
                                Label installedLabel = new Label("installed");
                                setGraphic(installedLabel);
                            }
                            else
                            {
                                setGraphic(btn);
                            }
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void addFavoriteCheckboxToTableView()
    {
        favoriteColumn.setCellValueFactory(pkg -> pkg.getValue().isFavoriteProperty());
        favoriteColumn.setCellFactory(column -> new CheckBoxTableCell<>()
        {
            @Override
            public void updateItem(Boolean item, boolean empty)
            {
                super.updateItem(item, empty);

                if (empty)
                {
                    setGraphic(null);
                }
                else
                {
                    CheckBox checkBox = new CheckBox();
                    WinGetPackage model = getTableView().getItems().get(getIndex());

                    if(model.isFavorite())
                    {
                        checkBox.setSelected(true);
                    }

                    checkBox.selectedProperty().setValue(model.isFavorite()); // Bind checkbox state to item value
                    checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                        model.setFavorite(isNowSelected);

                        if (isNowSelected)
                        {
                            onFavoriteCheckboxChecked(model);
                        }
                        else
                        {
                            onFavoriteCheckboxUnchecked(model);
                        }
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }

    private void onFavoriteCheckboxChecked(WinGetPackage item)
    {
        System.out.println("Checked: " + item.getName());
        item.setFavorite(true);
        comboBox_selectPackageList.getSelectionModel().getSelectedItem().addPackage(item);
    }

    private void onFavoriteCheckboxUnchecked(WinGetPackage item)
    {
        System.out.println("Unchecked: " + item.getName());
        item.setFavorite(false);
        comboBox_selectPackageList.getSelectionModel().getSelectedItem().addPackage(item);
    }

    private void searchPackages()
    {
        String searchKeyword = keywordTextField.getText().trim();

        if (!searchKeyword.isEmpty() && !isThreadWorking)
        {
            if (searchTableView.getItems() != null)
            {
                searchTableView.getItems().clear();
            }

            tableViewPlaceholderLabel.setText("Searching packages...");

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

    public void initialize_comboBox_selectPackageList() {
        ListManager listManager = ListManager.getInstance();

        // Set the ComboBox items to the ObservableList from ListManager
        comboBox_selectPackageList.setItems(listManager.getFXLists());

        // Define how the items are displayed in the ComboBox
        comboBox_selectPackageList.setCellFactory(lv -> new ListCell<PackageList>() {
            @Override
            protected void updateItem(PackageList item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });

        // Optionally, if you want to display a selected item (when the ComboBox is not expanded)
        comboBox_selectPackageList.setConverter(new StringConverter<PackageList>() {
            @Override
            public String toString(PackageList object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public PackageList fromString(String string) {
                // This method is not needed for a ComboBox, but it must be overridden.
                return null;
            }
        });

        // Optional: Add a listener to react when the user selects a package list
        comboBox_selectPackageList.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Selected PackageList: " + newVal.getName());
                // You can load the selected PackageList into another view/component here.
            }
        });
    }

    @FXML
    private void button_addPackageToList(ActionEvent event)
    {
        WinGetPackage selectedPackage = searchTableView.getSelectionModel().getSelectedItem();
        PackageList selectedList = comboBox_selectPackageList.getSelectionModel().getSelectedItem();

        if (selectedPackage != null && selectedList != null)
        {
            // Check if the selected package is already in the selected list
            boolean packageExists = selectedList.getFXPackages().stream()
                    .anyMatch(pkg -> pkg.getId().equals(selectedPackage.getId()));

            if (!packageExists)
            {
                // Add the package to the list
//                selectedList.getFXPackages().add(selectedPackage);
                selectedList.addPackage(selectedPackage);
                System.out.println("Package added to list: " + selectedPackage.getName());
            }
            else
            {
                System.out.println("Package already exists in the list: " + selectedPackage.getName());
            }
        }
        else
        {
            // Handle cases where nothing is selected
            System.out.println("No package or list selected.");
        }
    }
}
