import common.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try{
            DatabaseConnection.getConnection();
            System.out.println("Connect Database Successfully!");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}