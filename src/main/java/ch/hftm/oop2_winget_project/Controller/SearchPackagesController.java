package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Util.ListProvider;
import ch.hftm.oop2_winget_project.Model.Message;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.InputValidator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPackagesController implements IControllerBase, Initializable
{
    @FXML
    private TableView<WinGetPackage> searchTableView;
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
    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();
        tableViewPlaceholderLabel.setText("Suchbegriff eingeben und Pakete suchen");
        searchTableView.setPlaceholder(tableViewPlaceholderLabel);

        addButtonToTableView();
        setTableViewData();
        setTableViewSource();
        registerInputServices();
    }

    @FXML
    private void testButtonClick()
    {
        // Test message
        Message msg = new Message();
        msg.show(Alert.AlertType.ERROR, "title", "headertext", "message");

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
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().packageNameProperty());
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().packageIDProperty());
        this.versionColumn.setCellValueFactory(cellData -> cellData.getValue().packageVersionProperty());
        this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().packageSourceProperty());
    }

    @Override
    public void setTableViewSource()
    {
        this.searchTableView.setItems(ListProvider.getSearchPackageList());
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
        keywordTextField.setTextFormatter(InputValidator.createSpaceValidator());
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
                            ListProvider.getInstalledPackageList().add(data);
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
                    query.queryToList(QueryType.SEARCH, searchKeyword, ListProvider.getSearchPackageList());
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
}
