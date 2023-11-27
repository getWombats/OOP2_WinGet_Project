package ch.hftm.oop2_winget_project.Features;

import ch.hftm.oop2_winget_project.Models.WinGetPackage;
import ch.hftm.oop2_winget_project.Utils.PromptExitCode;
import ch.hftm.oop2_winget_project.Utils.SystemLanguage;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class WinGetQuery
{
    private static int headerCounter;
    private static String columnHeaderIdText;
    private static String columnHeaderVersionText;
    private static String columnHeaderAvailableText;
    private static String columnHeaderMatchText;
    private static String columnHeaderSourceText;
    private static long consoleExitCode;

    public static void setWinGetLanguage()
    {
        String current_language = SystemLanguage.getPreferedLanguage();

            switch(current_language)
            {
                case "de":
                    columnHeaderIdText = "id";
                    columnHeaderVersionText = "version";
                    columnHeaderMatchText = "übereinstimmung";
                    columnHeaderAvailableText = "verfügbar";
                    columnHeaderSourceText = "quelle";
                    break;
                case "en":
                    columnHeaderIdText = "id";
                    columnHeaderVersionText = "version";
                    columnHeaderMatchText = "match";
                    columnHeaderAvailableText = "available";
                    columnHeaderSourceText = "source";
                    break;
                case "fr":
                    columnHeaderIdText = "id";
                    columnHeaderVersionText = "version";
                    columnHeaderMatchText = "concordance";
                    columnHeaderAvailableText = "disponsible";
                    columnHeaderSourceText = "source";
                    break;
            }
    }


    public static void QueryToList(QueryType queryType, String keyWord, ObservableList<WinGetPackage> packageList)
    {
        try
        {
            List<String> rawDataList = new ArrayList<>();

            ProcessBuilder processBuilder = new ProcessBuilder("winget", queryType.toString(), keyWord);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String readerLine;

            headerCounter = 0;
            int columnSeparatorIndexId = -1;
            int columnSeparatorIndexVersion = -1;
            int columnSeparatorIndexAvailableOrMatch = -1;
            int columnSeparatorIndexSource = -1;
            int maxPackageLineLength = -1;

            while ((readerLine = reader.readLine()) != null)
            {
                rawDataList.add(readerLine);
                System.out.println(readerLine);
            }
            reader.close();

            consoleExitCode = process.waitFor();

            if(consoleExitCode != PromptExitCode.NO_PACKAGE_FOUND.getValue())
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

                        headerCounter++;
                    }

                    // Get actual package information
                    if(isPackageLine(line))
                    {
                        // Fix some weird asia packages
                        if(containsHanScript(line))
                        {
                            int missingLength = maxPackageLineLength - line.length() - 1;

                            StringBuilder uglyPatch = new StringBuilder();
                            uglyPatch.append(" ".repeat(Math.max(0, missingLength)));

                            line = line.substring(0, columnSeparatorIndexId-missingLength) + uglyPatch + line.substring(columnSeparatorIndexId-missingLength);
                        }

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

                        packageList.add(winGetPackage);
                    }
                }
            }
        }
        catch (IOException | InterruptedException ex)
        {
            // Exceptionhandler?
        }
        catch (IndexOutOfBoundsException ex)
        {
            ex.printStackTrace();
        }
    }

    // Unicode Han = Asia
    public static boolean containsHanScript(String line)
    {
        return line.codePoints().anyMatch(
                codepoint ->
                        Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }

    private static boolean isHeaderLine(String line)
    {
        return headerCounter == 0 && !line.isBlank() && !line.contains("-") && !line.contains("\\") && !line.contains("|") && !line.contains("▒") && !line.contains("█") && line.toLowerCase().contains(columnHeaderIdText);
    }

    private static boolean isPackageLine(String line)
    {
        return !line.contains("-") && !line.toLowerCase().contains(columnHeaderIdText) && !line.isBlank() && !line.contains("▒") && !line.contains("█");
    }

    public static long getPromptExitCode()
    {
        return consoleExitCode;
    }
}
