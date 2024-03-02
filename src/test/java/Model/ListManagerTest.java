package Model;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListManagerTest {

    private ListManager listManager;

    @BeforeEach
    void setUp() {
        // Initialize ListManager singleton instance; Used in multiple tests.
        listManager = ListManager.getInstance();
        listManager.setLists(new ArrayList<>()); // Ensure it's empty before each test.
    }

    @Test
    void testSingletonInstance() {
        ListManager anotherInstance = ListManager.getInstance();
        assertNotNull(listManager, "ListManager instance should not be null");
        assertSame(listManager, anotherInstance, "Instances should be the same (singleton)");
    }

    @Test
    void testCreateAndDeletePackageList() {
        String listName = "TestList";
        listManager.createPackageList(listName);
        assertEquals(1, listManager.getLists().size(), "List size should be 1 after adding a list");
        assertEquals(listName, listManager.getLists().get(0).getName(), "The name of the package list should be 'TestList'");

        listManager.deletePackageList(listManager.getLists().get(0));
        assertTrue(listManager.getLists().isEmpty(), "List should be empty after removing the package list");
    }

    @Test
    void testSetAndGetLists() {
        ObservableList<PackageList> newLists = FXCollections.observableArrayList(new PackageList("NewList"));
        listManager.setFXLists(newLists);
        assertEquals(newLists, listManager.getFXLists(), "The lists should match the set lists");
        assertEquals(1, listManager.getLists().size(), "The size should reflect the new lists' size");
    }

    @Test
    void testSelectedPackageListGetterSetter() {
        PackageList newPackageList = new PackageList("SelectedList");
        listManager.setSelectedPackageList(newPackageList);
        assertEquals(newPackageList, listManager.getSelectedPackageList(), "The selected package list should match the new package list");
    }

    @Test
    void testGetListsReturnsCopy() {
        listManager.createPackageList("ImmutableList");
        List<PackageList> retrievedLists = listManager.getLists();
        assertNotSame(retrievedLists, listManager.getFXLists(), "getLists should return a copy, not the original list");
        assertEquals(retrievedLists.size(), listManager.getFXLists().size(), "The copy should have the same size as the original");
    }

    @Test
    void testModifyingRetrievedListDoesNotAffectOriginal() {
        listManager.createPackageList("TestList");
        List<PackageList> retrievedLists = listManager.getLists();
        retrievedLists.remove(0);
        assertFalse(listManager.getLists().isEmpty(), "Modifying the retrieved list should not affect the original list");
    }
}