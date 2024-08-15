package cl.template.controllers;

import cl.template.controllers.response.BaseResponse;
import cl.template.enums.UserResponseCode;
import cl.template.exceptions.UserAlreadyExistsException;
import cl.template.models.User;
import cl.template.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = iUserService.getUsers();
        return ResponseEntity.ok(users);
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

}
