package ch.hftm.oop2_winget_project.Utils;

public enum PromptExitCode
{
    OK(0),
    NO_PACKAGE_FOUND(-1978335212);
    private final long value;

    private PromptExitCode(long value)
    {
        this.value = value;
    }

    public long getValue()
    {
        return value;
    }
}
