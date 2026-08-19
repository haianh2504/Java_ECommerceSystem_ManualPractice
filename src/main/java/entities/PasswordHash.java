package entities;

import java.util.Objects;

public final class PasswordHash {
    private String value;
//    constructor
    public PasswordHash(String value)
    {
        this.value = Objects.requireNonNull(value, "Password hash cannot be null");
        if(value.isBlank())
        {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }
    }
//    toString
    @Override
    public String toString()
    {
        return this.value;
    }
}
