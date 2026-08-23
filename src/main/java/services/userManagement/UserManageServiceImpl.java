package services.userManagement;

import entities.*;
import repository.userRepo.UserRepository;

import java.util.Objects;
import java.util.Optional;

public final class UserManageServiceImpl implements UserManagementService{
    // Lấy UserRepository làm biến tham chiếu quyết định các phương thức
    private final UserRepository userRepository;
//    constructor
    public UserManageServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
//    find user by ID
    @Override
    public User findUserById(Long userId) {
        Objects.requireNonNull(userId,"UserId cannot be null");
        return userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
    }
//    find user by Email
    @Override
    public User findUserByEmail(Email email) {
        Objects.requireNonNull(email, "User email cannot be null");
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }
    //    create new user after REGISTER
    @Override
    public User createUser(PasswordHash passwordHash, PersonName name, PhoneNumber phoneNumber, Email email, UserRole userRole){
        // new User
        if(userRepository.findByEmail(email).isPresent())
        {
            throw new IllegalStateException("This email has already been used");
        }
        User user = new User(passwordHash,name,phoneNumber,email,userRole);
        // phoneNumber is optional
        userRepository.save(user);
        return user;
    }
//    activate User
    @Override
    public void activateUser(Long userId, PhoneNumber phoneNumber) throws RuntimeException
    {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
        user.activate(phoneNumber);
        userRepository.update(user);
    }
//    change name
    @Override
    public void changeUserName(Long userId, PersonName newName)
    {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        if(newName == null)
        {
            throw new NullPointerException("New username cannot be null");
        }
        user.changeName(newName);
        userRepository.update(user);
    }
//    change phone number
    @Override
    public void changePhoneNumber(Long userId, PhoneNumber newPhoneNumber){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        if(newPhoneNumber == null)
        {
            throw new NullPointerException("New phoneNumber cannot be null");
        }
        user.changePhoneNumber(newPhoneNumber);
        userRepository.update(user);
    }
//    change email
    @Override
    public void changeEmail(Long userId, Email newEmail){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        if(newEmail == null)
        {
            throw new NullPointerException("New email cannot be null");
        }
        user.changeEmail(newEmail);
        userRepository.update(user);
    }
//    change Role
    @Override
    public void promoteToAdmin(Long adminId,Long userId)
    {
        User admin = userRepository.findById(adminId).orElseThrow(
                () -> new RuntimeException("User admin not found")
        );
        if(admin.getRole() != UserRole.ADMIN)
        {
            throw new IllegalStateException("Do not have permission to promote user");
        }
        User targetUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Target user not found")
        );
        if(targetUser.getStatus() == UserStatus.BANNED)
        {
            throw new IllegalStateException("Banned user cannot become admin");
        }
        targetUser.changeRole(UserRole.ADMIN);
        userRepository.update(targetUser);
    }
}
