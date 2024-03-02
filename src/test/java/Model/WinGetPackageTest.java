package Model;

import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;

public class WinGetPackageTest {

    private WinGetPackage winGetPackage;

    @BeforeEach
    void setUp() {
        // Initialize WinGetPackage object; Used in multiple tests.
        winGetPackage = new WinGetPackage("TestName", "TestId", "TestVersion", "TestSource", true);
    }

    @Test
    void testDefaultConstructor() {
        WinGetPackage packageDefault = new WinGetPackage();
        assertNull(packageDefault.getName(), "Default name should be null");
        assertNull(packageDefault.getId(), "Default ID should be null");
        assertNull(packageDefault.getVersion(), "Default version should be null");
        assertNull(packageDefault.getSource(), "Default source should be null");
        assertEquals(false, packageDefault.isInstalled(), "Default installed status should be false");
    }

    @Test
    void testNameGetterSetter() {
        winGetPackage.setName("NewName");
        assertEquals("NewName", winGetPackage.getName(), "The name should be 'NewName'");
    }

    @Test
    void testIdGetterSetter() {
        winGetPackage.setId("NewId");
        assertEquals("NewId", winGetPackage.getId(), "The ID should be 'NewId'");
    }

    @Test
    void testVersionGetterSetter() {
        winGetPackage.setVersion("NewVersion");
        assertEquals("NewVersion", winGetPackage.getVersion(), "The version should be 'NewVersion'");
    }

    @Test
    void testUpdateVersionGetterSetter() {
        winGetPackage.setUpdateVersion("NewUpdateVersion");
        assertEquals("NewUpdateVersion", winGetPackage.getUpdateVersion(), "The update version should be 'NewUpdateVersion'");
    }

    @Test
    void testSourceGetterSetter() {
        winGetPackage.setSource("NewSource");
        assertEquals("NewSource", winGetPackage.getSource(), "The source should be 'NewSource'");
    }

    @Test
    void testInstalledGetterSetter() {
        winGetPackage.setInstalled(false);
        assertEquals(false, winGetPackage.isInstalled(), "The installed status should be false");
        winGetPackage.setInstalled(true);
        assertEquals(true, winGetPackage.isInstalled(), "The installed status should be true");
    }

    @Test
    void testFXNameGetterSetter() {
        SimpleStringProperty testName = new SimpleStringProperty("FXName");
        winGetPackage.setFXName(testName);
        assertEquals(testName, winGetPackage.getFXName(), "The FXName property should match the set property");
    }

    @Test
    void testFXIdGetterSetter() {
        SimpleStringProperty testId = new SimpleStringProperty("FXId");
        winGetPackage.setFXID(testId);
        assertEquals(testId, winGetPackage.getFXId(), "The FXId property should match the set property");
    }

    @Test
    void testFXVersionGetterSetter() {
        SimpleStringProperty testVersion = new SimpleStringProperty("FXVersion");
        winGetPackage.setFXVersion(testVersion);
        assertEquals(testVersion, winGetPackage.getFXVersion(), "The FXVersion property should match the set property");
    }

    @Test
    void testFXUpdateVersionGetterSetter() {
        SimpleStringProperty testUpdateVersion = new SimpleStringProperty("FXUpdateVersion");
        winGetPackage.setFXUpdateVersion(testUpdateVersion);
        assertEquals(testUpdateVersion, winGetPackage.getFXUpdateVersion(), "The FXUpdateVersion property should match the set property");
    }

    @Test
    void testFXSourceGetterSetter() {
        SimpleStringProperty testSource = new SimpleStringProperty("FXSource");
        winGetPackage.setFXSource(testSource);
        assertEquals(testSource, winGetPackage.getFXSource(), "The FXSource property should match the set property");
    }
}