package ch.hftm.oop2_winget_project.Api;

import ch.hftm.oop2_winget_project.Model.WinGetPackage;

public interface IControllerBase
{
    void setTableViewData();
    void setTableViewSource();
    void refreshTableViewContent();
    WinGetPackage getObjectFromSelection();
    void addButtonToTableView();
}
