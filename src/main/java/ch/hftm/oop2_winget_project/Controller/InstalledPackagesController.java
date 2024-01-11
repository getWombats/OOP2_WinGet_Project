package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InstalledPackagesController implements IControllerBase, Initializable
{
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
    private Label tableViewPlaceholderLabel;
    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();

        addButtonToTableView();
        setTableViewData();
        setTableViewSource();

        tableViewPlaceholderLabel.setText("Liste installierter Pakete");
        installedPackagesTableView.setPlaceholder(tableViewPlaceholderLabel);
    }

    @FXML
    private void testButtonClick()
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
        this.installedPackagesTableView.setItems(App.getListManager().getInstalledPackageList());
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

    }

    private void showInstalledPackages()
    {
        if (!isThreadWorking)
        {
            installedPackagesTableView.getItems().clear();

            tableViewPlaceholderLabel.setText("Installierte Pakete werden geladen...");

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery();
                try
                {
                    query.queryToList(QueryType.LIST, "", App.getListManager().getInstalledPackageList());
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
                        tableViewPlaceholderLabel.setText("Error oder so?");
                    }
                    isThreadWorking = false;
                });
            }).start();
        }
    }
}
