package Model;

import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackageListTest {

    private PackageList packageList;

    @BeforeEach
    void setUp() {
        packageList = new PackageList("TestList");
    }

    @Test
    void testInitialConditions() {
        assertEquals("TestList", packageList.getName());
        assertEquals(0, packageList.getSize());
        assertTrue(packageList.getPackages().isEmpty());
    }

    @Test
    void testAddRemovePackage() {
        WinGetPackage pkg = new WinGetPackage("TestPackage", "TestID", "1.0", "TestSource");
        packageList.addPackage(pkg);

        assertEquals(1, packageList.getSize());
        assertFalse(packageList.getPackages().isEmpty());
        assertEquals("TestPackage", packageList.getPackages().get(0).getName());

        packageList.removePackage(pkg);
        assertEquals(0, packageList.getSize());
        assertTrue(packageList.getPackages().isEmpty());
    }

    @Test
    void testSetPackages() {
        ObservableList<WinGetPackage> newPackages = FXCollections.observableArrayList();
        WinGetPackage newPkg = new WinGetPackage("NewPackage", "NewID", "2.0", "NewSource");
        newPackages.add(newPkg);

        packageList.setFXPackages(newPackages);

        assertEquals(1, packageList.getSize());
        assertEquals(newPkg, packageList.getPackages().get(0));
    }

    @Test
    void testPredefinedLists() {
        assertNotNull(PackageList.getSearchPackageList());
        assertNotNull(PackageList.getInstalledPackageList());
        assertNotNull(PackageList.getUpgradePackageList());
    }
}
