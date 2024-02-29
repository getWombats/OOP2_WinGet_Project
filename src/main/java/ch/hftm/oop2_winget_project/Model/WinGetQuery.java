package ch.hftm.oop2_winget_project.Model;

import ch.hftm.oop2_winget_project.App;
import ch.hftm.oop2_winget_project.Util.QueryType;
import ch.hftm.oop2_winget_project.Util.SourceType;
import ch.hftm.oop2_winget_project.Util.ConsoleExitCode;
import ch.hftm.oop2_winget_project.Util.UniCodeChecker;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WinGetQuery
{
    private final String columnHeaderIdText = App.getAppInstance().getWinGetSettings().getColumns().get("columnId");
    private final String columnHeaderVersionText = App.getAppInstance().getWinGetSettings().getColumns().get("columnVersion");
    private final String columnHeaderAvailableText = App.getAppInstance().getWinGetSettings().getColumns().get("columnAvailable");
    private final String columnHeaderMatchText = App.getAppInstance().getWinGetSettings().getColumns().get("columnMatch");
    private final String columnHeaderSourceText = App.getAppInstance().getWinGetSettings().getColumns().get("columnSource");
    private final Pattern VALIDLINE_REGEX = Pattern.compile("(.*[0-9a-zA-Z]+.*)");
    private int headerCounter = 0;
    private int columnSeparatorIndexId = -1;
    private int columnSeparatorIndexVersion = -1;
    private int columnSeparatorIndexAvailableOrMatch = -1;
    private int columnSeparatorIndexSource = -1;
    private int maxPackageLineLength = -1;
    private long consoleExitCode;
    private QueryType queryType;
    private List<String> rawDataList = Collections.synchronizedList(new ArrayList());

    public WinGetQuery(QueryType qt)
    {
        this.queryType = qt;
    }

    public void queryToList(String keyWord) throws IOException, InterruptedException
    {
        ProcessBuilder processBuilder = null;

        switch(queryType){
            case SEARCH, LIST -> processBuilder = new ProcessBuilder("winget.exe", queryType.toString(), keyWord, "--accept-source-agreements");
            case UPGRADE -> processBuilder = new ProcessBuilder("winget.exe", queryType.toString(), "--include-unknown", "--accept-source-agreements");
        }

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String readerLine;

        while ((readerLine = reader.readLine()) != null)
        {
            rawDataList.add(readerLine);
        }
        reader.close();

        consoleExitCode = process.waitFor();
    }

    public void CreatePackageList(ObservableList<WinGetPackage> packageList)
    {
        for(String line : rawDataList)
        {
            // Get index when each column begins as separator if line is table header, only executes once
            if(headerCounter < 1 && isHeaderLine(line))
            {
                columnSeparatorIndexId = line.toLowerCase().indexOf(columnHeaderIdText);
                columnSeparatorIndexVersion = line.toLowerCase().indexOf(columnHeaderVersionText);

                if(line.toLowerCase().contains(columnHeaderMatchText))
                {
                    columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderMatchText);
                }
                else if (line.toLowerCase().contains(columnHeaderAvailableText))
                {
                    columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderAvailableText);
                }

                columnSeparatorIndexSource = line.toLowerCase().indexOf(columnHeaderSourceText);
                maxPackageLineLength = columnSeparatorIndexSource + SourceType.MSSTORE.toString().length();

                headerCounter++;
            }

            // Get actual package information
            if(isPackageLine(line))
            {
                // Fix some weird asia packages
                if(UniCodeChecker.containsHanScript(line))
                {
                    line = ManipulateHanLine(line);
                }

                System.out.println(line);
                String packageVersion;
                if(columnSeparatorIndexAvailableOrMatch > -1)
                {
                    packageVersion = line.substring(columnSeparatorIndexVersion, columnSeparatorIndexAvailableOrMatch).trim();
                }
                else
                {
                    packageVersion = line.substring(columnSeparatorIndexVersion, columnSeparatorIndexSource).trim();
                }

                WinGetPackage winGetPackage = new WinGetPackage(
                        line.substring(0, columnSeparatorIndexId).trim(), // Package Name
                        line.substring(columnSeparatorIndexId, columnSeparatorIndexVersion).trim(), // Package ID
                        packageVersion,
                        line.substring(columnSeparatorIndexSource).trim() // Package Source
                );

                // Compare search results with installed packages to set relating packages as installed
                if(queryType == QueryType.SEARCH)
                {
                    SetInstalledPackage(winGetPackage);
                }
                packageList.add(winGetPackage);
            }
        }
    }

    public void CreateUpdateList(ObservableList<WinGetPackage> packageList)
    {
        for(String line : rawDataList)
        {
            // Get index when each column begins as separator if line is table header, only executes once
            if(isHeaderLine(line))
            {
                columnSeparatorIndexId = line.toLowerCase().indexOf(columnHeaderIdText);
                columnSeparatorIndexVersion = line.toLowerCase().indexOf(columnHeaderVersionText);

                if(line.toLowerCase().contains(columnHeaderMatchText))
                {
                    columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderMatchText);
                }
                else if (line.toLowerCase().contains(columnHeaderAvailableText))
                {
                    columnSeparatorIndexAvailableOrMatch = line.toLowerCase().indexOf(columnHeaderAvailableText);
                }

                columnSeparatorIndexSource = line.toLowerCase().indexOf(columnHeaderSourceText);
                maxPackageLineLength = columnSeparatorIndexSource + SourceType.MSSTORE.toString().length();
            }

            // Get actual package information
            if(isPackageLine(line) && !line.toLowerCase().contains("aktualisierungen") && !line.toLowerCase().contains("zielgruppenadressierung"))
            {
                // Fix some weird asia packages
                if(UniCodeChecker.containsHanScript(line))
                {
                    line = ManipulateHanLine(line);
                }

                WinGetPackage winGetPackage = new WinGetPackage(
                        line.substring(0, columnSeparatorIndexId).trim(), // Package Name
                        line.substring(columnSeparatorIndexId, columnSeparatorIndexVersion).trim(), // Package ID
                        line.substring(columnSeparatorIndexVersion, columnSeparatorIndexAvailableOrMatch).trim(), // Package version
                        line.substring(columnSeparatorIndexAvailableOrMatch, columnSeparatorIndexSource).trim(), // Package update version
                        line.substring(columnSeparatorIndexSource).trim() // Package Source
                );

                packageList.add(winGetPackage);
            }
        }
    }

    private String ManipulateHanLine(String line)
    {
        int missingLength = maxPackageLineLength - line.length() - 1;

        StringBuilder uglyPatch = new StringBuilder();
        uglyPatch.append(" ".repeat(Math.max(0, missingLength)));

        return line.substring(0, columnSeparatorIndexId-missingLength) + uglyPatch + line.substring(columnSeparatorIndexId-missingLength);
    }

    private void SetInstalledPackage(WinGetPackage winGetPackage)
    {
        for(WinGetPackage installedPackage : PackageList.getInstalledPackageList())
        {
            if(installedPackage.getId().equals(winGetPackage.getId()))
            {
                winGetPackage.setInstalled(true);
                break;
            }
        }
    }

    private boolean isHeaderLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
        return line.toLowerCase().contains(columnHeaderIdText) && line.toLowerCase().contains(columnHeaderVersionText) && line.toLowerCase().contains(columnHeaderSourceText) && matcher.find();
    }

    private boolean isPackageLine(String line)
    {
        Matcher matcher = VALIDLINE_REGEX.matcher(line);
//        return !line.isBlank() && !line.toLowerCase().contains(columnHeaderIdText) && !line.trim().equals("-") && !line.trim().contains("---") && !line.trim().equals("\\") && !line.trim().contains("▒") && !line.trim().contains("█");
        return !line.toLowerCase().contains(columnHeaderIdText) && !line.toLowerCase().contains(columnHeaderVersionText) && !line.toLowerCase().contains(columnHeaderSourceText) && matcher.find() && !line.contains("▒") && !line.contains("█");
    }

    public long getConsoleExitCode()
    {
        return consoleExitCode;
    }
}
