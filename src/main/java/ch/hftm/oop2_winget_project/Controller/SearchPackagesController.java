package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Features.QueryType;
import ch.hftm.oop2_winget_project.Features.WinGetQuery;
import ch.hftm.oop2_winget_project.Models.WinGetPackage;
import ch.hftm.oop2_winget_project.Api.TableViewModificationable;
import ch.hftm.oop2_winget_project.Utils.PromptExitCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
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

    private static ObservableList<WinGetPackage> packageList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        setTableViewData();
        setTableViewSource();

        searchTableView.setPlaceholder(new Label("Software suchen"));
    }

    @FXML
    void searchKeywordButtonClick(ActionEvent event)
    {
        String searchKeyword = keywordTextField.getText().trim();

        if(!searchKeyword.isEmpty())
        {
            packageList = FXCollections.observableArrayList(Objects.requireNonNull(WinGetQuery.QueryToList(QueryType.SEARCH, searchKeyword)));

            if(WinGetQuery.getPromptExitCode() == PromptExitCode.OK.getValue())
            {
                refreshTableViewContent();
            }
            else
            {
                if(searchTableView.getItems() != null)
                {
                    searchTableView.getItems().clear();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Keine Pakete mit dem Suchbegriff '" + searchKeyword + "' gefunden");
                alert.setTitle("");
                alert.show();

            }
        }
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
}
