package ch.hftm.oop2_winget_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;

/**
 * The ListManager class manages a list of PackageList objects.
 * There is only one such list in the application.
 * This list is observable, meaning it can update the UI automatically when items are added or removed.
 * The class provides methods to add and remove PackageLists, and to
 * get the entire list to use elsewhere.
 */

public class ListManager {

//    Instances
    private static ListManager instance;

//    Variables
    private ObservableList<PackageList> lists;
    private PackageList selectesPackage;

//    Instantiation method
    public static ListManager getInstance() {
        if (instance == null) {
            instance = new ListManager();
        }
        return instance;
    }

//    Constructors
    private ListManager() {
        this.lists = FXCollections.observableArrayList();
    }

//    Methods
    public void createPackageList(String packageListName){
        PackageList newPackageList = new PackageList(packageListName);
        lists.add(newPackageList);
    }

    public void deletePackageList(PackageList packageList) {
        lists.remove(packageList);
    }

    public ObservableList<PackageList> getLists() {
        return lists;
    }

    public void printPackageListNames() {
        System.out.println("Package Lists:");
        for (PackageList packageList : lists) {
            System.out.println("- " + packageList.getName());
        }
    }


    public void setSelectedPackageList(PackageList selectedPackage) {
        this.selectesPackage = selectedPackage;
    }

    public PackageList getSelectedPackageList() {
        return this.selectesPackage;
    }
}