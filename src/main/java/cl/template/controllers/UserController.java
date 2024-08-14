package cl.template.controllers;

import cl.template.controllers.response.BaseResponse;
import cl.template.enums.ResponseCode;
import cl.template.exceptions.UserAlreadyExistsException;
import cl.template.models.User;
import cl.template.service.user.IUserService;
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

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return iUserService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@Validated @RequestBody User user) {
        try {
            // Asume que UserAlreadyExistsException se lanza si el usuario ya existe
            iUserService.createUser(user);
            return ResponseEntity.status(ResponseCode.SUCCESS.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(ResponseCode.SUCCESS.getCode())
                            .message(ResponseCode.SUCCESS.getMessage())
                            .build());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(ResponseCode.USER_ALREADY_EXISTS.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(ResponseCode.USER_ALREADY_EXISTS.getCode())
                            .message(ResponseCode.USER_ALREADY_EXISTS.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                    .body(BaseResponse.builder()
                            .code(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                            .message(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                            .build());
        }
    }



}
