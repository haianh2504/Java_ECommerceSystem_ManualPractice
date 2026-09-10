package user.repository;

import user.entities.Email;
import user.entities.PhoneNumber;
import user.entities.User;

import java.util.Optional;

public interface UserRepository {
    public User save(User user);
    public User update(User user);
    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(Email email);
    public Optional<User> findByPhoneNumber(PhoneNumber phoneNumber);
}
