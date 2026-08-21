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
            Long id,
            PasswordHash passwordHash,
            PersonName name,
            Email email,
            UserRole userRole
    ) {
        Objects.requireNonNull(id, "User id cannot be null");
        Objects.requireNonNull(passwordHash, "User passwordHash cannot be null");
        Objects.requireNonNull(name, "User name cannot be null");
        Objects.requireNonNull(email,"User email cannot be null");
        Objects.requireNonNull(userRole, "User role cannot be null");
    }
//    constructor - full info
    public User(Long id,PasswordHash passwordHash,PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole)
    {
        validateBasicInfo(id,passwordHash,name,email,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        else if(phoneNumber.phoneNumber().isBlank()){
            throw new IllegalArgumentException("User name cannot be blank");
        }
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
        this.status = UserStatus.ACTIVE;
        this.createdAt = Instant.now();
    }
//    constructor - not full info
    public User(Long id, PasswordHash passwordHash, PersonName name, Email email, UserRole userRole)
    {
        validateBasicInfo(id,passwordHash, name,email,userRole);
        this.id = id;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.status = UserStatus.PENDING;
        this.createdAt = Instant.now();
    }
//    constructor not full - SQL return
public User(Long id, PasswordHash passwordHash, PersonName name, Email email, UserRole userRole, UserStatus userStatus, Instant createdAt)
{
    validateBasicInfo(id,passwordHash, name,email,userRole);
    this.id = id;
    this.passwordHash = passwordHash;
    this.name = name;
    this.email = email;
    this.userRole = userRole;
    this.status = Objects.requireNonNull(userStatus,"User status cannot be null");
    this.createdAt = Objects.requireNonNull(createdAt,"Timestamp createdAt cannot be null");
}
//    constructor - full info
    public User(Long id,PasswordHash passwordHash,PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole, UserStatus userStatus,Instant createdAt)
    {
        validateBasicInfo(id,passwordHash,name,email,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        else if(phoneNumber.phoneNumber().isBlank()){
            throw new IllegalArgumentException("User name cannot be blank");
        }
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
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
//    activate account
    public void activate()
    {
        if (phoneNumber == null || email == null) {
            throw new NullPointerException("phonenumber and email cannot be null");
        }
        this.status = UserStatus.ACTIVE;
    }
//    ban account
}
