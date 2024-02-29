package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.QueryType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpgradePackagesController implements IControllerBase, Initializable
{
    @FXML
    private TableView<WinGetPackage> upgradePackagesTableView;
    @FXML
    private TableColumn<WinGetPackage, String> idColumn;
    @FXML
    private TableColumn<WinGetPackage, String> nameColumn;
    @FXML
    private TableColumn<WinGetPackage, String> sourceColumn;
    @FXML
    private TableColumn<WinGetPackage, String> installedVersionColumn;
    @FXML
    private TableColumn<WinGetPackage, String> availableVersionColumn;
    @FXML
    private TableColumn<WinGetPackage, Void> actionColumn;
    @FXML
    private Label tableViewPlaceholderLabel;
    @FXML
    private VBox placeholderContent;
    @FXML
    private TextField textfield_filter;
    @FXML
    private ComboBox comboBox_filter;

    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        placeholderContent = new VBox(10);
        tableViewPlaceholderLabel = new Label();
        setTableViewPlaceholder("List all available updates for installed packages", false);
    }

    @FXML
    private void getAvailableUpdates() {
        if (!isThreadWorking)
        {
            upgradePackagesTableView.getItems().clear();

            setTableViewPlaceholder("Loading available updates", true);

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery(QueryType.UPGRADE);
                try
                {
                    query.queryToList(null);
                }
                catch (IOException | InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
                        query.CreateUpdateList(PackageList.getUpgradePackageList());

                        for(var item : PackageList.getUpgradePackageList()) {
                            System.out.println(item.getName() + " / ID: " + item.getId() + " / V alt:" + item.getVersion() + " / V neu: " + item.getUpdateVersion());
                        }

                        refreshTableViewContent();
                    }
                    else
                    {
                        setTableViewPlaceholder("Error loading packages", false);
                    }
                    isThreadWorking = false;
                });
            }).start();
        }
    }

    @Override
    public void setTableViewData() {
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        this.installedVersionColumn.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        this.availableVersionColumn.setCellValueFactory(cellData -> cellData.getValue().getFXUpdateVersion());
        this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }

    @Override
    public void setTableViewSource() {
        this.upgradePackagesTableView.setItems(PackageList.getUpgradePackageList());
    }

    @Override
    public void refreshTableViewContent() {
        upgradePackagesTableView.setItems(null);
        setTableViewSource();
    }

    @Override
    public WinGetPackage getObjectFromSelection() {
        return upgradePackagesTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void addButtonToTableView() {

    }

    private void setTableViewPlaceholder(String labelText, boolean showProgressIndicator){
        tableViewPlaceholderLabel.setText(labelText);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(showProgressIndicator);

        placeholderContent = new VBox(10);
        placeholderContent.getChildren().addAll(progressIndicator, tableViewPlaceholderLabel);
        placeholderContent.setAlignment(Pos.CENTER);

        upgradePackagesTableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefWidth(newVal.doubleValue());
        });
        upgradePackagesTableView.heightProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefHeight(newVal.doubleValue());
        });

        upgradePackagesTableView.setPlaceholder(placeholderContent);
    }
}
