package ch.hftm.oop2_winget_project.Persistence;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;
import ch.hftm.oop2_winget_project.Model.PackageList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BatchFileCreator {

    public static void createInstallScript(PackageList packageList) {

        String userHome = System.getProperty("user.home");
        String scriptPath = userHome + File.separator + "Desktop\\WinGet_InstallScript.bat";


        try(BufferedWriter bw = new BufferedWriter((new FileWriter(scriptPath)))) {

            // Turns output in command line off.
            bw.write("@echo off");

            // Formatting for welcome Ascii-Art because of unsupported characters.
            bw.newLine();
            bw.write("for /f \"delims=: tokens=*\" %%A in ('findstr /b ::: \"%~f0\"') do @echo(%%A\n");

            // Ascii-Art
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
            bw.write("timeout /t 1 /nobreak >nul");
            bw.newLine();
            bw.write("echo This script contains the following packages: ");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("timeout /t 1 /nobreak >nul");

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
            bw.write(":menue");
            bw.newLine();
            bw.write("timeout /t 1 /nobreak >nul");
            bw.newLine();
            bw.write("echo What actions do you want to perform?");
            bw.newLine();
            bw.write("echo 1. Install packages");
            bw.newLine();
            bw.write("echo 2. Update packages");
            bw.newLine();
            bw.write("echo 3. Remove packages");
            bw.newLine();
            bw.write("echo 4. Cancel");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("choice /C 1234 /N /M \"Enter your choice... (1, 2, 3 or 4)");
            bw.newLine();
            bw.write("if /i %errorlevel%==1 goto install");
            bw.newLine();
            bw.write("if /i %errorlevel%==2 goto update");
            bw.newLine();
            bw.write("if /i %errorlevel%==3 goto remove");
            bw.newLine();
            bw.write("if /i %errorlevel%==4 goto end");
            bw.newLine();
            bw.write("echo.");


            bw.newLine();
            bw.write(":install");
            bw.newLine();
            bw.write("choice /C YN /N /M \"You are about to install new packages. Continue? (Yes/Y or No/N)");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("if /i %errorlevel%==1 goto doinstall");
            bw.newLine();
            bw.write("if /i %errorlevel%==2 goto menue");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write(":doinstall");
            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.newLine();
                bw.write(String.format("winget install --id=" + packageId + " --silent --accept-package-agreements --accept-source-agreements"));
            }
            bw.newLine();
            bw.write("goto end");


            bw.newLine();
            bw.write(":update");
            bw.newLine();
            bw.write("choice /C YN /N /M \"You are about to update existing packages. Continue? (Yes/Y or No/N)");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("if /i %errorlevel%==1 goto doupdate");
            bw.newLine();
            bw.write("if /i %errorlevel%==2 goto menue");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write(":doupdate");
            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.newLine();
                bw.write(String.format("winget update --id=" + packageId + " --silent --accept-package-agreements --accept-source-agreements"));
            }
            bw.newLine();
            bw.write("goto end");

            bw.newLine();
            bw.write(":remove");
            bw.newLine();
            bw.write("choice /C YN /N /M \"You are about to remove packages. Continue? (Yes/Y or No/N)");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write("if /i %errorlevel%==1 goto doremove");
            bw.newLine();
            bw.write("if /i %errorlevel%==2 goto menue");
            bw.newLine();
            bw.write("echo.");
            bw.newLine();
            bw.write(":doremove");
            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.newLine();
                bw.write(String.format("winget remove --id=" + packageId + " --silent"));
            }
            bw.newLine();
            bw.write("goto end");



            bw.newLine();
            bw.write(":end");
            bw.newLine();
            bw.write("echo Exiting script...");
            bw.newLine();
            bw.write("timeout /t 3 /nobreak >nul");
            bw.newLine();
            bw.write("exit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}