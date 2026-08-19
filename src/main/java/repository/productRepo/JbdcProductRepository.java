package repository.productRepo;

import entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public final class JbdcProductRepository implements ProductRepository {
    private final Connection connection;
//    constructor
    public JbdcProductRepository(Connection connection)
    {
        this.connection = Objects.requireNonNull(connection, "Connection cannot be null");

    }
//    save User
    @Override
    public void save(Product product)
    {
        String sql = """
                INSERT INTO products(
                product_name,
                quantity,
                price,
                status,
                type,
                created_at
                )
                VALUES(?,?,?,?,?,?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, product.getName().toString());
            ps.setInt(2, product.getQuantity());
            ps.setBigDecimal(3,product.getBasePrice());
            ps.setString(4,product.getStatus().toString());
            ps.setString(5,product.getProductType().toString());
            ps.setTimestamp(6,java.sql.Timestamp.from(product.getCreatedAt()));
//          // Thực thi câu lệnh INSERT xuống Postgre
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error while saving product into DATABASE: " + e.getMessage(),e);
        }
    }
}
