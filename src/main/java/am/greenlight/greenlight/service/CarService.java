package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.State;
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

    public void save(Car car) {
        car.setState(State.ACTIVE);
        carRepository.save(car);
    }

    public void saveCar(Car car, MultipartFile file) {
        try {
            String name = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            File picCar = new File(uploadDir, name);

            file.transferTo(picCar);

            car.setPicCar(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        carRepository.save(car);

    }

    public Car getOne(long id) {
        return carRepository.getOne(id);
    }

    public List<Car> findCarByUserId(long id) {
        return carRepository.findCarByUserId(id);
    }

    public List<Car> findCarByUserIdAndState(long id, State state) {
        return carRepository.findCarByUserIdAndState(id, state);
    }

    public void deleteCarById(long id) {
        carRepository.deleteById(id);
    }


}
