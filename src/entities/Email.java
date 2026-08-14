package entities;

public class Email {
    private String email;
    private boolean isValidEmail(String email)
    {
        if(email == null){
            throw new NullPointerException("Email cannot be null");
        }
        if(email.trim().isEmpty())
        {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        {
            throw new IllegalArgumentException("Invalid Email");
        }
        return true;
    }
//    constructor
    public Email(String email)
    {
        if(email == null){
            throw new NullPointerException("Email cannot be null");
        }
        if(email.trim().isEmpty())
        {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
        {
            throw new IllegalArgumentException("Invalid Email");
        }
        this.email = email.trim();
    }
//    getter
    public String getValue()
    {
        return this.email;
    }
}
