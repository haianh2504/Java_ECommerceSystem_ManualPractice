package services.userManagement;

import entities.*;

public interface UserManagementService {
//    create new User
    public User createUser(PasswordHash passwordHash, PersonName name, PhoneNumber phoneNumber, Email email, UserRole userRole);
//    make status ACTIVE if have full info
    public void activateUser(Long userId,PhoneNumber phoneNumber);
//    make status BANNED
//    change Name
    public void changeUserName(Long userId, PersonName newName);
//    change phoneNumber
    public void changePhoneNumber(Long userId, PhoneNumber newPhoneNumber);
//    change email
    public void changeEmail(Long userId, Email newEmail);
//    Admin authorize
    public void promoteToAdmin(Long adminId,Long userId);
}
