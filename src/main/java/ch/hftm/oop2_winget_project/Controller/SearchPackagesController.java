package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Features.QueryType;
import ch.hftm.oop2_winget_project.Models.WinGetQuery;
import ch.hftm.oop2_winget_project.Models.WinGetPackage;
import ch.hftm.oop2_winget_project.Api.TableViewModificationable;
import ch.hftm.oop2_winget_project.Utils.ConsoleExitCode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPackagesController implements TableViewModificationable, Initializable
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
    private TextField keywordTextField;
    @FXML
    private Label tableViewPlaceholderLabel;
    private boolean isThreadWorking;
    private static final ObservableList<WinGetPackage> packageList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tableViewPlaceholderLabel = new Label();

        setTableViewData();
        setTableViewSource();

        tableViewPlaceholderLabel.setText("Suchbegriff eingeben und Pakete suchen");
        searchTableView.setPlaceholder(tableViewPlaceholderLabel);

        keywordTextField.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER )
            {
                searchPackages();
            }
        } );
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
        this.searchTableView.setItems(packageList);
    }

    @Override
    public void refreshTableViewContent()
    {
        searchTableView.setItems(null);
        setTableViewSource();
    }

    @Override
    public WinGetPackage getObjectFromSelection()
    {
        return searchTableView.getSelectionModel().getSelectedItem();
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

            tableViewPlaceholderLabel.setText("Pakete werden geladen...");

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery();
                query.QueryToList(QueryType.SEARCH, searchKeyword, packageList);

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
                        refreshTableViewContent();
                    }
                    else
                    {
                        tableViewPlaceholderLabel.setText("Keine Pakete gefunden");
                    }
                    isThreadWorking = false;
                });
            }).start();
        }
    }
}
