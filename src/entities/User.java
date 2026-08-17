package entities;

import lombok.Builder;

import java.util.Objects;

@Builder
public final class User {
    private String id;
    private PersonName name;
    private PhoneNumber phoneNumber;
    private Email email;
    private UserRole userRole;
    private UserStatus status;
    private void checkNullFast(String id, PersonName name, UserRole userRole)
    {
        if(id == null) {
            throw new NullPointerException("");
        }
        if(name == null)
        {
            throw new NullPointerException();
        }
        if(userRole == null)
        {
            throw new NullPointerException("User Role cannot be null");
        }
    }
//    initializer block
    {
        this.id = Objects.requireNonNull(id, "User id cannot be null");
        if(id.isBlank()) throw new IllegalArgumentException("UserId cannot be blank");
        this.name = Objects.requireNonNull(name, "User name cannot be null");
        if(name.getName().isBlank()) throw new IllegalArgumentException("User name cannot be blank");
        this.userRole = Objects.requireNonNull(userRole, "User Role cannot be null");
    }
    public User(String id, PersonName name, UserRole userRole){};
//    constructor - full info
    public User(String id, PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole)
    {
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        else if(phoneNumber.phoneNumber().isBlank()){
            throw new IllegalArgumentException("User name cannot be blank");
        }
        if(email == null)
        {
            throw new NullPointerException("User Email cannot be null");
        }
        else if(email.email().isBlank()){
            throw new IllegalArgumentException("User Email cannot be blank");
        }
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
        this.status = UserStatus.ACTIVE;
    }
//    constructor - not full info
    public User(String id, PersonName name, Email email, UserRole userRole)
    {
        checkNullFast(id,name,userRole);
        if(email == null)
        {
            throw new NullPointerException("User Email cannot be null");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.status = UserStatus.PENDING;
    }
    public User(String id, PersonName name,PhoneNumber phoneNumber, UserRole userRole)
    {
        checkNullFast(id,name,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.status = UserStatus.PENDING;
    }
//    getters
    public final String getId()
    {
        return this.id;
    }
    public final String getName()
    {
        return this.name.getName();
    }
    public final String getPhoneNumber()
    {
        if(this.phoneNumber == null)
        {
            return "";
        }
        return this.phoneNumber.phoneNumber();
    }
    public final String getEmail()
    {
        if(this.email == null)
        {
            return "";
        }
        return this.email.email();
    }
    public final String getRole()
    {
        return this.userRole.getDescription();
    }
    public final String getStatus()
    {
        return this.status.getDescription();
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
