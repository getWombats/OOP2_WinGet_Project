package ch.hftm.oop2_winget_project.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListManager
{
    private final ObservableList<WinGetPackage> searchPackageList = FXCollections.observableArrayList();
    private final ObservableList<WinGetPackage> installedPackageList = FXCollections.observableArrayList();
    private final ObservableList<WinGetPackage> favoritePackageList = FXCollections.observableArrayList();

    public ObservableList<WinGetPackage> getSearchPackageList()
    {
        return searchPackageList;
    }

    public ObservableList<WinGetPackage> getInstalledPackageList()
    {
        return installedPackageList;
    }

    public ObservableList<WinGetPackage> getFavoritePackageList()
    {
        return favoritePackageList;
    }
}
