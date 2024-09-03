package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.*;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.security.JwtTokenUtil;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil tokenUtil;


    @GetMapping()
    public ResponseEntity<UserResDto> getUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        UserResDto userGetDto = modelMapper.map(user, UserResDto.class);
        log.info("user with email - {} get its data", user.getEmail());
        return ResponseEntity.ok(userGetDto);
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequestDto authRequest) {

        User user = userService.findByEmail(authRequest.getEmail());
        if (user.getStatus() == Status.ACTIVE && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            AuthResponseDto auth = new AuthResponseDto(tokenUtil.generateToken(user.getEmail()));
            log.info("user with email - {} successful authentication", user.getEmail());
            return ResponseEntity.ok(auth);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PostMapping()
    public ResponseEntity<Integer> register(@Valid @RequestBody UserRegisterDto userRegister,
                                            BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            Optional<User> byEmail = userService.findUserOptionalByEmail(userRegister.getEmail());

            if (!byEmail.isPresent()) {
                User user = modelMapper.map(userRegister, User.class);
                user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                user.setOtp(UUID.randomUUID().toString());
                userService.save(user);
                log.info("user with email - {} was registered", user.getEmail());
                userService.sendMessageToMailForRegister(user, locale);
                return ResponseEntity.ok(0);
            } else if (byEmail.get().getStatus() == Status.ARCHIVED) {
                long id = byEmail.get().getId();
                User user = modelMapper.map(userRegister, User.class);
                user.setId(id);
                user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
                user.setOtp(UUID.randomUUID().toString());
                userService.save(user);
                log.info("user with email - {} was registered its ARCHIVED account ", user.getEmail());
                userService.sendMessageToMailForRegister(user, locale);
                return ResponseEntity.ok(0);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result.getFieldErrorCount());
    }


    //TODO test  localhost:8080/user/activate?email= ,  Exception  
    @GetMapping("/activate")
    public ResponseEntity<String> activate(@RequestParam("email") String email,
                                           @RequestParam("otp") String otp) {

        User user = userService.findByEmail(email);
        if (user.getOtp().equals(otp)) {
            user.setStatus(Status.ACTIVE);
            user.setOtp("");
            userService.save(user);
            log.info("user with email - {} was ACTIVE its account", user.getEmail());
            return ResponseEntity.ok("User was activate,please login");
        }
        return ResponseEntity.badRequest().body("Something went wrong.Please try again");
    }

    @PostMapping(path = "/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeAvatar(@AuthenticationPrincipal CurrentUser currentUser,
                                               @RequestParam("image") MultipartFile file) {
        User user = currentUser.getUser();
        if (userService.saveUserImg(user, file)) {
            log.info("user with email - {} changed its avatar", user.getEmail());
            return ResponseEntity.ok("ok");
        }
        log.info("user with email - {} don't can change its avatar", user.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PutMapping()
    public ResponseEntity<String> change(@AuthenticationPrincipal CurrentUser currentUser,
                                         @RequestBody UserChangeDto userDto, BindingResult result) {

        if (!result.hasErrors()) {
            User user = currentUser.getUser();
            user.userChange(userDto);
            userService.save(user);
            log.info("user with email - {} change its data", user.getEmail());
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PutMapping("/password/change")
    public ResponseEntity<String> passwordChange(@Valid @RequestBody PasswordChangeDto pasChange, BindingResult result,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        if (!result.hasErrors() && passwordEncoder.matches(pasChange.getOldPassword(),
                user.getPassword())) {

            user.setPassword(passwordEncoder.encode(pasChange.getPassword()));
            userService.save(user);
            log.info("user with email - {} change its password", user.getEmail());
            return ResponseEntity.ok("Your password changed");
        }
        log.info("user with email - {} don't can change its password", user.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incompatibility");
    }

    @DeleteMapping()
    public void deleteUser(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        user.setStatus(Status.ARCHIVED);
        userService.save(user);
        log.info("user with email - {} change its Status ARCHIVED", user.getEmail());
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<String> forgotPass(@RequestParam("email") String email, Locale locale) {
        User user = userService.findByEmail(email);
        if (user.getStatus() != Status.DELETED) {
            user.setOtp(UUID.randomUUID().toString());
            userService.save(user);
            log.info("user with email - {} used forgotPass method", user.getEmail());
            userService.sendMessageToMailForForgotPassword(user, locale);
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this email not existent");
    }

    @GetMapping("/forgotPassword/reset")
    public ResponseEntity<Map<String, String>> reset(@RequestParam("email") String email,
                                                     @RequestParam("otp") String otp) {

        Map<String, String> response = new HashMap<>();
        User user = userService.findByEmail(email);
        if (user.getOtp().equals(otp)) {
            response.put("email", user.getEmail());
            response.put("otp", user.getOtp());
            log.info(" email - {} used reset method", email);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // front end-ը պետք է տա օտպը ու էմաիլը
    @PutMapping("/forgotPassword/change")
    public ResponseEntity<String> changePass(@RequestBody ForgotPasswordDto forgotPass) {

        User user = userService.findByEmail(forgotPass.getEmail());
        if (user.getOtp().equals(forgotPass.getOtp())) {
            user.setPassword(passwordEncoder.encode(forgotPass.getPassword()));
            userService.save(user);
            log.info("user with email - {} changed its password", user.getEmail());
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PutMapping("/phone")
    public ResponseEntity<String> savePhoneNumber(@RequestBody HashMap<String, String> phoneNumber,
                                                  @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        user.setPhoneNumber(phoneNumber.get("number"));
        userService.save(user);
        log.info("user with email - {} save its PhoneNumber", user.getEmail());
        return ResponseEntity.ok("Ok");
    }
}