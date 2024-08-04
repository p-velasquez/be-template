package cl.template.service.user;

import cl.template.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().build());
        return users;
    }

    @Override
    public User getUserById(Long id){
        return User.builder().id(id).build();
    }
}
