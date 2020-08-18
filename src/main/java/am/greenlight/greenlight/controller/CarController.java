package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.CarRequestDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/car/save")
    public String carAddPage() {
        return "saveCar";
    }

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
    public String carPage(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User user = currentUser.getUser();
        List<Car> cars = carService.findCarByUserId(user.getId());
        model.addAttribute("cars", cars);
        return "carPage";
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