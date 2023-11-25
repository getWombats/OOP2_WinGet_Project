package ch.hftm.oop2_winget_project.Features;

public enum SourceType
{
    MSSTORE {
        @Override
        public String toString()
        {
            return "msstore";
        }
    },
    WINGET {
        @Override
        public String toString()
        {
            return "winget";
        }
    }
}
