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
            bw.write("echo  __          ___        _____      _     _____           _        _ _           ");
            bw.newLine();
            bw.write("echo  \\ \\        / (_)      / ____|    | |   |_   _|         | |      | | |          ");
            bw.newLine();
            bw.write("echo   \\ \\  /\\  / / _ _ __ | |  __  ___| |_    | |  _ __  ___| |_ __ _| | | ___ _ __ ");
            bw.newLine();
            bw.write("echo    \\ \\/  \\/ / | | '_ \\| | |_ |/ _ \\ __|   | | | '_ \\/ __| __/ _` | | |/ _ \\ '__|");
            bw.newLine();
            bw.write("echo     \\  /\\  /  | | | | | |__| |  __/ |_   _| |_| | | \\__ \\ || (_| | | |  __/ |   ");
            bw.newLine();
            bw.write("echo      \\/  \\/   |_|_| |_|\\_____|\\___|\\__| |_____|_| |_|___/\\__\\__,_|_|_|\\___|_|   ");
            bw.newLine();
            bw.write("choice /C YNyn /N /M \"Do you want to install all the following packages? (Select Y for yes or N for No)");
            bw.newLine();
            for (WinGetPackage pkg : packageList.getPackages()) {
                bw.write(String.format(pkg.getName() + "     " + pkg.getFXId() + "     " + pkg.getVersion() + "     " + pkg.getSource()));
                bw.newLine();
            }

            bw.write("if errorlevel Y goto yes");
            bw.newLine();
            bw.write("if errorlevel y goto yes");
            bw.newLine();
            bw.write("if errorlevel N goto no");
            bw.newLine();
            bw.write("if errorlevel n goto yes");

            bw.newLine();
            bw.write("echo :yes");
            for (WinGetPackage pkg : packageList.getPackages()) {
                String packageId = pkg.getId();
                bw.write(String.format("winget install --id=" + packageId + " -e --silent --scope machine --accept-package-agreements --accept-source-agreements"));
                bw.newLine();
            }


            bw.newLine();
            bw.write("echo :no");
            bw.newLine();
            bw.write("pause");

        } catch (IOException e) {
        }
    }
}