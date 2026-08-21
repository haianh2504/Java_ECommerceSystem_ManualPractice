package entities;
import lombok.Builder;

import java.time.Instant;
import java.util.Objects;
@Builder
public final class User {
    private Long id;
    private PasswordHash passwordHash;
    private PersonName name;
    private PhoneNumber phoneNumber; // can add later
    private Email email; // compulsory
    private UserRole userRole;
    private UserStatus status;
    private Instant createdAt;
    private static void validateBasicInfo(
            PasswordHash passwordHash,
            PersonName name,
            Email email,
            UserRole userRole
    ) {
        Objects.requireNonNull(passwordHash, "User passwordHash cannot be null");
        Objects.requireNonNull(name, "User name cannot be null");
        Objects.requireNonNull(email,"User email cannot be null");
        Objects.requireNonNull(userRole, "User role cannot be null");
    }
//    constructor - full info
    public User(PasswordHash passwordHash,PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole)
    {
        validateBasicInfo(passwordHash,name,email,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        else if(phoneNumber.phoneNumber().isBlank()){
            throw new IllegalArgumentException("User name cannot be blank");
        }
        // id có thể null -> postgreSQL tự generate
        this.name = name;
        this.passwordHash = passwordHash;
        if(phoneNumber == null)
        {
            this.status = UserStatus.PENDING;
        }
        else{
            this.phoneNumber = phoneNumber;
            this.status = UserStatus.ACTIVE;
        }
        this.email = email;
        this.userRole = userRole;
        this.createdAt = Instant.now();
    }

//    constructor - SQL return
    public User(Long id,PasswordHash passwordHash,PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole, UserStatus userStatus,Instant createdAt)
    {
        validateBasicInfo(passwordHash,name,email,userRole);
        this.id = Objects.requireNonNull(id, "User id cannot be null");
        this.name = name;
        this.passwordHash = passwordHash;
        // phoneNumber can be null
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
        this.status = Objects.requireNonNull(userStatus,"User status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt,"Timestamp createdAt cannot be null");
    }

//    getters
    public final Long getId()
    {
        return this.id;
    }
    public final PasswordHash getPasswordHash()
    {
        return this.passwordHash;
    }
    public final PersonName getName()
    {
        return this.name;
    }
    public final PhoneNumber getPhoneNumber()
    {
        return this.phoneNumber;
    }
    public final Email getEmail()
    {
        return this.email;
    }
    public final UserRole getRole()
    {
        return this.userRole;
    }
    public final UserStatus getStatus()
    {
        return this.status;
    }
    public final Instant getTimeCreated()
    {
        return this.createdAt;
    }
//    add phoneNumber
    private void addPhoneNumber(PhoneNumber phoneNumber)
    {
        if(this.phoneNumber != null) return;
        this.phoneNumber = Objects.requireNonNull(phoneNumber,"PhoneNumber cannot be null");
    }
//    activate account
    public void activate(PhoneNumber phoneNumber)
    {
        // Nếu đã có phone number
        if(this.phoneNumber != null){
            return;
        }
        try{
            addPhoneNumber(phoneNumber);
            this.status = UserStatus.ACTIVE;
        }catch (NullPointerException e)
        {
            throw new RuntimeException("Error while activating user: " + e.getMessage(),e);
        }
    }
//    change role
    public void changeRole(UserRole newRole)
    {
        if(newRole == null)
        {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if(this.status == UserStatus.BANNED)
        {
            throw new IllegalStateException("Banned user cannot change role");
        }
        this.userRole = newRole;
    }
//    change name
    public void changeName(PersonName newName)
    {
        if(newName == null)
        {
            throw new NullPointerException("New username cannot be null");
        }
        this.name = newName;
    }
//    change phone number
    public void changePhoneNumber(PhoneNumber phoneNumber)
    {
        if(phoneNumber == null)
        {
            throw new NullPointerException("New phoneNumber cannot be null");
        }
        if(phoneNumber.phoneNumber().equals(this.phoneNumber.phoneNumber())) return;
        this.phoneNumber = phoneNumber;
    }
//    change email
    public void changeEmail(Email email)
    {
        if(email == null)
        {
            throw new NullPointerException("New email cannot be null");
        }
        this.email = email;
    }
}
