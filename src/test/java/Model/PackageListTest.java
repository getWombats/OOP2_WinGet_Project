package Model;

import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

class PackageListTest {

    private PackageList packageList;
    private final String testName = "TestList";

    @BeforeEach
    void setUp() {
        // Initialize PackageList object; Used in multiple tests.
        packageList = new PackageList(testName);
    }

    @Test
    void testDefaultConstructor() {
        PackageList defaultList = new PackageList();
        assertNotNull(defaultList.getPackages(), "Packages list should be null");
    }

    @Test
    void testNameGetterSetter() {
        String newName = "NewListName";
        packageList.setName(newName);
        assertEquals(newName, packageList.getName(), "The name should be 'NewListName'");
    }

    @Test
    void testIdGetterSetter() {
        String newId = UUID.randomUUID().toString();
        packageList.setId(newId);
        assertEquals(newId, packageList.getId(), "The ID should be the new UUID");
    }

    @Test
    void testSizeGetterSetter() {
        packageList.setSize(5);
        assertEquals(5, packageList.getSize(), "The size should be 5");
    }

    @Test
    void testAddAndRemovePackage() {
        WinGetPackage newPackage = new WinGetPackage("TestName", "TestId", "TestVersion", "TestSource");
        packageList.addPackage(newPackage);
        assertEquals(1, packageList.getSize(), "The size should increase to 1");
        assertTrue(packageList.getPackages().contains(newPackage), "The new package should be added to the list");

        packageList.removePackage(newPackage);
        assertEquals(0, packageList.getSize(), "The size should decrease to 0");
        assertFalse(packageList.getPackages().contains(newPackage), "The new package should be removed from the list");
    }

    @Test
    void testSetAndGetPackages() {
        List<WinGetPackage> newPackages = new ArrayList<>();
        WinGetPackage package1 = new WinGetPackage("Name1", "Id1", "Version1", "Source1");
        WinGetPackage package2 = new WinGetPackage("Name2", "Id2", "Version2", "Source2");
        newPackages.add(package1);
        newPackages.add(package2);

        packageList.setPackages(newPackages);
        assertEquals(newPackages, packageList.getPackages(), "The packages list should match the new list");
        assertEquals(0, packageList.getSize(), "The size should reflect the new list's size");
    }

    @Test
    void testFXNameGetterSetter() {
        SimpleStringProperty newNameProperty = new SimpleStringProperty("FXNewName");
        packageList.setFXName(newNameProperty);
        assertEquals(newNameProperty, packageList.getFXName(), "The FXName property should match the new property");
    }

    @Test
    void testFXSizeGetterSetter() {
        SimpleIntegerProperty newSizeProperty = new SimpleIntegerProperty(10);
        packageList.setFXSize(newSizeProperty);
        assertEquals(newSizeProperty, packageList.getFXSize(), "The FXSize property should match the new property");
    }

    @Test
    void testFXPackagesGetterSetter() {
        ObservableList<WinGetPackage> newFXPackages = FXCollections.observableArrayList(new WinGetPackage("FXName", "FXId", "FXVersion", "FXSource"));
        packageList.setFXPackages(newFXPackages);
        assertEquals(newFXPackages, packageList.getFXPackages(), "The FXPackages should match the new observable list");
    }

    @Test
    void testStaticPackageLists() {
        assertNotNull(PackageList.getSearchPackageList(), "Search package list should not be null");
        assertNotNull(PackageList.getInstalledPackageList(), "Installed package list should not be null");
        assertNotNull(PackageList.getUpgradePackageList(), "Upgrade package list should not be null");
    }
}