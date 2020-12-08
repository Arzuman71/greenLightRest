package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.*;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.RatingService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil tokenUtil;
    private final EmailService emailService;


    @GetMapping("")
    public ResponseEntity<UserResDto> getUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        UserResDto userGetDto = modelMapper.map(user, UserResDto.class);
        return ResponseEntity.ok(userGetDto);
    }

    @PostMapping("auth")
    public ResponseEntity<Object> auth(@RequestBody AuthRequestDto authRequest) {
        Optional<User> byEmail = userService.findByEmail(authRequest.getEmail());

        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getStatus() == Status.ACTIVE && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                AuthResponseDto auth = new AuthResponseDto(tokenUtil.generateToken(user.getEmail()));
                return ResponseEntity.ok(auth);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PostMapping("")
    public ResponseEntity<Integer> register(@Valid @RequestBody UserRegisterDto userRegister,
                                            BindingResult result, Locale locale) {
        if (!result.hasErrors() && userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            Optional<User> byEmail = userService.findByEmail(userRegister.getEmail());

            if (!byEmail.isPresent()) {
                User user = modelMapper.map(userRegister, User.class);
                user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                user.setOtp(UUID.randomUUID().toString());
                userService.save(user);
                sendMessageToEmail(user);
                return ResponseEntity.ok(0);
            } else if (byEmail.get().getStatus() == Status.ARCHIVED) {
                long id = byEmail.get().getId();
                User user = modelMapper.map(userRegister, User.class);
                user.setId(id);
                user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                user.setOtp(UUID.randomUUID().toString());
                userService.save(user);
                sendMessageToEmail(user);
                return ResponseEntity.ok(0);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getFieldErrorCount());
    }

    private void sendMessageToEmail(User user) {
        log.error("user with address {} email was could not registered", user.getEmail());
        String link = "http://localhost:8080/user/activate?email=" + user.getEmail() + "&otp=" + user.getOtp();
        emailService.send(user.getEmail(), "Welcome", "Dear " + user.getName() + ' ' + user.getSurname() + " You have successfully registered.Please " +
                "activate your account by clicking on: " + link);
    }


    //TODO test
    @GetMapping("/user/activate")
    public ResponseEntity<String> activate(@RequestParam("email") String email,
                                           @RequestParam("otp") String otp) {

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getOtp().equals(otp)) {
                user.setStatus(Status.ACTIVE);
                user.setOtp("");
                userService.save(user);
                return ResponseEntity.ok("User was activate,please login");
            }
        }
        return ResponseEntity.badRequest().body("Something went wrong.Please try again");
    }

    @PutMapping("about")
    public ResponseEntity<String> changeAbout(@AuthenticationPrincipal CurrentUser currentUser,
                                              @RequestBody AboutChangeDto aboutDto) {
        User user = currentUser.getUser();
        user.setAbout(aboutDto.getAbout());
        userService.save(user);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("avatar")
    public ResponseEntity<String> changeAvatar(@AuthenticationPrincipal CurrentUser currentUser,
                                               @RequestParam MultipartFile file) {
        if (userService.saveUserImg(currentUser.getUser(), file)) {
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PutMapping("")
    public ResponseEntity.BodyBuilder change(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody UserChangeDto userChangeDto, BindingResult result) {

        if (!result.hasErrors()) {
            User userTmp = modelMapper.map(userChangeDto, User.class);
            User user = currentUser.getUser();
            user.userChange(userTmp);
            userService.save(user);
            return ResponseEntity.ok();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("password/change")
    public ResponseEntity<String> passwordChange(@Valid @RequestBody PasswordChangeDto pasChange, BindingResult result,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        if (!result.hasErrors()
                && passwordEncoder.matches(pasChange.getOldPassword(), user.getPassword())
                && pasChange.getPassword().equals(pasChange.getConfirmPassword())) {

            user.setPassword(passwordEncoder.encode(pasChange.getPassword()));
            userService.save(user);
            return ResponseEntity.ok("Your password changed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incompatibility");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        user.setStatus(Status.ARCHIVED);
        userService.save(user);
        return ResponseEntity.ok("you have deleted your account");

    }

    @GetMapping("forgotPassword")
    public ResponseEntity<String> forgotPass(@RequestParam("email") String email) {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent() && byEmail.get().getStatus() == Status.ACTIVE) {
            User user = byEmail.get();
            user.setOtp(UUID.randomUUID().toString());
            userService.save(user);
            String link = "http://localhost:8080/user/forgotPassword/reset?email=" + user.getEmail() + "&token=" + user.getOtp();
            emailService.send(user.getEmail(), "Welcome", "Dear " + user.getName() + ' ' + user.getSurname() + "activate your account by clicking on:" + link);

            return ResponseEntity.ok("Your password changed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this email not existent");
    }

    @GetMapping("forgotPassword/reset")
    public ResponseEntity<ConfirmEmailDto> reset(@RequestParam("email") String email,
                                                 @RequestParam("otp") String otp) {
        ConfirmEmailDto confirmEmail = new ConfirmEmailDto();

        Optional<User> byUsername = userService.findByEmail(email);
        if (byUsername.isPresent() && byUsername.get().getOtp().equals(otp)) {
            confirmEmail.setEmail(byUsername.get().getEmail());
            confirmEmail.setToken(byUsername.get().getOtp());
            return ResponseEntity.ok(confirmEmail);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(confirmEmail);
    }

    // front end-ը պետք է տա օտպը ու էմաիլը
    @PostMapping("forgotPassword/change")
    public ResponseEntity.BodyBuilder changePass(@RequestBody ForgotPasswordDto forgotPass) {

        Optional<User> byEmail = userService.findByEmail(forgotPass.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getOtp().equals(forgotPass.getOtp())
                    && forgotPass.getPassword().equals(forgotPass.getRepeatPassword())) {

                user.setPassword(passwordEncoder.encode(forgotPass.getPassword()));
                userService.save(user);
                return ResponseEntity.ok();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }
}