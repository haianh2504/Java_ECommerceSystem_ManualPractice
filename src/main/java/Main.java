import java.util.*;

public class Main {
    public static String tokenize()
    {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String charStorage = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int boundary = 0;
        for(int i = 0; i < 16; i++)
        {
            boundary++;
            int index = random.nextInt(charStorage.length());
            sb.append(charStorage.charAt(index));
            if(boundary == 4 && i != 16 - 1)
            {
                boundary = 0;
                sb.append("-");
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(tokenize());
    }
}