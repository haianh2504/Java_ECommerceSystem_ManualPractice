package entities;

public enum UserStatus {
    ACTIVE, //  đã xác thực đầy đủ thông tin
    PENDING, // chưa xác thực đầy đủ email và số điện thoại
    BANNED; // vi phạm, gian lận, spam ( bị khoá do admin )

}
