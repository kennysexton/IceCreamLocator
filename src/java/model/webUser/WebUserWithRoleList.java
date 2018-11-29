/*
This class will be good for web user update - provides a single web user plus the list of user Roles (for pick list).
 */
package model.webUser;

public class WebUserWithRoleList {
    
    public StringData webUser = new StringData();
    public model.role.StringDataList role = new model.role.StringDataList();
    
}