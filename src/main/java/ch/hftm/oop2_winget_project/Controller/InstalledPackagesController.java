package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.TableViewModificationable;
import ch.hftm.oop2_winget_project.Models.WinGetPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class InstalledPackagesController implements TableViewModificationable, Initializable
{
    // Controller for installed packages view
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
    private static final ObservableList<WinGetPackage> packageList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    @Override
    public void setTableViewData()
    {

    }

    @Override
    public void setTableViewSource()
    {

    }

    @Override
    public void refreshTableViewContent()
    {

    }

    @Override
    public WinGetPackage getObjectFromSelection()
    {
        return null;
    }

    @Override
    public void addButtonToTableView()
    {

    }
}
