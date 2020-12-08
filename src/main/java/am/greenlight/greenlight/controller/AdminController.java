package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class AdminController {

    private final UserService userService;

    @GetMapping("find/{name}/{surname}")
    public ResponseEntity<List<User>> findUserByNameAndSurname(
            @PathVariable("name") String name,
            @PathVariable("surname") String surname) {
        List<User> users = userService.findByNameAndSurname(name, surname);
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable("userId") long id) {
        User userDelete = userService.getOne(id);
        userDelete.setStatus(Status.DELETED);
        userService.save(userDelete);

        return ResponseEntity.ok("you have deleted  " + userDelete.getName() + " account");
    }

}
