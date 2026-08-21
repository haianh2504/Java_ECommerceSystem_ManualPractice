package infrastructure;
//  Giúp gửi các câu lệnh SQL đến database và kiểm soát
import java.sql.Connection;

//  Tạo ra đối tượng Connection bằng cách khớp chuỗi URL kết nối (Database URL) với Driver phù hợp. ( tạo nên người điều phối )
import java.sql.DriverManager;

//  Xử lý tất cả các lỗi xảy ra trong quá trình tương tác với cơ sở dữ liệu
import java.sql.SQLException;

public class DatabaseConnection {
    // Information for database connection
    private static final String url = System.getenv("DB_URL");
    private static final String username = System.getenv("DB_USERNAME");
    private static final String password = System.getenv("DB_PASSWORD");
//    retrieve connection
    public static Connection getConnection()
    {
        try{
            return DriverManager.getConnection(url,username,password);
        }catch (SQLException e)
        {
            throw new RuntimeException("Kết nối cơ sở dữ liệu thất bại: " + e.getMessage(), e);
        }
    }

}
