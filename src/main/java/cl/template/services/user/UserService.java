package cl.template.services.user;

import cl.template.exceptions.UserAlreadyExistsException;
import cl.template.integrations.pokemon.IPokemonService;
import cl.template.integrations.pokemon.Pokemon;
import cl.template.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${user.file.path}")
    private String filePath;

    private IPokemonService iPokemonService;

    @Autowired
    public void setIPokemonService(IPokemonService iPokemonService) {
        this.iPokemonService = iPokemonService;
    }

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
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void createUser(User user) {
        try {
            LOGGER.info("Creating a new user: {}", user);
            List<User> users = getUsers();
            boolean userExists = users.stream()
                    .anyMatch(existingUser -> existingUser.getId().equals(user.getId())
                            || existingUser.getUsername().equals(user.getUsername()));

            if (userExists) {
                LOGGER.warn("User already exists: {}", user);
                throw new UserAlreadyExistsException("User with ID " + user.getId() + " already exists.");
            }

            users.add(user);
            objectMapper.writeValue(new File(filePath), users);
        } catch (IOException e) {
            LOGGER.error("Error while creating user: {}", user.getUsername(), e);
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public String getMyPokemonById(String id){
        Pokemon pokemon = iPokemonService.getPokemonByNameOrId(id).block();
        return pokemon != null ? "Your pokemon is: ".concat(pokemon.getName()) : "There's no pokemon with your ID";
    }

}
