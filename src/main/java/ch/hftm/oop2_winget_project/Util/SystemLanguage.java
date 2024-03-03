package ch.hftm.oop2_winget_project.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class SystemLanguage
{
    private static long consoleExitCode;

    public static String getPreferredLanguage()
    {
        try
        {
            List<String> rawDataList = new ArrayList<>();

            String preferredLanguage = "";

            ProcessBuilder processBuilderLanguage = new ProcessBuilder("cmd", "/C", "REG QUERY \"HKEY_Current_User\\Control Panel\\International\\User Profile\" /v Languages /t REG_MULTI_SZ /se _");
            processBuilderLanguage.redirectErrorStream(true);
            Process process_getPrefLang = processBuilderLanguage.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process_getPrefLang.getInputStream()));

            String readerLine;

            while ((readerLine = reader.readLine()) != null)
            {
                rawDataList.add(readerLine);
            }

            reader.close();

            consoleExitCode = process_getPrefLang.waitFor();

            for(String line : rawDataList)
            {
                if(line.contains("Languages"))
                {
                    String preferredLanguages = line.substring(line.indexOf("REG_MULTI_SZ") + "REG_MULTI_SZ".length()).trim();
                    preferredLanguage = preferredLanguages.substring(0, 2);
                    break;
                }
            }
            return preferredLanguage;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        return "";
    }

    public static long getPromptExitCode()
    {
        return consoleExitCode;
    }
}