package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Model.*;
import ch.hftm.oop2_winget_project.Util.*;
import ch.hftm.oop2_winget_project.Api.IControllerBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private TableColumn<WinGetPackage, Void> favoriteColumn;
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
    ComboBox<PackageList> comboBox_selectPackageList; // The comboBox for PackageList selection

    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();
        tableViewPlaceholderLabel.setText("Enter search keyword");
        searchTableView.setPlaceholder(tableViewPlaceholderLabel);

        addButtonToTableView();
        addFavoriteToggleToTableView();
        setTableViewData();
        setTableViewSource();
        registerInputServices();
//        initialize_comboBox_selectPackageList();
    }

    @FXML
    private void testButtonClick()
    {
        // Test message
//        Message msg = new Message();
//        msg.show(Alert.AlertType.ERROR, "title", "headertext", "message");

        // Test notification
        Message notify = new Message();
        notify.showNotification("title", "message");
    }

    @FXML
    private void searchButtonClick()
    {
        searchPackages();
    }

    @Override
    public void setTableViewData()
    {
//        this.favoriteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(favoriteColumn)); // Test with checkbox
//        this.favoriteColumn.setCellValueFactory(cellData -> cellData.getValue().isFavoriteProperty()); // Test with checkbox
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().packageIDProperty());
        this.versionColumn.setCellValueFactory(cellData -> cellData.getValue().packageVersionProperty());
        this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().packageSourceProperty());
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
                            System.out.println(data.getPackageName() + " [ID: " + data.getPackageID() + "] will be installed..."); // Test, execute here 'winget install {packageId}'

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

    public void addFavoriteToggleToTableView()
    {
        Callback<TableColumn<WinGetPackage, Void>, TableCell<WinGetPackage, Void>> cellFactory = new Callback<>()
        {
            @Override
            public TableCell<WinGetPackage, Void> call(final TableColumn<WinGetPackage, Void> param)
            {
                final TableCell<WinGetPackage, Void> cell = new TableCell<>()
                {
                    private final ToggleButton btn = new ToggleButton("test");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            data.setFavorite(btn.isSelected()); // Set package as favorite
                            System.out.println(data.getPackageName() + " is now favorite: " + data.isFavorite());
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
                            btn.setSelected(data.isFavorite());
                        }
                    }
                };
                return cell;
            }
        };
        favoriteColumn.setCellFactory(cellFactory);
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
                WinGetQuery query = new WinGetQuery();
                try
                {
                    query.queryToList(QueryType.SEARCH, searchKeyword, PackageList.getSearchPackageList());
                }
                catch (IOException | InterruptedException ex)
                {
                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                }

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
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
        comboBox_selectPackageList.setItems(listManager.getLists());

        // Set a cell factory to display the PackageList name in the ComboBox
        comboBox_selectPackageList.setCellFactory(lv -> new ListCell<PackageList>() {
            @Override
            protected void updateItem(PackageList item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });

        // Optionally, set a converter if you need to get the selected item as a PackageList instance.
        comboBox_selectPackageList.setConverter(new StringConverter<PackageList>() {
            @Override
            public String toString(PackageList object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public PackageList fromString(String string) {
                // This is relevant if you allow users to input custom text into the ComboBox.
                return null; // Implement logic as necessary
            }
        });
    }
}
