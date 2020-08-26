package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.PasswordChangeDto;
import am.greenlight.greenlight.dto.UserChangeDto;
import am.greenlight.greenlight.dto.UserRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Role;
import am.greenlight.greenlight.model.enumForUser.State;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final CarService carService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/user")
    public String loginPage(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            model.addAttribute("currentUser", user);
            List<Car> cars = carService.findCarByUserIdAndState(user.getId(), State.ACTIVE);

            model.addAttribute("cars", cars);
        }
        return "user";
    }


    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute("userRequestDto") UserRequestDto userRequest,
                               BindingResult result, Locale locale) {
        if (result.hasErrors()) {
            return "register";
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            return "redirect:/?msg=Password and Confirm Password does not match!";
        }
        Optional<User> byEmail = userService.findByEmail(userRequest.getEmail());

        if (byEmail.isPresent()) {
            if (byEmail.get().getState() == State.ARCHIVED) {

                return forRegister(userRequest);
            }
            return "register";
        }
        return forRegister(userRequest);
    }

    private String forRegister(UserRequestDto userRequest) {

        User user = User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .age(userRequest.getAge())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .gender(userRequest.getGender())
                .active(false)
                .role(Role.USER)
                .token(UUID.randomUUID().toString())
                .state(State.ACTIVE)
                .build();
        userService.save(user);

        //  log.error("user with address {} email was could not registered", user.getEmail());

        return "redirect:/";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("email") String email,
                           @RequestParam("token") String token) {

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getToken().equals(token)) {
                user.setActive(true);
                user.setToken("");
                userService.save(user);
                return "redirect:/?msg=User was activate,please login";
            }
        }
        return "redirect:/?msg=Something went wrong.Please try again";
    }


    @PostMapping("/saveAboutMe")
    public String addAboutMe(@AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("aboutMe") String aboutMe) {

        User user = currentUser.getUser();
        user.setAboutMe(aboutMe);
        userService.save(user);
        return "redirect:/user";

    }

    @PostMapping("/saveUserImg")
    public String saveUserImg(@AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("picUrl") MultipartFile file) {

        userService.saveUserImg(currentUser.getUser(), file);
        return "/user";
    }


    @GetMapping("/user/DataChange/p")
    public String userDataChangePage(@AuthenticationPrincipal CurrentUser currentUser,
                                     ModelMap model,
                                     @RequestParam(name = "msg", required = false) String msg) {

        User user = currentUser.getUser();
        UserChangeDto userChangeDto = UserChangeDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .aboutMe(user.getAboutMe())
                //  .email(user.getEmail())
                .gender(user.getGender())
                .build();

        model.addAttribute("userChangeDto", userChangeDto);
        return "userDataChangePage";

    }

    @PostMapping("/userDataChange")
    public String userDataChange(@AuthenticationPrincipal CurrentUser currentUser,
                                 @ModelAttribute UserChangeDto userChangeDto,
                                 BindingResult result) {

        if (result.hasErrors()) {
            return "userDataChangePage";
        }
        User user = currentUser.getUser();
        user.setName(userChangeDto.getName());
        user.setSurname(userChangeDto.getSurname());
        user.setAge(userChangeDto.getAge());
        user.setPhoneNumber(userChangeDto.getPhoneNumber());
        //user.setEmail(userChangeDto.getEmail());
        user.setGender(userChangeDto.getGender());

        userService.save(user);
        return "redirect:/";
    }

    @PostMapping("/user/Password/Change")
    public String userPasswordChange(@AuthenticationPrincipal CurrentUser currentUser,
                                     @ModelAttribute PasswordChangeDto pasChange, BindingResult result) {
        if (result.hasErrors()) {
            return "userDataChangePage";
        }
        User user = currentUser.getUser();
        if (!passwordEncoder.matches(pasChange.getOldPassword(), user.getPassword())) {
            return "redirect:/user/DataChange/p?msg=wrong old Password";
        }
        if (!pasChange.getPassword().equals(pasChange.getConfirmPassword())) {
            return "redirect:/user/DataChange/p?msg=wrong reiteration new Password";
        }
        user.setPassword(passwordEncoder.encode(pasChange.getPassword()));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/user/forgotPassword")
    public String forgotPass(@RequestParam("email") String email) {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().getActive()) {
            User user = byEmail.get();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.save(user);
        }
        return "login";
    }

    @GetMapping("/user/forgotPassword/reset")
    public String reset(@RequestParam("token") String token,
                        @RequestParam("email") String email,
                        Model model) {
        Optional<User> byUsername = userService.findByEmail(email);
        if (byUsername.isPresent() && byUsername.get().getToken().equals(token)) {
            model.addAttribute("email", byUsername.get().getEmail());
            model.addAttribute("token", byUsername.get().getToken());
            return "changePassword";
        }
        return "redirect:/";
    }

    @PostMapping("/user/forgotPassword/change")
    public String chamgePass(@RequestParam("token") String token,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("repetPassword") String repetPassword) {

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getToken().equals(token) && password.equals(repetPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                userService.save(user);
                return "login";
            }
        }
        return "login";
    }

    @GetMapping("/user/activate/byEmail")
    public String activateByEmail(@RequestParam("email") String email) {

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent() && !byEmail.get().getActive()) {
            User user = byEmail.get();
            user.setToken(UUID.randomUUID().toString());

            return "redirect:/";
        }
        return null;
    }
}
//LocalDate today=LocalDate.now();
//        LocalDate birthDate=LocalDate.of(1989,10,4);
//        int years = Period.between(birthDate, today).getYears();

