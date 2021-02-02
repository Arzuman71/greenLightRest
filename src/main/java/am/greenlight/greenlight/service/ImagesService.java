package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Images;
import am.greenlight.greenlight.repository.CarRepo;
import am.greenlight.greenlight.repository.ImagesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final ImagesRepo imagesRepo;
    private final CarRepo carRepo;

    @Value("${file.upload.dir}")
    private String uploadDir;

    public Images getOne(long id) {
        return imagesRepo.getOne(id);
    }

    public Set<Images> save(long CarId, MultipartFile file) {
        Optional<Car> car = carRepo.findById(CarId);
        if (car.isPresent()) {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(uploadDir, name);
            Images images = Images.builder()
                    .name(name)
                    .car(car.get())
                    .build();
            try {
                file.transferTo(picCar);
                imagesRepo.save(images);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return car.get().getImages();
    }

    public void change(long pictureId, MultipartFile file) {
        Optional<Images> images = imagesRepo.findById(pictureId);
        if (images.isPresent()) {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(uploadDir, name);
            images.get().setName(name);
            try {
                file.transferTo(picCar);
                imagesRepo.save(images.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
