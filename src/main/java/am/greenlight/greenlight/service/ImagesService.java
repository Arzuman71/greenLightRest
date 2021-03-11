package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Images;
import am.greenlight.greenlight.repository.CarRepo;
import am.greenlight.greenlight.repository.ImagesRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final ImagesRepo imagesRepo;
    private final CarRepo carRepo;
    private static final Logger log = LoggerFactory.getLogger(ImagesService.class);

    @Value("${file.upload.dir}")
    private String carPictureDir;

    public Images getOne(long id) {
        return imagesRepo.getOne(id);
    }

    public void save(long CarId, MultipartFile file) {
        Optional<Car> car = carRepo.findById(CarId);
        if (car.isPresent()) {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(carPictureDir, name);
            Images images = Images.builder()
                    .name(name)
                    .car(car.get())
                    .build();
            try {
                file.transferTo(picCar);
                imagesRepo.save(images);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("save image car with this id - {}", car.get().getId());
        }
    }

    public void change(long pictureId, MultipartFile file) {
        Optional<Images> images = imagesRepo.findById(pictureId);
        if (images.isPresent()) {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(carPictureDir, name);
            images.get().setName(name);
            try {
                file.transferTo(picCar);
                imagesRepo.save(images.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteById(long id) {
        log.info("car image with this id - {} deleted", id);
        imagesRepo.deleteById(id);
    }
}
