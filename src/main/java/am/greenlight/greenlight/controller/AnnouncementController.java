package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.AnnouncementRequestDto;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CarService carService;
    private final RatingService ratingService;
    private final ModelMapper modelMapper;


    @PostMapping("/announcement/Add")
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
        Announcement announcement = modelMapper.map(announcementReq, Announcement.class);

        announcementService.save(announcement);
        return "redirect:/user";
    }

    @GetMapping("/announcement/byUserIdAndState")
    public String getAnnouncement(ModelMap modelMap,
                                  @AuthenticationPrincipal CurrentUser currentUser,
                                  @RequestParam("state") Status state) {
        if (state == Status.DELETED) {
            return "redirect:/";
        }
        long id = currentUser.getUser().getId();
        List<Announcement> announcements = announcementService.findAllByUserIdAndState(id, state);

        modelMap.addAttribute("announcements", announcements);
        if (state == Status.ARCHIVED) {
            return "finishedAnnouncement";
        }
        return "userAnnouncement";
    }




    @GetMapping("/driverAndPassengerAnnouncementP")
    public String driverAndPassengerAnnouncement(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        long userId = currentUser.getUser().getId();
        List<Car> cars = carService.findCarByUserIdAndState(userId, Status.ACTIVE);
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
            cars = carService.findCarByUserIdAndState(userId, Status.ACTIVE);

        } else if (announcement.getAnnouncementType() == AnnouncementType.TRUCK_DRIVER) {
            path = "changeTruckAnnouncementP";
            cars = carService.findCarByUserIdAndState(userId, Status.ACTIVE);

        } else if (announcement.getAnnouncementType() == AnnouncementType.SEEKER_TRUCK) {
            path = "changePeopleAnnouncementP";
        }
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("announcement", announcement);
        return path;

    }

    @PutMapping("/announcement/Change")
    public String changeAnnouncement(@ModelAttribute AnnouncementRequestDto announcementReq,
                                     @RequestParam(name = "carId", required = false) Long carId,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        if (carId != null && carId != 0) {
            Car car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return "redirect:/";
            }
        }
        Announcement announcement =  modelMapper.map(announcementReq,Announcement.class);
        announcementService.save(announcement);
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

        announcement.setStatus(Status.DELETED);
        announcementService.save(announcement);
        modelMap.addAttribute("msg", "ձեր հայտարարությունը հաջողությամբ ջնջվեց");
        return "messagePage";
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