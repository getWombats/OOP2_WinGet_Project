package ch.hftm.oop2_winget_project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListManager class manages a list of PackageList objects.
 * There is only one such list in the application, it is a singelton.
 * This list is observable, meaning it can update the UI automatically when items are added or removed.
 * The class provides methods to add and remove PackageLists, and to
 * get the entire list to use elsewhere.
 */

public class ListManager {

    // Variables
    private static ListManager instance;
    private ObservableList<PackageList> lists;
    private PackageList selectedPackage;

    // Instantiation
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

    // Constructors
    private ListManager() {
        this.lists = FXCollections.observableArrayList();
    }

    // Getters, Setters
    public void setLists(List<PackageList>lists) {
        this.lists.setAll(lists);
    }
    public List<PackageList> getLists() {
        return new ArrayList<>(this.lists);
    }
    public ObservableList<PackageList> getFXLists() {
        return this.lists;
    }
    public void setFXLists(ObservableList<PackageList> lists) {
        this.lists = lists;
    }

    public PackageList getSelectedPackageList() {
        return this.selectedPackage;
    }
    public void setSelectedPackageList(PackageList selectedPackage) {
        this.selectedPackage = selectedPackage;
    }

    // Methods
    public void createPackageList(String packageListName){
        PackageList newPackageList = new PackageList(packageListName);
        lists.add(newPackageList);
    }

    // Used one time for favourite creation.
    public void createFavouriteList() {
        System.out.println("Favlist does not exist");
        PackageList newPackageList = new PackageList("Favourites", "favourite-list-uuid");
        lists.add(newPackageList);
    }

    public void deletePackageList(PackageList packageList) {
        lists.remove(packageList);
    }
}