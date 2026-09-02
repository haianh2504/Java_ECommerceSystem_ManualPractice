package cart.repository;

import cart.entities.Cart;
import cart.entities.CartStatus;
import cart_item.entities.CartItem;
import common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class JbdcCartRepository implements CartRepository {
    private final Connection connection;
//    constructor
    public JbdcCartRepository(Connection connection){
        this.connection = Objects.requireNonNull(connection, "Database connection undefined");
    }
//    Find cart by id
    @Override
    public Optional<Cart> findById(Long cartId) {
        String sql = """
                SELECT
                id,
                user_id,
                created_at,
                status
                FROM carts WHERE id = ?;
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            // add key word into ?
            ps.setLong(1, cartId);
            // activate sql query
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return Optional.empty();
            }
            return Optional.of(
                    new Cart(
                            rs.getLong("id"),
                            rs.getLong("user_id"),
                            rs.getTimestamp("created_at").toInstant(),
                            CartStatus.valueOf(rs.getString("status"))
                    )
            );
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while searching cart by id: " + e.getMessage(), e);
        }
    }
//    Find cart by userId
    @Override
    public Optional<Cart> findByUserId(Long userId) {
        String sql = """
                SELECT
                id,
                user_id,
                created_at,
                status
                FROM carts WHERE user_id = ?;
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if(!rs.next())
            {
                return Optional.empty();
            }
            return Optional.of(
                    new Cart(
                            rs.getLong("id"),
                            rs.getLong("user_id"),
                            rs.getTimestamp("created_at").toInstant(),
                            CartStatus.valueOf(rs.getString("status"))
                    )
            );
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while searching cart by user id: " + e.getMessage(), e);
        }
    }
//    save cart
    @Override
    public void save(Cart cart) {
        String sql = """
                INSERT INTO carts(
                user_id,
                created_at
                ) VALUES (?, ?);
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, cart.getUserId());
            ps.setTimestamp(2, java.sql.Timestamp.from(cart.getCreatedAt()));
            ps.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while saving cart to database: " + e.getMessage(), e);
        }
    }
//    delete cart - // hard delete
    @Override
    public void deleteById(Long cartId) {
        String sql = """
                DELETE FROM carts WHERE id = ?;
        """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, cartId);
            ps.executeUpdate();
        }catch(SQLException e)
        {
            throw new RuntimeException("Error while deleting cart from database: " + e.getMessage(), e);
        }
    }
//    update cart - tạm thời chỉ status
    @Override
    public void update(Cart cart) {
        String sql = """
                UPDATE carts
                SET
                status = ?
                WHERE cart_id = ?;
        """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,cart.getCartStatus().name());
            ps.setLong(2,cart.getCartId());
            ps.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while updating cart from database: " + e.getMessage(), e);
        }
    }
}
