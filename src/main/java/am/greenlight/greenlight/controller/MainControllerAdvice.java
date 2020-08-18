package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainControllerAdvice {


    @ModelAttribute("user")
    public User username(@AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser != null){
            return currentUser.getUser();
        }
        return null;

    }
}
