package ch.hftm.oop2_winget_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListManager class manages a list of PackageList objects.
 * There is only one such list in the application.
 * This list is observable, meaning it can update the UI automatically when items are added or removed.
 * The class provides methods to add and remove PackageLists, and to
 * get the entire list to use elsewhere.
 */

public class ListManager {

//    Variables
    private static ListManager instance;
    private ObservableList<PackageList> lists;
    private PackageList selectedPackage;

//    Instantiation method
public static ListManager getInstance() {
    if (instance == null) {
        synchronized (ListManagerDTO.class) { // Synchronized to prevent multiple threads checking, returning null and creating multiple instances.
            if (instance == null) {
                instance = new ListManager();
            }
        }
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

    public ObservableList<PackageList> getFXLists() {
        return lists;
    }

    public List<PackageList> getLists() {
        return new ArrayList<>(lists);
    }

    public void setLists(ObservableList<PackageList> lists) {
        this.lists = lists;
    }



    public void setSelectedPackageList(PackageList selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    public PackageList getSelectedPackageList() {
        return this.selectedPackage;
    }

    // This method ensures that the deserialized object is replaced with the singleton instance.
    protected Object readResolve() {
        return getInstance();
    }
}