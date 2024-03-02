package Model;

import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageListDTOTest {

    private PackageListDTO packageListDTO;

    @BeforeEach
    void setUp() {
        packageListDTO = new PackageListDTO();
    }

    @Test
    void testIdProperty() {
        String expectedId = "UniqueID123";
        packageListDTO.setId(expectedId);
        assertEquals(expectedId, packageListDTO.getId(), "The ID should be set and retrieved correctly.");
    }

    @Test
    void testNameProperty() {
        String expectedName = "TestPackageList";
        packageListDTO.setName(expectedName);
        assertEquals(expectedName, packageListDTO.getName(), "The name should be set and retrieved correctly.");
    }

    @Test
    void testSizeProperty() {
        int expectedSize = 10;
        packageListDTO.setSize(expectedSize);
        assertEquals(expectedSize, packageListDTO.getSize(), "The size should be set and retrieved correctly.");
    }

    @Test
    void testPackagesProperty() {
        List<WinGetPackageDTO> expectedPackages = new ArrayList<>();
        expectedPackages.add(new WinGetPackageDTO()); // Add a default package for testing
        packageListDTO.setPackages(expectedPackages);

        assertNotNull(packageListDTO.getPackages(), "The packages list should not be null.");
        assertEquals(1, packageListDTO.getPackages().size(), "The packages list should contain one package.");
        assertEquals(expectedPackages, packageListDTO.getPackages(), "The packages should be set and retrieved correctly.");
    }

    @Test
    void testDTOInitialization() {
        assertNull(packageListDTO.getId(), "Initially, the ID should be null.");
        assertNull(packageListDTO.getName(), "Initially, the name should be null.");
        assertEquals(0, packageListDTO.getSize(), "Initially, the size should be 0.");
        assertNull(packageListDTO.getPackages(), "Initially, the packages should be null.");
    }
}
