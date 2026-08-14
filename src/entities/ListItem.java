package entities;

public class ListItem {
    private String productId;
    private int number;
//    constructor
    public ListItem(String productId, int number)
    {
        if(productId == null)
        {
            throw new NullPointerException("Product ID in ListItem cannot be null");
        }
        // need to check if the product Id exists
        if(number <= 0) // also need to check the upper bound
        {
            throw new IllegalArgumentException("The number in ListItem is invalid");
        }
    }
//    getters
    public final String getProductId()
    {
        return this.productId;
    }
    public final int getNumber()
    {
        return this.number;
    }
//    setter
    public void changeNumber(int number)
    {
        if(number <= 0 || number == this.number) // also need to check the upper bound
        {
            throw new IllegalArgumentException("The number is invalid");
        }
        this.number = number;
    }
    public void changeNumberByOne()
    {
        this.number += 1;
    }
}
