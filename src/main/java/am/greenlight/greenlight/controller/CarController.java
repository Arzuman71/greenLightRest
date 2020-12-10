package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;


    @GetMapping("cars")
    public ResponseEntity<List<Car>> cars(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Car> cars = carService.findCarByUserIdAndStatus(user.getId(), Status.ACTIVE);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("{carId}")
    public ResponseEntity<Optional<Car>> getOne(@PathVariable("carId") int id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Car> save(@Valid @RequestBody CarRequestDto carReq,
                                    BindingResult result, @AuthenticationPrincipal CurrentUser currentUser) {
        if (!result.hasErrors()) {
            Car car = modelMapper.map(carReq, Car.class);
            car.setUser(currentUser.getUser());
            car = carService.save(car);
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.status(403).body(null);
    }

    @PutMapping("image")
    public ResponseEntity.BodyBuilder changeCarImg(@RequestParam("id") int id,
                                                   @RequestParam("img") MultipartFile file) {
        Car car = carService.getOne(id);
        carService.save(car, file);
        return ResponseEntity.ok();
    }

    //ToDo test, CurrentUser
    @PutMapping("")
    public ResponseEntity.BodyBuilder changeCarData(@RequestBody Car car) {
        carService.save(car);
        return ResponseEntity.ok();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteCar(@PathVariable("id") int id) {
        Car car = carService.getOne(id);
        if (car != null) {
            car.setStatus(Status.ARCHIVED);
            carService.save(car);
        }
        return ResponseEntity.ok();
    }

}