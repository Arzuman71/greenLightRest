package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.upload.userAvatar.dir}")
    private String userAvatarDir;

    private final UserRepo userRepo;
    private final PreferenceService prefService;
    private final EmailService emailService;


    public User save(User user) {
        if (user.getPreference() == null) {
            user.setPreference(prefService.getOne(1));
        }
        return userRepo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    public boolean saveUserImg(User user, MultipartFile file) {

        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(userAvatarDir, name);
            file.transferTo(picUrl);
            user.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        userRepo.save(user);
        return true;
    }

    public List<User> findByNameAndSurname(String name, String surname) {
        return userRepo.findByNameAndSurname(name, surname);
    }

    public User getOne(long id) {
        return userRepo.getOne(id);
    }


    public void sendMessageToMailForRegister(User user, Locale locale) {
        String link = "http://localhost:8080/user/activate?email=" + user.getEmail() + "&otp=" + user.getOtp();
        emailService.sendHtmlEmil(user.getEmail(), "Բարի գալուստ GreenLight.am", user, link, "email/userWelcomeMail.html", locale);
    }

    public void sendMessageToMailForForgotPassword(User user, Locale locale) {
        String link = "http://localhost:3000/user/forgotPassword/reset?email=" + user.getEmail() + "&otp=" + user.getOtp();
        emailService.sendHtmlEmil(user.getEmail(), "GreenLight.am - Փոխել գաղտնաբառը", user, link, "email/forgotPasswordMail.html", locale);
    }
}
