package order.repository;

import order.entities.Order;
import order.entities.OrderStatus;
import order_item.repository.OrderItemRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JdbcOrderRepository implements OrderRepository {
    private final Connection connection;
//    constructor
    public JdbcOrderRepository(Connection connection) {
        this.connection = Objects.requireNonNull(connection, "connection cannot be null");
    }
//    save order
    public void save(Order order) {
        String sql = """
                INSERT INTO orders(
                user_id,
                status,
                created_at,
                sub_total,
                shipping_fee,
                discount_amount,
                total_price
                )
                VALUES(?,?,?,?,?,?,?);
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1, order.getUserId());
            ps.setString(2,order.getOrderStatus().name());
            ps.setTimestamp(3, java.sql.Timestamp.from(order.getCreatedAt()));
            ps.setBigDecimal(4, order.getSubTotal());
            ps.setBigDecimal(5, order.getShippingFee());
            ps.setBigDecimal(6, order.getDiscountAmount());
            ps.setBigDecimal(7, order.getTotalPrice());
            ps.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while saving order: " + e.getMessage(),e);
        }
    }
//    get orders by userId
    @Override
    public List<Order> findByUserId(Long userId) {
        String sql = """
                SELECT * FROM orders WHERE user_id = ?;
        """;
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1,userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                orders.add(
                        new Order(
                                rs.getLong("id"),
                                rs.getLong("user_id"),
                                OrderStatus.valueOf(rs.getString("status")),
                                rs.getTimestamp("created_at").toInstant(),
                                rs.getBigDecimal("sub_total"),
                                rs.getBigDecimal("shipping_fee"),
                                rs.getBigDecimal("discount_amount"),
                                rs.getBigDecimal("total_price")
                        )
                );
            }
        }catch(SQLException e)
        {
            throw new RuntimeException("Error while fetching orders: " + e.getMessage(),e);
        }
        return orders;
    }
//    get order by orderId
    @Override
    public Optional<Order> findByOrderId(Long orderId) {
        String sql = """
                SELECT * FROM orders WHERE order_id = ?;
        """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
           ps.setLong(1,orderId);
           ResultSet rs = ps.executeQuery();
           if(!rs.next())
               {
               return Optional.empty();
               }
           return Optional.of(
                   new Order(
                           rs.getLong("id"),
                           rs.getLong("user_id"),
                           OrderStatus.valueOf(rs.getString("status")),
                           rs.getTimestamp("created_at").toInstant(),
                           rs.getBigDecimal("sub_total"),
                           rs.getBigDecimal("shipping_fee"),
                           rs.getBigDecimal("discount_amount"),
                           rs.getBigDecimal("total_price")
                   )
           );
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while fetching orders: " + e.getMessage(),e);
        }
    }
//    delete Order by orderId
    @Override
    public void deleteByOrderId(Long orderId) {
        String sql = """
                DELETE FROM orders WHERE order_id = ?;
        """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setLong(1,orderId);
            ps.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while deleting orders: " + e.getMessage(),e);
        }
    }
}
