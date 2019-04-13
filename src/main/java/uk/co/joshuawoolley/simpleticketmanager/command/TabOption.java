package uk.co.joshuawoolley.simpleticketmanager.command;

public class TabOption {
    private String label;
    private String permission;

    public TabOption(String label, String permission) {
        this.label = label;
        this.permission = permission;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public String getPermission() {
        return this.permission;
    }
}
