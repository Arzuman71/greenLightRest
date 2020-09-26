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
public class AdminController {

    private final UserService userService;
    private final ItemService announcementService;

    @GetMapping("/user/find")
    public ResponseEntity<List<User>> findUserByNameAndSurname(@ModelAttribute User user) {
        List<User> users = userService.findByNameAndSurname(user.getName(), user.getSurname());
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable("id") long id) {
        User userDelete = userService.getOne(id);
        userDelete.setStatus(Status.DELETED);
        userService.save(userDelete);

        return ResponseEntity.ok("you have deleted  " + userDelete.getName() + " account");
    }

}
