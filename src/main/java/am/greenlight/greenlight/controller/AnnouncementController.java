package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.AnnouncementRequestDto;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;
import am.greenlight.greenlight.model.enumForUser.State;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CarService carService;
    private final RatingService ratingService;


    @PostMapping("/addAnnouncement")
    public String addAnnouncement(@ModelAttribute AnnouncementRequestDto announcementReq,
                                  @AuthenticationPrincipal CurrentUser currentUser,
                                  @RequestParam(name = "carId", required = false) Long carId) {
        if (carId != null && carId != 0) {
            Car car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return "redirect:/";
            }
            announcementReq.setCar(car);
        }
        Announcement announcement = Announcement.builder()
                .id(0)
                .fromIs(announcementReq.getFromIs())
                .whereIs(announcementReq.getWhereIs())
                .startDate(announcementReq.getStartDate())
                .price(announcementReq.getPrice())
                .state(State.ACTIVE)
                .user(currentUser.getUser())
                .car(announcementReq.getCar())
                .announcementType(announcementReq.getAnnouncementType())
                .numberOfPassengers(announcementReq.getNumberOfPassengers())
                .build();
        announcementService.save(announcement);
        return "redirect:/user";
    }

    @GetMapping("/Announcement/ByUserAndState")
    public String getAnnouncement(ModelMap modelMap,
                                  @AuthenticationPrincipal CurrentUser currentUser,
                                  @RequestParam("state") State state) {
        if (state == State.DELETED) {
            return "redirect:/";
        }
        long id = currentUser.getUser().getId();
        List<Announcement> announcements = announcementService.findAllByUserIdAndState(id, state);

        modelMap.addAttribute("announcements", announcements);
        if (state == State.ARCHIVED) {
            return "finishedAnnouncement";
        }
        return "userAnnouncement";
    }

    @GetMapping("/truckAndPeopleAnnouncementP")
    public String truckAndPeopleAnnouncement(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        long userId = currentUser.getUser().getId();
        List<Car> cars = carService.findCarByUserIdAndState(userId, State.ACTIVE);
        modelMap.addAttribute("cars", cars);
        return "truckAndPeopleAnnouncement";
    }


    @GetMapping("/driverAndPassengerAnnouncementP")
    public String driverAndPassengerAnnouncement(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        long userId = currentUser.getUser().getId();
        List<Car> cars = carService.findCarByUserIdAndState(userId, State.ACTIVE);
        modelMap.addAttribute("cars", cars);
        return "driverAndPassengerAnnouncement";
    }

    //TODO js միջոցով բդի ենենք

    @GetMapping("/changeAnnouncementP")
    public String changeAnnouncementP(ModelMap modelMap,
                                      @AuthenticationPrincipal CurrentUser currentUser,
                                      @RequestParam("id") long id) {
        long userId = currentUser.getUser().getId();
        String path = "";
        List<Car> cars = null;
        Announcement announcement = announcementService.getOne(id);

        if (announcement.getAnnouncementType() == AnnouncementType.PASSENGER) {
            path = "changePassengerAnnouncementP";
        } else if (announcement.getAnnouncementType() == AnnouncementType.CAR_DRIVER) {
            path = "changeDriverAnnouncementP";
            cars = carService.findCarByUserIdAndState(userId, State.ACTIVE);

        } else if (announcement.getAnnouncementType() == AnnouncementType.TRUCK_DRIVER) {
            path = "changeTruckAnnouncementP";
            cars = carService.findCarByUserIdAndState(userId, State.ACTIVE);

        } else if (announcement.getAnnouncementType() == AnnouncementType.SEEKER_TRUCK) {
            path = "changePeopleAnnouncementP";
        }
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("announcement", announcement);
        return path;

    }

    @PostMapping("/changeAnnouncement")
    public String changeAnnouncement(ModelMap modelMap,
                                     @ModelAttribute AnnouncementRequestDto announcementReq,
                                     @RequestParam(name = "carId", required = false) Long carId,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        if (carId != null && carId != 0) {
            Car car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return "redirect:/";
            }
        }
        Announcement announcement = Announcement.builder()
                .id(0)
                .fromIs(announcementReq.getFromIs())
                .whereIs(announcementReq.getWhereIs())
                .startDate(announcementReq.getStartDate())
                .price(announcementReq.getPrice())
                .user(currentUser.getUser())
                .car(announcementReq.getCar())
                .announcementType(announcementReq.getAnnouncementType())
                .numberOfPassengers(announcementReq.getNumberOfPassengers())
                .createdDate(LocalDateTime.now())
                .build();
        announcementService.save(announcement);
        modelMap.addAttribute("msg", "ձեր հայտարարությունը հաջողությամբ փոփոխվեց");
        return "messagePage";
    }

    @GetMapping("/deleteAnnouncement")
    public String deleteAnnouncement(ModelMap modelMap,
                                     @RequestParam("id") long id,
                                     CurrentUser currentUser) {

        Announcement announcement = announcementService.getOne(id);
        if (!(announcement.getUser().equals(currentUser.getUser()))) {
            return "redirect:/";
        }

        announcement.setState(State.DELETED);
        announcementService.save(announcement);
        modelMap.addAttribute("msg", "ձեր հայտարարությունը հաջողությամբ ջնջվեց");
        return "messagePage";
    }


    @GetMapping("/addAnnouncementP")
    public String addAnnouncementP() {
        return "carOrTruck";
    }


    @GetMapping("/announcement/details")
    public String announcementDetailsP(ModelMap model, @RequestParam("id") Long id) {
        Announcement announcement = announcementService.getOne(id);
        long userId = announcement.getUser().getId();
        Double rating = ratingService.RatingById(userId);

        model.addAttribute("rating", rating);
        model.addAttribute("announcement", announcement);
        return "announcementDetails";
    }

}