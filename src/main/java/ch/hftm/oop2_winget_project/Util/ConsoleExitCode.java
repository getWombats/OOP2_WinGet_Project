package ch.hftm.oop2_winget_project.Util;

public enum ConsoleExitCode
{
    OK(0),
    NO_PACKAGE_FOUND(-1978335212);
    private final long value;

    private ConsoleExitCode(long value)
    {
        this.value = value;
    }

    public long getValue()
    {
        return value;
    }
}
