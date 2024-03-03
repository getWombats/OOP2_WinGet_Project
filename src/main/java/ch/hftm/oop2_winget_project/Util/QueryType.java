package ch.hftm.oop2_winget_project.Util;

public enum QueryType
{
    SEARCH {
        @Override
        public String toString()
        {
            return "search";
        }
    },
    LIST {
        @Override
        public String toString()
        {
            return "list";
        }
    },
    INSTALL {
        @Override
        public String toString()
        {
            return "install";
        }
    },
    UPGRADE {
        @Override
        public String toString()
        {
            return "upgrade";
        }
    },
    UNINSTALL {
        @Override
        public String toString()
        {
            return "uninstall";
        }
    }
}
