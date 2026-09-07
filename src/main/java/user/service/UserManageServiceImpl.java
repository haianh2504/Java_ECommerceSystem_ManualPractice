package user.service;

import exception.business.detailed_exceptions.AccountBannedException;
import exception.business.detailed_exceptions.EmailAlreadyInUseException;
import exception.business.detailed_exceptions.PhoneAlreadyInUseException;
import exception.business.detailed_exceptions.UserNotAuthorizedException;
import exception.resource.detailed_exceptions.UserNotFoundException;
import user.repository.UserRepository;
import user.entities.*;

import java.util.Objects;

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
                ()-> new UserNotFoundException(userId)
        );
    }
//    find user by Email
    @Override
    public User findUserByEmail(Email email) {
        Objects.requireNonNull(email, "User email cannot be null");
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email)
        );
    }
    //    create new user after REGISTER
    @Override
    public User createUser(PasswordHash passwordHash, PersonName name, PhoneNumber phoneNumber, Email email, UserRole userRole){
        // new User
        if(userRepository.findByEmail(email).isPresent())
        {
            throw new EmailAlreadyInUseException();
        }
        // phoneNumber is optional
        User user = userRepository.save(new User(passwordHash,name,phoneNumber,email,userRole));
        return user;
    }
//    activate User
    @Override
    public void activateUser(Long userId, PhoneNumber phoneNumber) throws RuntimeException
    {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new UserNotFoundException(userId));
        user.activate(phoneNumber);
        userRepository.update(user);
    }
//    change name
    @Override
    public void changeUserName(Long userId, PersonName newName)
    {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
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
                () -> new UserNotFoundException(userId)
        );
        if(newPhoneNumber == null)
        {
            throw new NullPointerException("New phoneNumber cannot be null");
        }
        if(userRepository.findByPhoneNumber(newPhoneNumber).isPresent())
        {
            throw new PhoneAlreadyInUseException();
        }
        user.changePhoneNumber(newPhoneNumber);
        userRepository.update(user);
    }
//    change email
    @Override
    public void changeEmail(Long userId, Email newEmail){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(newEmail == null)
        {
            throw new NullPointerException("New email cannot be null");
        }
        if(userRepository.findByEmail(newEmail).isPresent())
        {
            throw new EmailAlreadyInUseException();
        }
        user.changeEmail(newEmail);
        userRepository.update(user);
    }
//    change Role
    @Override
    public void promoteToAdmin(Long adminId,Long userId)
    {
        User admin = userRepository.findById(adminId).orElseThrow(
                () -> new UserNotFoundException(adminId)
        );
        if(admin.getRole() != UserRole.ADMIN)
        {
            throw new UserNotAuthorizedException();
        }
        User targetUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if(targetUser.getStatus() == UserStatus.BANNED)
        {
            throw new AccountBannedException();
        }
        targetUser.authorize();
        userRepository.update(targetUser);
    }
}
