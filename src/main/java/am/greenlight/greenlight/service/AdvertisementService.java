package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.repository.AdvertisementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    @Value("${file.upload.dir}")
    private String uploadDir;
    private final AdvertisementRepo advertisementRepo;


    public List<Advertisement> findAll() {
        return advertisementRepo.findAll();

    }

    public void save(Advertisement advertisement, MultipartFile file) {

        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(uploadDir, name);

            file.transferTo(picUrl);
            advertisement.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        advertisementRepo.save(advertisement);
    }


    public void save(Advertisement advertisement) {
        advertisementRepo.save(advertisement);
    }

    public Advertisement getOne(long id) {
        return advertisementRepo.getOne(id);
    }
}
