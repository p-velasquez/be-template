package cl.template.service.user;

import cl.template.models.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    User getUserById(Long id);
}
