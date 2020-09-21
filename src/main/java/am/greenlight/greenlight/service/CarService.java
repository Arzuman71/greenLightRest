package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.CarRepository;
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
public class CarService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car save(Car car, MultipartFile file) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(uploadDir, name);
            file.transferTo(picCar);
            car.setPicCar(name);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return carRepository.save(car);
    }

    public Car getOne(long id) {
        return carRepository.getOne(id);
    }

    public List<Car> findCarByUserId(long id) {
        return carRepository.findCarByUserId(id);
    }

    public List<Car> findCarByUserIdAndStatus(long id, Status state) {
        return carRepository.findCarByUserIdAndStatus(id, state);
    }

}
