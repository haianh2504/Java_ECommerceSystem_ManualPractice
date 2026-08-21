import infrastructure.DatabaseConnection;

import java.sql.SQLException;
import java.util.*;

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