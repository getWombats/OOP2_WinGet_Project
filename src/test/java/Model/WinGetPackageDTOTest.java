package Model;

import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WinGetPackageDTOTest {

    private WinGetPackageDTO winGetPackageDTO;

    @BeforeEach
    void setUp() {
        // Initialize WinGetPackageDTO object; Used in multiple tests.
        winGetPackageDTO = new WinGetPackageDTO();
    }

    @Test
    void testDefaultConstructor() {
        assertNull(winGetPackageDTO.getName(), "Default name should be null");
        assertNull(winGetPackageDTO.getId(), "Default ID should be null");
        assertNull(winGetPackageDTO.getVersion(), "Default version should be null");
        assertNull(winGetPackageDTO.getSource(), "Default source should be null");
    }

    @Test
    void testNameGetterSetter() {
        winGetPackageDTO.setName("NewName");
        assertEquals("NewName", winGetPackageDTO.getName(), "The name should be 'NewName'");
    }

    @Test
    void testIdGetterSetter() {
        winGetPackageDTO.setId("NewId");
        assertEquals("NewId", winGetPackageDTO.getId(), "The ID should be 'NewId'");
    }

    @Test
    void testVersionGetterSetter() {
        winGetPackageDTO.setVersion("NewVersion");
        assertEquals("NewVersion", winGetPackageDTO.getVersion(), "The version should be 'NewVersion'");
    }

    @Test
    void testSourceGetterSetter() {
        winGetPackageDTO.setSource("NewSource");
        assertEquals("NewSource", winGetPackageDTO.getSource(), "The source should be 'NewSource'");
    }
}