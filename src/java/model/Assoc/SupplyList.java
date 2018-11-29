/*
This class will be good for web user update - provides a single web user plus the list of user Roles (for pick list).
 */
package model.Assoc;


public class SupplyList {
    
    public StringData supplyS = new StringData();
    public model.Other.StringDataList flavor_dropdown = new model.Other.StringDataList();
    public model.webUser.StringDataList store_dropdown = new model.webUser.StringDataList();
    
}