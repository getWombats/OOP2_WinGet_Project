package ch.hftm.oop2_winget_project.Api;

import ch.hftm.oop2_winget_project.Models.WinGetPackage;

public interface TableViewModificationable
{
    void setTableViewData();
    void setTableViewSource();
    void refreshTableViewContent();
    WinGetPackage getObjectFromSelection();
    void addButtonToTableView();
}
