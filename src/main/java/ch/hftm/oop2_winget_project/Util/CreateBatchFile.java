package ch.hftm.oop2_winget_project.Util;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.PackageList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateBatchFile {

    public static void createInstallScript(PackageList packageList) {

//        String scriptPath = "install_packages.bat";
        String userHome = System.getProperty("user.home");
        String scriptPath = userHome + File.separator + "Desktop\\WinGet_InstallScript.bat";


        try(BufferedWriter bw = new BufferedWriter((new FileWriter(scriptPath)))) {
            bw.write("@echo off");

            bw.newLine();
            bw.write("for /f \"delims=: tokens=*\" %%A in ('findstr /b ::: \"%~f0\"') do @echo(%%A\n");

            bw.newLine();
            bw.write(":::  __        ___        ____      _     ___           _        _ _           ");
            bw.newLine();
            bw.write(":::  \\ \\      / (_)_ __  / ___| ___| |_  |_ _|_ __  ___| |_ __ _| | | ___ _ __ ");
            bw.newLine();
            bw.write(":::   \\ \\ /\\ / /| | '_ \\| |  _ / _ \\ __|  | || '_ \\/ __| __/ _` | | |/ _ \\ '__|     Created by "); // 5 Spaces after ascii Art
            bw.newLine();
            bw.write(":::    \\ V  V / | | | | | |_| |  __/ |_   | || | | \\__ \\ || (_| | | |  __/ |        Andres Soria");
            bw.newLine();
            bw.write(":::     \\_/\\_/  |_|_| |_|\\____|\\___|\\__| |___|_| |_|___/\\__\\__,_|_|_|\\___|_|        Luca Buetzberger");

            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("echo Do you want to install all the following packages? (Select Y for Yes or N for No)");
            bw.newLine();
            bw.write("echo.");

            bw.newLine();
            bw.write("echo Name     PackageID     Version     Source");
            bw.newLine();
            bw.write("echo ----------------------------------------------");
            for (WinGetPackage pkg : packageList.getPackages()) {
                bw.newLine();
                bw.write("echo " + String.format(pkg.getName() + "     " + pkg.getId() + "     " + pkg.getVersion() + "     " + pkg.getSource()));
            }

            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("choice /C YN /N /M \"(Y/N).");
//            Adding wait time
            bw.newLine();
            bw.write("if %errorlevel%==1 goto yes");
            bw.newLine();
            bw.write("if %errorlevel%==2 goto end");


            bw.newLine();
            bw.write(":yes");

            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.newLine();
                bw.write(String.format("winget install --id=" + packageId + " --silent --accept-package-agreements --accept-source-agreements"));
            }
            bw.newLine();
            bw.write("goto end");

            bw.newLine();
            bw.write(":end");
            bw.newLine();
            bw.write("echo Script completed.");
            bw.newLine();
            bw.write("pause");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}