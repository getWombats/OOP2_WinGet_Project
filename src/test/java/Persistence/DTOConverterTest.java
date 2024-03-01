package Persistence;

import ch.hftm.oop2_winget_project.Model.ListManager;
import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.ListManagerDTO;
import ch.hftm.oop2_winget_project.Model.PackageListDTO;
import ch.hftm.oop2_winget_project.Model.WinGetPackageDTO;

import ch.hftm.oop2_winget_project.Persistence.DTOConverter;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
public class DTOConverterTest {
    private ListManager listManager;
    private ListManagerDTO listManagerDTO;

    @BeforeEach
    void setUp() {
        // Set up your test objects here

//        listManager = ListManager.getInstance();
//        listManager.createPackageList(packageList);

        WinGetPackage winGetPackage = new WinGetPackage();
        winGetPackage.setName("TestPackage");
        winGetPackage.setId("1");
        winGetPackage.setVersion("1.0");
        winGetPackage.setSource("TestSource");

        PackageList packageList = new PackageList("TestList");
        packageList.addPackage(winGetPackage);



        WinGetPackageDTO winGetPackageDTO = new WinGetPackageDTO();
        winGetPackageDTO.setName("TestPackage");
        winGetPackageDTO.setId("1");
        winGetPackageDTO.setVersion("1.0");
        winGetPackageDTO.setSource("TestSource");

        PackageListDTO packageListDTO = new PackageListDTO();
        packageListDTO.setName("TestList");
        packageListDTO.setPackages(FXCollections.observableArrayList(Arrays.asList(winGetPackageDTO)));

        listManagerDTO = new ListManagerDTO();
        listManagerDTO.setList(FXCollections.observableArrayList(Arrays.asList(packageListDTO)));
    }

    @Test
    void testToListManagerDTOConversion() {
        ListManagerDTO convertedDTO = DTOConverter.toListManagerDTO(listManager);
        assertNotNull(convertedDTO);
        assertFalse(convertedDTO.getList().isEmpty());
        assertEquals("TestList", convertedDTO.getList().get(0).getName());
    }

    @Test
    void testFromListManagerDTOConversion() {
        ListManager convertedListManager = DTOConverter.fromListManagerDTO(listManagerDTO);
        assertNotNull(convertedListManager);
        assertFalse(convertedListManager.getLists().isEmpty());
        assertEquals("TestList", convertedListManager.getLists().get(0).getName());
    }

    @Test
    void testToPackageListDTOConversion() {
        PackageList packageList = listManager.getLists().get(0);
        PackageListDTO packageListDTO = DTOConverter.toPackageListDTO(packageList);
        assertNotNull(packageListDTO);
        assertEquals("TestList", packageListDTO.getName());
        assertFalse(packageListDTO.getPackages().isEmpty());
    }

    @Test
    void testFromPackageListDTOConversion() {
        PackageListDTO packageListDTO = listManagerDTO.getList().get(0);
        PackageList packageList = DTOConverter.fromPackageListDTO(packageListDTO);
        assertNotNull(packageList);
        assertEquals("TestList", packageList.getName());
        assertFalse(packageList.getFXPackages().isEmpty());
    }

    @Test
    void testToWinGetPackageDTOConversion() {
        WinGetPackage winGetPackage = listManager.getLists().get(0).getFXPackages().get(0);
        WinGetPackageDTO winGetPackageDTO = DTOConverter.toWinGetPackageDTO(winGetPackage);
        assertNotNull(winGetPackageDTO);
        assertEquals("TestPackage", winGetPackageDTO.getName());
    }

    @Test
    void testFromWinGetPackageDTOConversion() {
        WinGetPackageDTO winGetPackageDTO = listManagerDTO.getList().get(0).getPackages().get(0);
        WinGetPackage winGetPackage = DTOConverter.fromWinGetPackageDTO(winGetPackageDTO);
        assertNotNull(winGetPackage);
        assertEquals("TestPackage", winGetPackage.getName());
    }
}
