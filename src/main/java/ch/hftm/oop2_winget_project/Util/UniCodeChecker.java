package ch.hftm.oop2_winget_project.Util;

public final class UniCodeChecker
{
    // Unicode Han = Asia
    public static boolean containsHanScript(String line)
    {
        try {
            return line.codePoints().anyMatch(
                    codepoint ->
                            Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN);
        } catch (NullPointerException e) {
            return true;
        }
    }
}