package entities;

import lombok.Builder;

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
        if(id == null)
        {
            throw new NullPointerException("User id cannot be null");
        }
        if(name == null)
        {
            throw new NullPointerException("User name cannot be null");
        }
        if(userRole == null)
        {
            throw new NullPointerException("User Role cannot be null");
        }
    }
//    constructor - full info
    public User(String id, PersonName name,PhoneNumber phoneNumber, Email email, UserRole userRole)
    {
        checkNullFast(id,name,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        if(email == null)
        {
            throw new NullPointerException("User Email cannot be null");
        }
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userRole = userRole;
        this.status = UserStatus.ACTIVE;
    }
//    constructor - not full info
    public User(String id, PersonName name, UserRole userRole)
    {
        checkNullFast(id,name,userRole);
        if(phoneNumber == null)
        {
            throw new NullPointerException("User PhoneNumber cannot be null");
        }
        this.id = id;
        this.name = name;
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
        return this.phoneNumber.phoneNumber();
    }
    public final String getEmail()
    {
        return this.email.getValue();
    }
    public final String getRole()
    {
        return this.userRole.getDescription();
    }
    public final String getStatus()
    {
        return this.status.getDescription();
    }
}
