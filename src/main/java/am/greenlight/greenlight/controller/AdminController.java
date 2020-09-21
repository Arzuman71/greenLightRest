package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AnnouncementService announcementService;

    @GetMapping("/user/find")
    public ResponseEntity<List<User>> findUserByNameAndSurname(@ModelAttribute User user) {
        List<User> users = userService.findByNameAndSurname(user.getName(), user.getSurname());
        if (users == null) {
            users = new ArrayList<>();
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("id") long id,
                                             @AuthenticationPrincipal CurrentUser currentUser) {
        String mes;
        User user = currentUser.getUser();
        if (user.getRole() == Role.USER && user.getId() == id) {
            user.setStatus(Status.ARCHIVED);
            userService.save(user);
            mes = "you have deleted your account";
        } else {
            User userDelete = userService.getOne(id);
            userDelete.setStatus(Status.DELETED);
            userService.save(userDelete);
            mes = "you have deleted  " + user.getName() + " account";

        }
        return ResponseEntity.ok(mes);

    }

}
