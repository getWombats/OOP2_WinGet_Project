package Persistence;

import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Persistence.BatchFileCreator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;

public class BatchFileCreatorTest {

    private PackageList packageList;
    private String scriptPath;

    @BeforeEach
    void setUp() {
        // Set up your PackageList with some WinGetPackages.
        packageList = new PackageList("TestPackageList");
        packageList.addPackage(new WinGetPackage("TestPackage1", "TestID1", "1.0", "TestSource1"));
        packageList.addPackage(new WinGetPackage("TestPackage2", "TestID2", "2.0", "TestSource2"));

        // Set the script path.
        String userHome = System.getProperty("user.home");
        scriptPath = userHome + File.separator + "Desktop\\WinGet_InstallScript.bat";
    }

    @Test
    void createInstallScriptTest() throws IOException {
        // Test the creation of the script.
        BatchFileCreator.createInstallScript(packageList);

        // Check if the file exists.
        File scriptFile = new File(scriptPath);
        assertTrue(scriptFile.exists(), "The script file should have been created.");

        // Read the content of the created file.
        String content = Files.readString(scriptFile.toPath());

        // Testing for content: Batch Tags.
        assertTrue(content.contains("@echo off"), "The file should contain '@echo off'.");
        assertTrue(content.contains("timeout"), "The file should contain '@timeout'.");

        // Testing for content: TestPackage1.
        assertTrue(content.contains("TestPackage1"), "The file should contain 'TestPackage1'.");
        assertTrue(content.contains("TestID1"), "The file should contain 'TestID1'.");
        assertTrue(content.contains("1.0"), "The file should contain '1.0'.");
        assertTrue(content.contains("TestSource1"), "The file should contain 'TestSource1'.");

        // Testing for content: TestPackage2.
        assertTrue(content.contains("TestPackage2"), "The file should contain 'TestPackage1'.");
        assertTrue(content.contains("TestID2"), "The file should contain 'TestID2'.");
        assertTrue(content.contains("2.0"), "The file should contain '2.0'.");
        assertTrue(content.contains("TestSource2"), "The file should contain 'TestSource2'.");

        /// Testing for content: Menu.
        assertTrue(content.contains("echo 1. Install packages"), "The file should contain 'TestPackage1'.");
        assertTrue(content.contains("echo 2. Update packages"), "The file should contain 'TestID2'.");
        assertTrue(content.contains("echo 3. Remove packages"), "The file should contain '2.0'.");
        assertTrue(content.contains("echo 4. Cancel"), "The file should contain 'TestSource2'.");
        assertTrue(content.contains("if /i %errorlevel%==1 goto install"), "The file should contain '2.0'.");
        assertTrue(content.contains("if /i %errorlevel%==2 goto update"), "The file should contain '2.0'.");
        assertTrue(content.contains("if /i %errorlevel%==3 goto remove"), "The file should contain '2.0'.");
        assertTrue(content.contains("if /i %errorlevel%==4 goto end"), "The file should contain '2.0'.");
    }

    @AfterEach
    void tearDown() {
        // Clean up.
        File scriptFile = new File(scriptPath);
        if (scriptFile.exists()) {
            assertTrue(scriptFile.delete(), "The script file should be deleted after the test.");
        }
    }
}