package entities;

public enum UserStatus {
    ACTIVE("Active"), //  đã xác thực đầy đủ thông tin
    PENDING("Pending"), // chưa xác thực đầy đủ email và số điện thoại
    BANNED("Banned"); // vi phạm, gian lận, spam ( bị khoá do admin )
//
    private final String description;
    UserStatus(String description)
    {
        this.description = description;
    }
//    getter
    public String getDescription() {
        return this.description;
    }
}
