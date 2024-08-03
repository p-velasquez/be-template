package cl.template.controllers;

import cl.template.models.User;
import cl.template.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService IUserService;
    @GetMapping
    public List<User> getUsers() {
        return IUserService.getUsers();
    }
}
