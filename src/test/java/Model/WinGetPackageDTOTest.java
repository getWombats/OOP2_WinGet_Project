package Model;

import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WinGetPackageDTOTest {

    private WinGetPackageDTO packageDTO;

    @BeforeEach
    void setUp() {
        packageDTO = new WinGetPackageDTO();
        // You might initialize your object with predefined values here or leave it for the individual tests
    }

    @Test
    void testNameProperty() {
        String expectedName = "TestPackage";
        packageDTO.setName(expectedName);
        String actualName = packageDTO.getName();
        assertEquals(expectedName, actualName, "The name property should be set and retrieved correctly.");
    }

    @Test
    void testIdProperty() {
        String expectedId = "TestID";
        packageDTO.setId(expectedId);
        String actualId = packageDTO.getId();
        assertEquals(expectedId, actualId, "The ID property should be set and retrieved correctly.");
    }

    @Test
    void testVersionProperty() {
        String expectedVersion = "1.0.0";
        packageDTO.setVersion(expectedVersion);
        String actualVersion = packageDTO.getVersion();
        assertEquals(expectedVersion, actualVersion, "The version property should be set and retrieved correctly.");
    }

    @Test
    void testSourceProperty() {
        String expectedSource = "TestSource";
        packageDTO.setSource(expectedSource);
        String actualSource = packageDTO.getSource();
        assertEquals(expectedSource, actualSource, "The source property should be set and retrieved correctly.");
    }

    @Test
    void testDTOInitialization() {
        assertNull(packageDTO.getName(), "Initially, the name should be null.");
        assertNull(packageDTO.getId(), "Initially, the ID should be null.");
        assertNull(packageDTO.getVersion(), "Initially, the version should be null.");
        assertNull(packageDTO.getSource(), "Initially, the source should be null.");
    }
}