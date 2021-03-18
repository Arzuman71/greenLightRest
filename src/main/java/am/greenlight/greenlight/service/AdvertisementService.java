package am.greenlight.greenlight.service;

import am.greenlight.greenlight.dto.response.AdvertisementResponse;
import am.greenlight.greenlight.mapper.AdvertisementMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    @Value("${file.upload.dir}")
    private String AdvertisementPicDir;

    private final AdvertisementRepo advertisementRepo;
    private final AdvertisementMapper advertisementMapper;


    public List<AdvertisementResponse> findAll() {
        List<Advertisement> advertisements = advertisementRepo.findAll();
        List<AdvertisementResponse> responseList = advertisements.stream()
                .map(advertisementMapper::toResponse)
                .collect(Collectors.toList());
        return responseList;

    }

    public AdvertisementResponse save(Advertisement advertisement, MultipartFile file) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picUrl = new File(AdvertisementPicDir, name);

            file.transferTo(picUrl);
            advertisement.setPicUrl(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return advertisementMapper.toResponse(advertisementRepo.save(advertisement));
    }


    public Advertisement save(Advertisement advertisement) {
        return advertisementRepo.save(advertisement);
    }

    public Advertisement getOne(long id) {
        return advertisementRepo.getOne(id);
    }
}
