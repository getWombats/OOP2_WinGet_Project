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
    public void createPackageList(String packageListName){
        PackageList newPackageList = new PackageList(packageListName);
        lists.add(newPackageList);
    }

    public void deletePackageList(PackageList packageList) {
        lists.remove(packageList);
    }

    public ArrayList<PackageList> getLists() {
        return lists;
    }

    public void printPackageListNames() {
        System.out.println("Package Lists:");
        for (PackageList packageList : lists) {
            System.out.println("- " + packageList.getName());
        }
    }

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

