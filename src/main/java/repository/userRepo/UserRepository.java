package repository.userRepo;

import entities.Email;
import entities.User;

import java.util.Optional;

public interface UserRepository {
    public void save(User user);
    public Optional<User> findById(String id);
    public Optional<User> findByEmail(Email email);
}
