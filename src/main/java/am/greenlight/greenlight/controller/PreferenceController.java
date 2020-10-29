package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.PreferenceService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preference")
public class PreferenceController {


    private final PreferenceService prefService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> save(
            @AuthenticationPrincipal CurrentUser currentUser,
            @RequestBody Preference newPreference) {

        User user = currentUser.getUser();
        prefService.save(newPreference, user.getPreference());
        user.setPreference(newPreference);
        userService.save(user);
        return ResponseEntity.ok("OK");
    }
}