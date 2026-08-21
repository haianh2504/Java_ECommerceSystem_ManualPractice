package services.userManagement;

import entities.*;

public interface UserManagementService {
//    create new User
    public User createUser(Long id, PersonName name, UserRole userRole);
//    make status ACTIVE if have full info
    public void activateUser(Long userId);
//    make status BANNED
    public void banUser(Long userId);
//    change Name
    public void changeUserName(Long userId, PersonName newName);
//    change phoneNumber
    public void changePhoneNumber(String userId, PhoneNumber newPhoneNumber);
//    change email
    public void changeEmail(Long userId, Email newEmail);
//    change Role
    public void adminAuthorize(Long userId);
}
