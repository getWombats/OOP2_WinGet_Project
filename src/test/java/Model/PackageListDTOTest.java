package Model;

import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageListDTOTest {

    private PackageListDTO packageListDTO;

    @BeforeEach
    void setUp() {
        // Initialize PackageListDTO object; Used in multiple tests.
        packageListDTO = new PackageListDTO();
    }

    @Test
    void testDefaultConstructor() {
        assertNull(packageListDTO.getId(), "Default ID should be null");
        assertNull(packageListDTO.getName(), "Default name should be null");
        assertEquals(0, packageListDTO.getSize(), "Default size should be 0");
        assertNull(packageListDTO.getPackages(), "Default packages should be null");
    }

    @Test
    void testIdGetterSetter() {
        String newId = "NewId";
        packageListDTO.setId(newId);
        assertEquals(newId, packageListDTO.getId(), "The ID should be 'NewId'");
    }

    @Test
    void testNameGetterSetter() {
        String newName = "NewName";
        packageListDTO.setName(newName);
        assertEquals(newName, packageListDTO.getName(), "The name should be 'NewName'");
    }

    @Test
    void testSizeGetterSetter() {
        int newSize = 10;
        packageListDTO.setSize(newSize);
        assertEquals(newSize, packageListDTO.getSize(), "The size should be 10");
    }

    @Test
    void testPackagesGetterSetter() {
        WinGetPackageDTO packageOne = new WinGetPackageDTO();
        packageOne.setName("PackageOne");
        packageOne.setId("1");
        packageOne.setVersion("1.0");
        packageOne.setSource("SourceOne");

        WinGetPackageDTO packageTwo = new WinGetPackageDTO();
        packageTwo.setName("PackageTwo");
        packageTwo.setId("2");
        packageTwo.setVersion("2.0");
        packageTwo.setSource("SourceTwo");

        List<WinGetPackageDTO> newPackages = new ArrayList<>(Arrays.asList(packageOne, packageTwo));
        packageListDTO.setPackages(newPackages);
        assertEquals(newPackages, packageListDTO.getPackages(), "The packages list should match the set list");
        assertEquals(2, packageListDTO.getPackages().size(), "The packages list size should be 2");
    }
}