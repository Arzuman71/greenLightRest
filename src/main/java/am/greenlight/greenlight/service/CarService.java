package am.greenlight.greenlight.service;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.CarRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CarService {

    @Value("${file.upload.carPicture.dir}")
    private String carPictureDir;
    private final CarRepo carRepository;
    private static final Logger log = LoggerFactory.getLogger(CarService.class);


    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car save(Car car, MultipartFile file) {
        if (!file.isEmpty()) {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(carPictureDir, name);
            try {
                file.transferTo(picCar);
                car.setPicUrl(name);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return carRepository.save(car);
    }

    public Car getOne(long id) {
        return carRepository.getOne(id);
    }

    public Optional<Car> findById(long id) {
        return carRepository.findById(id);
    }

    public List<Car> findCarByUserId(long id) {
        return carRepository.findCarByUserId(id);
    }

    public List<Car> findCarByUserIdAndStatus(long id, Status state) {
        return carRepository.findCarByUserIdAndStatus(id, state);
    }

}
