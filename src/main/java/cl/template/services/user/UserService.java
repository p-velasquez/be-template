package cl.template.services.user;

import cl.template.exceptions.UserAlreadyExistsException;
import cl.template.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Value("${user.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<User> getUsers() {
        try {
            return objectMapper.readValue(new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            // En caso de error, devuelve una lista vacía
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try {
            List<User> users = getUsers();
            return users.stream()
                    .filter(user -> user.getId().equals(id))
                    .findFirst(); // Encuentra el primer usuario con el ID, o devuelve un Optional vacío si no lo encuentra
        } catch (Exception e) {
            return Optional.empty(); // Devuelve Optional vacío en caso de error
        }
    }


    @Override
    public void createUser(User user) {
        try {
            List<User> users = getUsers();
            boolean userExists = users.stream()
                    .anyMatch(existingUser -> existingUser.getId().equals(user.getId()));

            if (userExists) {
                throw new UserAlreadyExistsException("User with ID " + user.getId() + " already exists.");
            }

            users.add(user); // Agregar el nuevo usuario a la lista
            objectMapper.writeValue(new File(filePath), users); // Guardar la lista actualizada
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving user", e);
        }
    }

}
