package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;

public class WinGetPackageTest {

    private WinGetPackage winGetPackage;

    @BeforeEach
    void setUp() {
        winGetPackage = new WinGetPackage("TestName", "TestId", "TestVersion", "TestSource", true);
    }

    @Test
    void testGettersAndSetters() {

        assertEquals("TestName", winGetPackage.getName());
        assertEquals("TestId", winGetPackage.getId());
        assertEquals("TestVersion", winGetPackage.getVersion());
        assertEquals("TestSource", winGetPackage.getSource());
        assertTrue(winGetPackage.isInstalled());

        winGetPackage.setName("NewName");
        winGetPackage.setId("NewId");
        winGetPackage.setVersion("NewVersion");
        winGetPackage.setUpdateVersion("NewUpdateVersion");
        winGetPackage.setSource("NewSource");
        winGetPackage.setInstalled(false);

        assertEquals("NewName", winGetPackage.getName());
        assertEquals("NewId", winGetPackage.getId());
        assertEquals("NewVersion", winGetPackage.getVersion());
        assertEquals("NewUpdateVersion", winGetPackage.getUpdateVersion());
        assertEquals("NewSource", winGetPackage.getSource());
        assertFalse(winGetPackage.isInstalled());
    }
}