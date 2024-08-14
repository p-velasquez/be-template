package cl.template.service.user;

import cl.template.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    Optional<User> getUserById(Long id);
    void createUser(User user);
    }
