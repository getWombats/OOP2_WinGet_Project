package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.PackageList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CreateBatFile {

    public static void createInstallScript(PackageList packageList) {

        String scriptPath = "install_packages.bat";

        try(BufferedWriter bw = new BufferedWriter((new FileWriter(scriptPath)))) {
            bw.write("@echo off");
            bw.newLine();

            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.write(String.format("winget install §s --accept-package-agreements --accept-source-agreements", packageId));
                bw.newLine();
            }
        } catch (IOException e) {
        }
    }
}
