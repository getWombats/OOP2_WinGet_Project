package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ListManager
{
//    Variables
    private ArrayList<PackageList> lists;

//    Constructors
    public ListManager() {
        this.lists = new ArrayList<>();
    }





//    Methods


// Existing Code
    private static final ObservableList<WinGetPackage> searchPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> installedPackageList = FXCollections.observableArrayList();
    private static final ObservableList<WinGetPackage> favoritePackageList = FXCollections.observableArrayList();

    public static ObservableList<WinGetPackage> getSearchPackageList()
    {
        return searchPackageList;
    }
    public static ObservableList<WinGetPackage> getInstalledPackageList()
    {
        return installedPackageList;
    }
    public static ObservableList<WinGetPackage> getFavoritePackageList()
    {
        return favoritePackageList;
    }
}

