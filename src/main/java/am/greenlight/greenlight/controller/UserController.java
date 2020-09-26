package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.*;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.EmailService;
import am.greenlight.greenlight.service.UserService;
import lombok.NonNull;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil tokenUtil;
    private final EmailService emailService;

    //ok
    @GetMapping("/user")
    public ResponseEntity<UserGetDto> getUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        UserGetDto userGetDto = modelMapper.map(user, UserGetDto.class);

        return ResponseEntity.ok(userGetDto);
    }

    //ok
    @PostMapping("user/auth")
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

    @PostMapping("/user")
    public ResponseEntity<Integer> registerUser(@Valid @RequestBody UserRegisterDto userRegister,
                                                BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            if (userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
                Optional<User> byEmail = userService.findByEmail(userRegister.getEmail());

                if (!byEmail.isPresent() || byEmail.get().getStatus() == Status.ARCHIVED) {
                    User user = modelMapper.map(userRegister, User.class);
                    user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                    user.setOtp(UUID.randomUUID().toString());
                    userService.save(user);
                    log.error("user with address {} email was could not registered", user.getEmail());
                    String link = "http://localhost:8080/user/activate?email=" + user.getEmail() + "&token=" + user.getOtp();
                    emailService.send(user.getEmail(), "Welcome", "Dear " + user.getName() + ' ' + user.getSurname() + " You have successfully registered.Please " +
                            "activate your account by clicking on:" + link);
                    return ResponseEntity.ok(0);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getFieldErrorCount());
    }

    @GetMapping("/user/activate")
    public ResponseEntity<String> activate(@RequestParam("email") String email,
                                           @RequestParam("token") String token) {

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getOtp().equals(token)) {
                user.setStatus(Status.ACTIVE);
                user.setOtp("");
                userService.save(user);
                return ResponseEntity.ok("User was activate,please login");
            }
        }
        return ResponseEntity.badRequest().body("Something went wrong.Please try again");
    }

    //ok
    @PutMapping("/user/about")
    public ResponseEntity<String> addAboutMe(@AuthenticationPrincipal CurrentUser currentUser,
                                             @NonNull @RequestBody String about) {

        User user = currentUser.getUser();
        user.setAbout(about);
        userService.save(user);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/user/Img")
    public ResponseEntity.BodyBuilder saveUserImg(@AuthenticationPrincipal CurrentUser currentUser,
                                                  @RequestParam("picUrl") MultipartFile file) {

        if (userService.saveUserImg(currentUser.getUser(), file)) {
            return ResponseEntity.ok();

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/user/data")
    public ResponseEntity<Map<String, Object>> userDataChange(
            @AuthenticationPrincipal CurrentUser currentUser,
            @ModelAttribute UserChangeDto userChangeDto,
            BindingResult result) {

        if (!result.hasErrors()) {
            User userTmp = modelMapper.map(userChangeDto, User.class);
            User user = currentUser.getUser();
            user.userChange(userTmp);
            userService.save(user);
            return ResponseEntity.ok(result.getModel());
        }
        return ResponseEntity.badRequest().body(result.getModel());
    }

    @PutMapping("/user/Password/Change")
    public ResponseEntity<String> userPasswordChange(@AuthenticationPrincipal CurrentUser currentUser,
                                                     @ModelAttribute PasswordChangeDto pasChange, BindingResult result) {
        User user = currentUser.getUser();

        if (!result.hasErrors()
                || passwordEncoder.matches(pasChange.getOldPassword(), user.getPassword())
                || pasChange.getPassword().equals(pasChange.getConfirmPassword())) {

            user.setPassword(passwordEncoder.encode(pasChange.getPassword()));
            userService.save(user);
            return ResponseEntity.ok("Your password changed");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incompatibility");
    }

    @GetMapping("/user/forgotPassword")
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

    @GetMapping("/user/forgotPassword/reset")
    public ResponseEntity<ConfirmEmailDto> reset(@RequestParam("email") String email,
                                                 @RequestParam("token") String token) {
        ConfirmEmailDto confirmEmail = new ConfirmEmailDto();

        Optional<User> byUsername = userService.findByEmail(email);
        if (byUsername.isPresent() && byUsername.get().getOtp().equals(token)) {
            confirmEmail.setEmail(byUsername.get().getEmail());
            confirmEmail.setToken(byUsername.get().getOtp());
            return ResponseEntity.ok(confirmEmail);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(confirmEmail);
    }

    @PostMapping("/user/forgotPassword/change")
    public ResponseEntity.BodyBuilder changePass(@ModelAttribute ForgotPasswordDto forgotPass) {

        Optional<User> byEmail = userService.findByEmail(forgotPass.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getOtp().equals(forgotPass.getToken())
                    && forgotPass.getPassword().equals(forgotPass.getRepeatPassword())) {

                user.setPassword(passwordEncoder.encode(forgotPass.getPassword()));
                userService.save(user);
                return ResponseEntity.ok();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

    //  @PutMapping("/user/activate/byEmail")
    //  public ResponseEntity.BodyBuilder activateByEmail(@RequestParam("email") String email) {

    //      Optional<User> byEmail = userService.findByEmail(email);
    //      if (byEmail.isPresent() && byEmail.get().getStatus() == Status.ARCHIVED) {
    //          User user = byEmail.get();
    //          user.setToken(UUID.randomUUID().toString());

    //          return ResponseEntity.ok();
    //      }
    //      return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    //  }
}