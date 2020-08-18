package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AnnouncementService announcementService;

    @GetMapping("/admin")
    public String adminPage() {

        return "admin";
    }

    @PostMapping("/findUser")
    public String findUserByNameAndSurname(ModelMap modelMap, @ModelAttribute User user) {

        List<User> users = userService.findByNameSurname(user.getName(), user.getSurname());
        modelMap.addAttribute("users", users);

        return "admin";
    }


    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "admin";

    }

}
