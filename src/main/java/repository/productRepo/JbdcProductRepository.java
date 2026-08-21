package repository.productRepo;

import entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class JbdcProductRepository implements ProductRepository {
    private final Connection connection;
//    constructor
    public JbdcProductRepository(Connection connection)
    {
        this.connection = Objects.requireNonNull(connection, "Connection cannot be null");

    }
//    save Product
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
                created_at,
                weight
                )
                VALUES(?,?,?,?,?,?,?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, product.getName().toString());
            ps.setInt(2, product.getQuantity());
            ps.setBigDecimal(3,product.getBasePrice());
            ps.setString(4,product.getStatus().toString());
            ps.setString(5,product.getProductType().toString());
            ps.setTimestamp(6,java.sql.Timestamp.from(product.getCreatedAt()));
            // thuộc tính riêng của physical product
            if(product instanceof PhysicalProduct)
            {
                ps.setBigDecimal(7,((PhysicalProduct) product).getWeight());
            }
            else if(product instanceof DigitalProduct)
            {
                ps.setNull(7, Types.DECIMAL);
            }
//          // Thực thi câu lệnh INSERT xuống Postgre
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Error while saving product into DATABASE: " + e.getMessage(),e);
        }
    }
//    find product by id
    @Override
    public Optional<Product> findById(String productId)
    {
        String sql = """
                SELECT
                product_name,
                quantity,
                price,
                status,
                type,
                created_at,
                weight
                FROM products
                WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            // chạy lệnh SELECT
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
            {
                return Optional.empty();
            }
            ProductName product_name = new ProductName(rs.getString("product_name"));
            int quantity = rs.getInt("quantity");
            BigDecimal price = rs.getBigDecimal("price");
            ProductStatus status = ProductStatus.valueOf(rs.getString("status"));
            ProductType type = ProductType.valueOf(rs.getString("type"));
            Instant created_at = rs.getTimestamp("created_at").toInstant();
            BigDecimal weight = rs.getBigDecimal("weight");
            if(weight != null)
            {
                Product data;
                if(type == ProductType.PHYSICAL)
                {
                    data = new PhysicalProduct(
                            productId,
                            product_name,
                            quantity,
                            price,
                            status,
                            type,
                            created_at,
                            weight
                    );
                }
                else{
                    data = new DigitalProduct(
                            productId,
                            product_name,
                            quantity,
                            price,
                            status,
                            type,
                            created_at
                    );
                }
                return Optional.of(data);
            }
            else{
                return Optional.empty();
            }


        }catch(SQLException e)
        {
            throw new RuntimeException("Error while searching for products: " + e.getMessage(), e);
        }
    }
}
