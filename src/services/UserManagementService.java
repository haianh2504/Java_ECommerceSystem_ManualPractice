package services;

import entities.*;

public interface UserManagementService {
//    create new User
    public User createUser(String id, PersonName name, UserRole userRole);
//    make status ACTIVE if have full info
    public void activateUser(String userId);
//    make status BANNED
    public void banUser(String userId);
//    change Name
    public void changeUserName(String userId, PersonName newName);
//    change phoneNumber
    public void changePhoneNumber(String userId, PhoneNumber newPhoneNumber);
//    change email
    public void changeEmail(String userId, Email newEmail);
//    change Role
    public void adminAuthorize(String userId);
}
