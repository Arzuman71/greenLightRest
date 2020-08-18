package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final PreferenceService prefService;


    public void save(User user) {
        if (user.getPreference() == null) {
            user.setPreference(prefService.getOne(1));
        }
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }


    public void saveUserImg(User user, MultipartFile file) {

        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(uploadDir, name);

            file.transferTo(picUrl);

            user.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
    }

    public List<User> findByNameSurname(String name, String surname) {
        return userRepository.findByNameAndSurname(name, surname);
    }

    public User getOne(long id) {
        return userRepository.getOne(id);
    }


    public void deleteById(long id) {
        userRepository.deleteById(id);

    }


}
