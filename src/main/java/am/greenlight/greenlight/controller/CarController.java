package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/car/save")
    public String saveCar(@ModelAttribute CarRequestDto carReq, @RequestParam("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        Car car = Car.builder()
                .id(id)
                .carModel(carReq.getCarModel())
                .carBrand(carReq.getCarBrand())
                .user(currentUser.getUser())
                .year(carReq.getYear())
                .carNumber(carReq.getCarNumber())
                .color(carReq.getColor())
                .carType(carReq.getCarType())
                .build();

        carService.save(car);
        return "redirect:/user";
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> cars(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        List<Car> cars = carService.findCarByUserIdAndState(user.getId(), Status.ACTIVE);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/car/edit")
    public String carEditPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("car", carService.getOne(id));
        return "editCar";
    }

    @GetMapping("/car/saveImage")
    public String carSaveImagePage(@RequestParam("id") int id, Model model) {
        Car car = carService.getOne(id);
        model.addAttribute("car", car);
        return "changeCarImage";
    }

    @PostMapping("/car/saveImage")
    public String changeCarImg(@RequestParam("id") int id,
                               @RequestParam("img") MultipartFile file) {
        Car car = carService.getOne(id);
                carService.saveCar(car, file);
                return "redirect:/user";

    }

    @GetMapping("/car/delete")
    public String deleteAuthor(@RequestParam("id") int id) {
        carService.deleteCarById(id);
        return "redirect:/user";
    }

}