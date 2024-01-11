package ch.hftm.oop2_winget_project.Util;

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
