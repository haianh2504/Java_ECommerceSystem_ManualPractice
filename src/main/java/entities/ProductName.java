package entities;

public class ProductName {
    private String name;
    private boolean isValidName(String name)
    {
        if(name == null)
        {
            throw new NullPointerException("Name Product cannot be null");
        }
        if(name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name Product cannot be empty");
        }
        return true;
    }
    //    constructor
    public ProductName(String name)
    {
        if(name == null)
        {
            throw new NullPointerException("Name Product cannot be null");
        }
        if(name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name Product cannot be empty");
        }
        this.name = name.trim();
    }
    //    getter
    public String getName()
    {
        return this.name;
    }
    //    setter
    public ProductName setName(String name)
    {
        isValidName(name);
        this.name = name;
        return this;
    }
}
