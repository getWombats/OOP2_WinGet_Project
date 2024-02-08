package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.ListManager;
import ch.hftm.oop2_winget_project.Util.QueryType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListManagerController implements IControllerBase, Initializable {

    @FXML
    private Button 
    @FXML
    private TableView<WinGetPackage> installedPackagesTableView;
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
    private Label tableViewPlaceholderLabel;
    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();
        tableViewPlaceholderLabel.setText("List all installed packages");
        installedPackagesTableView.setPlaceholder(tableViewPlaceholderLabel);

        addButtonToTableView();
        setTableViewData();
        setTableViewSource();
    }

    @FXML
    private void refreshButtonClick()
    {
        showInstalledPackages();
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
        this.installedPackagesTableView.setItems(ListManager.getInstalledPackageList());
    }

    @Override
    public void refreshTableViewContent()
    {
        installedPackagesTableView.setItems(null);
        setTableViewSource();
    }

    @Override
    public WinGetPackage getObjectFromSelection()
    {
        return installedPackagesTableView.getSelectionModel().getSelectedItem();
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
                    private final Button btn = new Button("Uninstall");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage data = getTableView().getItems().get(getIndex());
                            data.setInstalled(false); // Set package as uninstalled
                            System.out.println(data.getPackageName() + " [ID: " + data.getPackageID() + "] uninstalling package..."); // Test, execute here 'winget remove {packageId}'

                            // Update list
                            ListManager.getInstalledPackageList().remove(data);
                            // Implement item removal from list here, track uninstall process possible?
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
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void showInstalledPackages()
    {
        if (!isThreadWorking)
        {
            installedPackagesTableView.getItems().clear();

            tableViewPlaceholderLabel.setText("Loading installed packages...");

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery();
                try
                {
                    query.queryToList(QueryType.LIST, "", ListManager.getInstalledPackageList());
                }
                catch (IOException | InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
                        refreshTableViewContent();
                    }
                    else
                    {
                        tableViewPlaceholderLabel.setText("Error loading packages");
                    }
                    isThreadWorking = false;
                });
            }).start();
        }
    }
}
