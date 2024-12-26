package az.developia.task_management.controllers;

import az.developia.task_management.dtos.request.OwnerAddRequest;
import az.developia.task_management.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addOwner(@RequestBody OwnerAddRequest ownerAddRequest) {

        userService.addOwner(ownerAddRequest);
    }

    @GetMapping(path = "/login")
    public void login() {

    }
}
