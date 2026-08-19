package entities;

public enum PaymentProviderSupportStatus {
    SUPPORTED("SUPPORTED"),
    UNSUPPORTED("UNSUPPORTED");
    private final String description;
    PaymentProviderSupportStatus(String description)
    {
        this.description = description;
    }
    public final String getDescription()
    {
        return description;
    }
}
