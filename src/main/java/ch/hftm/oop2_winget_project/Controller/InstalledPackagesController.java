package ch.hftm.oop2_winget_project.Controller;

import ch.hftm.oop2_winget_project.Api.IControllerBase;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.WinGetQuery;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.SourceType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @FXML
    private ComboBox<String> comboBox_filter;
    @FXML
    private TextField textfield_filter;
    private boolean isThreadWorking;

//    private final Image image = new Image(getClass().getResource("/path/to/resource/image.jpg").toExternalForm());
//    private ImageView imageView = new ImageView(image);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        placeholderContent = new VBox(10);
        tableViewPlaceholderLabel = new Label();
        setTableViewPlaceholder("List all installed packages", false);

        addButtonToTableView();
        setSourceColumnLabel();
        setTableViewData();
        setTableViewSource();

        // Initialize filter components
        comboBox_filter.getItems().addAll("All Attributes", "Name", "ID", "Version", "Source");
        comboBox_filter.setValue("All Attributes");
        textfield_filter.textProperty().addListener((observable, oldValue, newValue) -> filterList());
        comboBox_filter.valueProperty().addListener((observable, oldValue, newValue) -> filterList());
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
                            WinGetPackage selectedItem = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to uninstall " + selectedItem.getName() + " ?", ButtonType.YES, ButtonType.CANCEL);
                            alert.setHeaderText("");
                            alert.setTitle("Remove Package");
//                            alert.getDialogPane().setStyle("-fx-background: black;");
//                            alert.setGraphic(imageView);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                isThreadWorking = true;
                                setGraphic(new ProgressBar());
                                new Thread(() -> {
                                    try
                                    {
                                        uninstallPackage(selectedItem.getId());
                                    }
                                    catch (IOException ex)
                                    {
                                        System.out.println(ex.getMessage()); // Replace with ExceptionHandler when implemented
                                    }

                                    Platform.runLater(() -> {
                                        selectedItem.setInstalled(false); // Set package as uninstalled
                                        // Update list
                                        PackageList.getInstalledPackageList().remove(selectedItem);
                                        setGraphic(null);
                                        textfield_filter.clear();
                                        isThreadWorking = false;
                                    });
                                }).start();
                            }
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
                            btn.getStyleClass().add("button-uninstall");
                            setGraphic(btn);
//                            WinGetPackage data = getTableView().getItems().get(getIndex());
//                            if(data.getSource().isEmpty())
//                            {
//                                // Do not show "deinstall" button when source is empty
//                                setGraphic(null);
//                            }
//                            else
//                            {
//                                btn.getStyleClass().add("button-uninstall");
//                                setGraphic(btn);
//                            }
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    private void setSourceColumnLabel(){
        sourceColumn.setCellFactory(column -> new TableCell<>() {
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
                        setTableViewPlaceholder("Error loading packages", false);
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
        ProcessBuilder processBuilder = new ProcessBuilder("winget.exe", QueryType.UNINSTALL.toString(), packageId);
        processBuilder.redirectErrorStream(true);
        processBuilder.start();
    }

    private void filterList() {
        String filterText = textfield_filter.getText().toLowerCase();
        String selectedAttribute = comboBox_filter.getValue();

        if (filterText.isEmpty() || selectedAttribute == null) {
            installedPackagesTableView.setItems(PackageList.getInstalledPackageList());
            return;
        }

        Stream<WinGetPackage> pkgStream = PackageList.getInstalledPackageList().stream();
        Predicate<WinGetPackage> filterPredicate = pkg -> {
            switch (selectedAttribute) {
                case "All Attributes":
                    return (pkg.getName() + " " + pkg.getId() + " " + pkg.getSource() + " " + pkg.getVersion()).toLowerCase().contains(filterText);
                case "Name":
                    return pkg.getName().toLowerCase().contains(filterText);
                case "ID":
                    return pkg.getId().toLowerCase().contains(filterText);
                case "Version":
                    return pkg.getVersion().toLowerCase().contains(filterText);
                case "Source":
                    return pkg.getSource().toLowerCase().contains(filterText);
                default:
                    return false;
            }
        };

        ObservableList<WinGetPackage> filteredList = pkgStream.filter(filterPredicate).collect(Collectors.toCollection(FXCollections::observableArrayList));

        if(filteredList.isEmpty()){
            setTableViewPlaceholder("No Packages found", false);
        }

        installedPackagesTableView.setItems(filteredList);
    }
}