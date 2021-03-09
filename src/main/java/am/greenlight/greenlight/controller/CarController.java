package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.dto.CarRes;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public ResponseEntity<List<CarRes>> cars(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Car> cars = carService.findCarByUserIdAndStatus(user.getId(), Status.ACTIVE);
        List<CarRes> carRes = new ArrayList<>();
        cars.forEach(c -> carRes.add(modelMapper.map(c, CarRes.class)));
        return ResponseEntity.ok(carRes);
    }

    @GetMapping("{carId}")
    public ResponseEntity<CarRes> getOne(@PathVariable("carId") int id) {
        Optional<Car> car = carService.findById(id);
        CarRes carRes = new CarRes();
        if (car.isPresent()) {
            carRes = modelMapper.map(car.get(), CarRes.class);
        }
        return ResponseEntity.ok(carRes);
    }

    // stugel e petq karox e chancni, ete chancav jnje path = "/",
    @PostMapping(path = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Car> save(@Valid @RequestBody CarRequestDto carReq,
                                    BindingResult result, @AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam(value = "image", required = false) MultipartFile file) {

        if (!result.hasErrors()) {
            Car car = modelMapper.map(carReq, Car.class);
            car.setUser(currentUser.getUser());
            car.setId(0);
            car = carService.save(car, file);
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.status(403).body(null);
    }

    @PutMapping(path = "/mainPicture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> changeCarImg(@RequestParam("id") int id,
                                          @RequestParam(value = "image") MultipartFile file) {
        Car car = carService.getOne(id);
        carService.save(car, file);
        return ResponseEntity.ok().build();
    }

    //ToDo test, petq e stugel
    @PutMapping()
    public ResponseEntity<CarRes> changeCarData(@Valid @RequestBody CarRequestDto carReqDto,
                                             BindingResult result,
                                             @AuthenticationPrincipal CurrentUser currentUser) {

        if (!result.hasErrors()) {
            Car car = modelMapper.map(carReqDto, Car.class);
            car.setUser(currentUser.getUser());
            Car car1 = carService.save(car);
           CarRes res = modelMapper.map(car1, CarRes.class);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        Car car = carService.getOne(id);
        if (car != null && currentUser.getUser().equals(car.getUser())) {
            car.setStatus(Status.ARCHIVED);
            carService.save(car);
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

}