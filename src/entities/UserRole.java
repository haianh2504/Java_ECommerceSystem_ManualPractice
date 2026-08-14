package entities;

public enum UserRole {
    ADMIN("Administrator"),
    NORMAL_USER("Normal user");
    private final String description;
    UserRole(String description)
    {
        this.description = description;
    }
//    getter
    public final String getDescription()
    {
        return this.description;
    }
}
