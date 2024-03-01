package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Util.SourceType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpgradePackagesController implements IControllerBase, Initializable
{
    @FXML
    private TableView<WinGetPackage> tableView_upgradePackages;
    @FXML
    private TableColumn<WinGetPackage, String> column_id;
    @FXML
    private TableColumn<WinGetPackage, String> column_name;
    @FXML
    private TableColumn<WinGetPackage, String> column_source;
    @FXML
    private TableColumn<WinGetPackage, String> column_installedVersion;
    @FXML
    private TableColumn<WinGetPackage, String> column_availableVersion;
    @FXML
    private TableColumn<WinGetPackage, Void> column_action;
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

        addButtonToTableView();
        setSourceColumnLabel();
        setTableViewData();
        setTableViewSource();
    }

    @FXML
    private void getAvailableUpdates() {
        if (!isThreadWorking)
        {
            tableView_upgradePackages.getItems().clear();

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
                            System.out.println(item.getName() + " / ID: " + item.getId());
                            System.out.println("Installed version:" + item.getVersion());
                            System.out.println("Available version: " + item.getUpdateVersion());
                            System.out.println();
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
        this.column_name.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        this.column_id.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        this.column_installedVersion.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        this.column_availableVersion.setCellValueFactory(cellData -> cellData.getValue().getFXUpdateVersion());
        this.column_source.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }

    @Override
    public void setTableViewSource() {
        this.tableView_upgradePackages.setItems(PackageList.getUpgradePackageList());
    }

    @Override
    public void refreshTableViewContent() {
        tableView_upgradePackages.setItems(null);
        setTableViewSource();
    }

    @Override
    public WinGetPackage getObjectFromSelection() {
        return tableView_upgradePackages.getSelectionModel().getSelectedItem();
    }

    @Override
    public void addButtonToTableView() {
        Callback<TableColumn<WinGetPackage, Void>, TableCell<WinGetPackage, Void>> cellFactory = new Callback<>()
        {
            @Override
            public TableCell<WinGetPackage, Void> call(final TableColumn<WinGetPackage, Void> param)
            {
                final TableCell<WinGetPackage, Void> cell = new TableCell<>()
                {
                    private final Button btn = new Button("Update");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            WinGetPackage selectedItem = getTableView().getItems().get(getIndex());

                            btn.setDisable(true); // Disables button when clicked and package updates
                            isThreadWorking = true;
                            setGraphic(new ProgressBar());
                            new Thread(() -> {
                                try
                                {
                                    upgradePackage(selectedItem.getId());
                                }
                                catch (IOException ex)
                                {
                                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                                }

                                Platform.runLater(() -> {
                                    // Update installed packages list
                                    setNewPackageVersion(selectedItem);
                                    // Remove package from list after update
                                    PackageList.getUpgradePackageList().remove(selectedItem);
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
                            btn.getStyleClass().add("button-upgrade");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        column_action.setCellFactory(cellFactory);
    }

    private void setTableViewPlaceholder(String labelText, boolean showProgressIndicator){
        tableViewPlaceholderLabel.setText(labelText);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(showProgressIndicator);

        placeholderContent = new VBox(10);
        placeholderContent.getChildren().addAll(progressIndicator, tableViewPlaceholderLabel);
        placeholderContent.setAlignment(Pos.CENTER);

        tableView_upgradePackages.widthProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefWidth(newVal.doubleValue());
        });
        tableView_upgradePackages.heightProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefHeight(newVal.doubleValue());
        });

        tableView_upgradePackages.setPlaceholder(placeholderContent);
    }

    private void setSourceColumnLabel(){
        column_source.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Label sourceLabel = new Label();
                sourceLabel.getStyleClass().removeAll("label-ms-store", "label-winget");
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                }
                else if(item.equals(SourceType.MSSTORE.toString())){
                    sourceLabel.getStyleClass().add("label-ms-store");
                    sourceLabel.setText(SourceType.MSSTORE.toString());
                    setGraphic(sourceLabel);
                }
                else if(item.equals(SourceType.WINGET.toString())){
                    sourceLabel.getStyleClass().add("label-winget");
                    sourceLabel.setText(SourceType.WINGET.toString());
                    setGraphic(sourceLabel);
                }
                else {
                    Label nameLabel = new Label(item);
                    setGraphic(nameLabel);
                }
            }
        });
    }

    private void setNewPackageVersion(WinGetPackage pkg){
        for(var installedPackage : PackageList.getInstalledPackageList()){
            if(installedPackage.getId().equals(pkg.getId())){
                installedPackage.setVersion(pkg.getUpdateVersion());
                break;
            }
        }
    }

    private void upgradePackage(String packageId) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("winget.exe", QueryType.UPGRADE.toString(), "--id", packageId, "--accept-package-agreements", "--accept-source-agreements");
        processBuilder.redirectErrorStream(true);
        processBuilder.start();
    }
}
