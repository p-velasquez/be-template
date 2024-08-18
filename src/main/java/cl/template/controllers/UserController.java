package cl.template.controllers;

import cl.template.controllers.response.BaseResponse;
import cl.template.enums.UserResponseCode;
import cl.template.exceptions.UserAlreadyExistsException;
import cl.template.models.User;
import cl.template.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService iUserService;

    @Autowired
    public void setIUserService(IUserService iUserService) {
        this.iUserService = iUserService;
    }
    @GetMapping()
    public String getUserInfo(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        // Obtén datos del usuario autenticado
        String name = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");

        // Devuelve los datos como respuesta o úsalo en la lógica de tu aplicación
        return "Name: " + name + ", Email: " + email;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = iUserService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerOAuthUser(OAuth2AuthenticationToken authentication) {
        System.out.println("ENTRE");
        // Extraer los detalles del usuario autenticado
        if (authentication == null) {
            return ResponseEntity.status(UserResponseCode.UNAUTHORIZED.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(UserResponseCode.UNAUTHORIZED.getCode())
                            .message(UserResponseCode.UNAUTHORIZED.getMessage())
                            .build());
        }

        OAuth2User oAuth2User = authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        List<User> users = iUserService.getUsers();

        // Mapear los datos a tu entidad User
        User user = User.builder()
                        .id((long) users.size())
                        .username((String) attributes.get("login"))
                        .email((String) attributes.get("email"))
                        .name((String) attributes.get("name"))
                        .role("USER").build();

        // Invocar createUser para registrar al usuario
        return createUser(user);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@Validated @RequestBody User user) {
        try {
            // Asume que UserAlreadyExistsException se lanza si el usuario ya existe
            iUserService.createUser(user);
            return ResponseEntity.status(UserResponseCode.USER_CREATED.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(UserResponseCode.USER_CREATED.getCode())
                            .message(UserResponseCode.USER_CREATED.getMessage())
                            .build());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(UserResponseCode.USER_ALREADY_EXISTS.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(UserResponseCode.USER_ALREADY_EXISTS.getCode())
                            .message(UserResponseCode.USER_ALREADY_EXISTS.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(UserResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(UserResponseCode.INTERNAL_SERVER_ERROR.getCode())
                            .message(UserResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return iUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pokemon/{nameOrId}")
    public ResponseEntity<String> getMyPokemonById(@PathVariable String nameOrId) {
        return ResponseEntity.ok(iUserService.getMyPokemonById(nameOrId));
    }
}
