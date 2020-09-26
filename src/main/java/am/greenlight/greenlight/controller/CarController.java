package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;


    @GetMapping("/cars")
    public ResponseEntity<List<Car>> cars(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Car> cars = carService.findCarByUserIdAndStatus(user.getId(), Status.ACTIVE);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getOne(@PathVariable("id") int id) {
        Car car = carService.getOne(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/car/save")
    public ResponseEntity<Car> saveCar(@ModelAttribute CarRequestDto carReq, @AuthenticationPrincipal CurrentUser currentUser) {
        Car car = modelMapper.map(carReq, Car.class);
        car.setUser(currentUser.getUser());
        car = carService.save(car);
        return ResponseEntity.ok(car);
    }


    @PutMapping("/car/Image")
    public ResponseEntity.BodyBuilder changeCarImg(@RequestParam("id") int id,
                                                   @RequestParam("img") MultipartFile file) {
        Car car = carService.getOne(id);
        carService.save(car, file);
        return ResponseEntity.ok();

    }

    @DeleteMapping("/car")
    public ResponseEntity.BodyBuilder deleteCar(@RequestParam("id") int id) {
        Car car = carService.getOne(id);
        car.setStatus(Status.ARCHIVED);
        carService.save(car);
        return ResponseEntity.ok();
    }

}