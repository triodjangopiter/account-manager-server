package ru.pravvich.web.user;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Role;
import ru.pravvich.domain.User;
import ru.pravvich.service.UserService;

import static java.util.stream.Collectors.toList;

/**
 * @author Pavel Ravvich.
 */
@RestApi
@RestController
@RequestMapping("/user")
public class UserController {

    @NonNull
    private final UserService userService;

    @Autowired
    public UserController(@NonNull UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public UserRest registration(@RequestBody() UserRest user) {
        return null;
    }

    private UserRest toRest(@NonNull User user) {
        UserRest rest = new UserRest();
        rest.setId(user.getId());
        rest.setUsername(user.getUsername());
        rest.setPassword(user.getPassword());
        rest.setAccountNonLocked(user.isAccountNonLocked());
        rest.setRoles(user.getRoles().stream().map(Role::getRole).collect(toList()));
        return rest;
    }
}
