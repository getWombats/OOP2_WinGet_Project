package ch.hftm.oop2_winget_project.Service;

public final class UniCodeChecker
{
    // Unicode Han = Asia
    public static boolean containsHanScript(String line)
    {
        return line.codePoints().anyMatch(
                codepoint ->
                        Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
    }
}
