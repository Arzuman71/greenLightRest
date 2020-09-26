package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.AnnouncementRequestDto;
import am.greenlight.greenlight.dto.AnnouncementResponseDto;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AnnouncementService;
import am.greenlight.greenlight.service.CarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final CarService carService;
    private final ModelMapper modelMapper;


    @PostMapping("/announcement")
    public ResponseEntity<Object> addAnnouncement(@ModelAttribute AnnouncementRequestDto announcementReq,
                                                  @AuthenticationPrincipal CurrentUser currentUser) {
        Car car = null;
        long carId = announcementReq.getCarId();
        if (carId != 0) {
            car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
            }
        }
        Announcement announcement = modelMapper.map(announcementReq, Announcement.class);
        announcement.setCar(car);
        Announcement announcementSow = announcementService.save(announcement);
        return ResponseEntity.ok(announcementSow);
    }

    //ok
    @GetMapping("/announcement/{id}")
    public ResponseEntity<AnnouncementResponseDto> getOne(@PathVariable("id") Long id) {
        Announcement announcement = announcementService.getOne(id);
        AnnouncementResponseDto announcementResDto = modelMapper.map(announcement, AnnouncementResponseDto.class);
        return ResponseEntity.ok(announcementResDto);
    }

    @GetMapping("/announcement/active")
    public ResponseEntity<List<Announcement>> getAnnouncementActive(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Announcement> announcements = announcementService.findAllByUserIdAndState(id, Status.ACTIVE);

        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/announcement/archived")
    public ResponseEntity<List<Announcement>> getAnnouncementArchived(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Announcement> announcements = announcementService.findAllByUserIdAndState(id, Status.ARCHIVED);

        return ResponseEntity.ok(announcements);
    }


    //ok
    @PutMapping("/announcement/change")
    public ResponseEntity<AnnouncementRequestDto> changeAnnouncement(@RequestBody AnnouncementRequestDto announcementReq,
                                                                     @AuthenticationPrincipal CurrentUser currentUser) {
        long carId = announcementReq.getCarId();
        if (carId != 0) {
            Car car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(announcementReq);
            }
        }
        Announcement announcement = modelMapper.map(announcementReq, Announcement.class);
        announcementService.save(announcement);
        return ResponseEntity.ok(announcementReq);
    }

    //ok
    @DeleteMapping("/announcement/{id}")
    public ResponseEntity.BodyBuilder deleteAnnouncement(@PathVariable("id") long id,
                                                         CurrentUser currentUser) {

        Announcement announcement = announcementService.getOne(id);
        if (!(announcement.getUser().equals(currentUser.getUser()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }

        announcement.setStatus(Status.DELETED);
        announcementService.save(announcement);
        return ResponseEntity.ok();
    }


}