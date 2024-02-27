package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
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
    private TableColumn<WinGetPackage, Void> actionColumn;
    @FXML
    private Label tableViewPlaceholderLabel;
    @FXML
    private VBox placeholderContent;
    private boolean isThreadWorking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        placeholderContent = new VBox(10);
        tableViewPlaceholderLabel = new Label();
        setTableViewPlaceholder("List all installed packages", false);

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
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFXName());
        this.idColumn.setCellValueFactory(cellData -> cellData.getValue().getFXId());
        this.versionColumn.setCellValueFactory(cellData -> cellData.getValue().getFXVersion());
        this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().getFXSource());
    }

    @Override
    public void setTableViewSource()
    {
        this.installedPackagesTableView.setItems(PackageList.getInstalledPackageList());
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

                            isThreadWorking = true;
                            setGraphic(new ProgressBar());
                            new Thread(() -> {
                                try
                                {
                                    uninstallPackage(data.getId());
                                }
                                catch (IOException ex)
                                {
                                    System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                                }

                                Platform.runLater(() -> {
                                    // Update list
                                    PackageList.getInstalledPackageList().remove(data);
                                    isThreadWorking = false;
                                });
                            }).start();
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
                            if(data.getSource().isEmpty())
                            {
                                // Do not show "deinstall" button when source is empty
                                setGraphic(null);
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

    private void showInstalledPackages()
    {
        if (!isThreadWorking)
        {
            installedPackagesTableView.getItems().clear();

            setTableViewPlaceholder("Loading installed packages", true);

            isThreadWorking = true;
            new Thread(() -> {
                WinGetQuery query = new WinGetQuery(QueryType.LIST);
                try
                {
                    query.queryToList("");
                }
                catch (IOException | InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }

                Platform.runLater(() -> {
                    if (query.getConsoleExitCode() == ConsoleExitCode.OK.getValue())
                    {
                        query.CreatePackageList(PackageList.getInstalledPackageList());
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

    private void setTableViewPlaceholder(String labelText, boolean showProgressIndicator){
        tableViewPlaceholderLabel.setText(labelText);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(showProgressIndicator);

        placeholderContent = new VBox(10);
        placeholderContent.getChildren().addAll(progressIndicator, tableViewPlaceholderLabel);
        placeholderContent.setAlignment(Pos.CENTER);

        installedPackagesTableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefWidth(newVal.doubleValue());
        });
        installedPackagesTableView.heightProperty().addListener((obs, oldVal, newVal) -> {
            placeholderContent.setPrefHeight(newVal.doubleValue());
        });

        installedPackagesTableView.setPlaceholder(placeholderContent);
    }

    private void uninstallPackage(String packageId) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("winget", QueryType.UNINSTALL.toString(), packageId);
        processBuilder.redirectErrorStream(true);
        processBuilder.start();
    }
}
