package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.UserRequestDto;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.State;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.MainService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final MainService mainService;
    private final AnnouncementService announcementService;


    //todo
    @GetMapping("/")
    public String mainPage(ModelMap model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdDate")));
        //AnnounForShowDto
        Page<Announcement> allAnnouncement = announcementService.findAll(pageRequest);

        int totalPages = allAnnouncement.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("allAnnouncement", allAnnouncement);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(ModelMap modelMap, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            error = "Wrong data";
            modelMap.addAttribute("error", error);
        }
        return "login";
    }


    @GetMapping("/user/register/P")
    public String registerPage(@ModelAttribute("userRequestDto") UserRequestDto userRequest) {

        return "register";
    }


    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        if (!user.getActive() || user.getState() == State.DELETED) {
            return "redirect:/";
        }
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }


    @GetMapping(value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {

        return mainService.getImage(imageName);
    }

}
