package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PreferenceService prefService;


    public UserService(UserRepo userRepository, PreferenceService prefService) {
        this.userRepo = userRepository;
        this.prefService = prefService;
    }

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
            File picUrl = new File(uploadDir, name);

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
}
