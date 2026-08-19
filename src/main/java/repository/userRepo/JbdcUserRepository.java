package repository.userRepo;

import entities.Email;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
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

    }
//    find user by email
    @Override
    public Optional<User> findByEmail(Email email){

    }

}
