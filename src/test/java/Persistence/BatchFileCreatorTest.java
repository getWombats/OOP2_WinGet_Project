package Persistence;

import ch.hftm.oop2_winget_project.Model.PackageList;
import ch.hftm.oop2_winget_project.Model.WinGetPackage;

import ch.hftm.oop2_winget_project.Persistence.BatchFileCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class BatchFileCreatorTest {
    @TempDir
    Path tempDir; // JUnit Jupiter provides this temporary directory feature

    private PackageList packageList;
    private String scriptPath;

    @BeforeEach
    void setUp() {
        // Setup your PackageList with some WinGetPackages
        packageList = new PackageList("TestPackageList");
        packageList.addPackage(new WinGetPackage("TestPackage1", "TestID1", "1.0", "TestSource"));
        packageList.addPackage(new WinGetPackage("TestPackage2", "TestID2", "1.1", "TestSource"));

        // Override the user home system property to use the temporary directory for tests
        System.setProperty("user.home", tempDir.toString());
        scriptPath = System.getProperty("user.home") + File.separator + "Desktop\\WinGet_InstallScript.bat";
    }

    @Test
    void createInstallScriptTest() throws IOException {
        // Test the creation of the script
        BatchFileCreator.createInstallScript(packageList);

        // Check if the file exists
        File scriptFile = new File(scriptPath);
        assertTrue(scriptFile.exists(), "The script file should have been created.");

        // Read the content of the created file
        String content = Files.readString(scriptFile.toPath());

        // Verify that the content includes certain strings we expect to find
        assertTrue(content.contains("@echo off"), "The file should contain '@echo off'.");
        assertTrue(content.contains("TestPackage1"), "The file should contain 'TestPackage1'.");
        assertTrue(content.contains("TestID1"), "The file should contain 'TestID1'.");
        assertTrue(content.contains("winget install --id=TestID1"), "The file should contain winget install command for TestID1.");
    }

    @AfterEach
    void tearDown() {
        // Clean up if necessary
        File scriptFile = new File(scriptPath);
        if (scriptFile.exists()) {
            assertTrue(scriptFile.delete(), "The script file should be deleted after the test.");
        }
    }
}
