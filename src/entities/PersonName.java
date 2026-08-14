package entities;

public class PersonName {
    private String name;
    private boolean isValidName(String name)
    {
        if(name == null)
        {
            throw new NullPointerException("Name cannot be null");
        }
        if(name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(!name.matches("^[\\p{L}]+(?:[ '\\-][\\p{L}]+)*$")) {
            throw new IllegalArgumentException("Invalid person name");
        }
        return true;
    }
//    constructor
    public PersonName(String name)
    {
        if(name == null)
        {
            throw new NullPointerException("Name cannot be null");
        }
        if(name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(!name.matches("^[\\p{L}]+(?:[ '\\-][\\p{L}]+)*$")) {
            throw new IllegalArgumentException("Invalid person name");
        }
        this.name = name.trim();
    }
//    getter
    public String getName()
    {
        return this.name;
    }
//    setter
    public PersonName setName(String name)
    {
        isValidName(name);
        this.name = name.trim();
        return this;
    }
}
