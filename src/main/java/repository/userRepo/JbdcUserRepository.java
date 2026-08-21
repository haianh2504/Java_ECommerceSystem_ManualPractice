package repository.userRepo;

import entities.*;

import javax.swing.text.html.Option;
import java.sql.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class JbdcUserRepository implements UserRepository{
    // repository needs connection to communicate with DATABASE
    private final Connection connection;
//    constructor
    public JbdcUserRepository(Connection connection)
    {
        this.connection = Objects.requireNonNull(connection, "Connection cannot be null");
    }
//    save user
    @Override
    public void save(User user)
    {
        // SQL
        String sql = """
                INSERT INTO users(
                name,
                phone_number,
                email,
                role,
                status,
                password_hash,
                created_at
                )
                VALUES(?,?,?,?,?,?,?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, user.getName().toString());
            if(user.getPhoneNumber() != null){
                ps.setString(2,user.getPhoneNumber().toString());
            }
            else{
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setString(3,user.getEmail().toString());
            ps.setString(4,user.getRole().name());
            ps.setString(5,user.getStatus().name());
            ps.setString(6, user.getPasswordHash().toString());
            // from: Instant | valueOf: LocalDateTime
            ps.setTimestamp(7,java.sql.Timestamp.from(user.getTimeCreated()));
            // Thực thi câu lệnh INSERT xuống Postgre
            ps.executeUpdate();
        }catch(SQLException e)
        {
            throw new RuntimeException("Error while saving user into DATABASE: " + e.getMessage(),e);
        }
    }
//    find user by id
    @Override
    public Optional<User> findById(String id){
        String sql = """
                SELECT
                name,
                phone_number,
                email,
                role,
                status,
                password_hash,
                created_at
                FROM users WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return Optional.empty();
            }
            PersonName name = new PersonName(rs.getString("name"));
            Email email = new Email(rs.getString("email"));
            UserRole role = UserRole.valueOf(rs.getString("role"));
            UserStatus status = UserStatus.valueOf(rs.getString("status"));
            PasswordHash password_hash = new PasswordHash(rs.getString("password_hash"));
            Instant created_at = rs.getTimestamp("created_at").toInstant();
            String sqlPhone = rs.getString("phone_number");
            if(sqlPhone != null){
                PhoneNumber phone_number = new PhoneNumber(sqlPhone);
                User sqlUser = new User(
                        id,
                        password_hash,
                        name,
                        phone_number,
                        email,
                        role,
                        status,
                        created_at
                        );
                return Optional.of(sqlUser);
            }
            else{
                User sqlUser = new User(
                        id,
                        password_hash,
                        name,
                        email,
                        role,
                        status,
                        created_at
                        );
                return Optional.of(sqlUser);
            }
        }catch (SQLException e)
        {
            throw new RuntimeException("Error while searching user: " + e.getMessage(),e);
        }
    }
//    find user by email
    @Override
    public Optional<User> findByEmail(Email email){
        String sql = """
                SELECT
                id,
                name,
                phone_number,
                email,
                role,
                status,
                password_hash,
                created_at
                FROM users
                WHERE email = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql))
        {
            // chạy lệnh SELECT
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) return Optional.empty();
            // lay thuoc tinh
            String id = String.valueOf(rs.getLong("id"));
            PersonName name = new PersonName(rs.getString("name"));
            Email user_email = new Email(rs.getString("email"));
            UserRole role = UserRole.valueOf(rs.getString("role"));
            UserStatus status = UserStatus.valueOf(rs.getString("status"));
            PasswordHash password_hash = new PasswordHash(rs.getString("password_hash"));
            Instant created_at = rs.getTimestamp("created_at").toInstant();
            String sqlPhone = rs.getString("phone_number");
            if(sqlPhone != null)
            {
                PhoneNumber phone_number = new PhoneNumber(sqlPhone);
                return Optional.of(new User(
                        id,
                        password_hash,
                        name,
                        phone_number,
                        user_email,
                        role,
                        status,
                        created_at
                ));
            }
            else {
                return Optional.of(new User(
                        id,
                        password_hash,
                        name,
                        user_email,
                        role,
                        status,
                        created_at
                ));
            }

        }catch(SQLException e)
        {
            throw new RuntimeException("Error while searching user: " + e.getMessage(),e);
        }
    }

}
