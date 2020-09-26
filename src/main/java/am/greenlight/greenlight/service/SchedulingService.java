package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulingService {

    private final ItemService announcementService;
    private final AdvertisementService advertisementService;
    private final UserRepo userRepo;


    @Scheduled(cron = " 0 0 3 * * *")
    public void changeAnnouncementsStatus() {
        List<Item> announcements = announcementService.findAll();
        announcements.stream()
                .filter(a -> a.getDeadline().isBefore(LocalDateTime.now().plus(4, ChronoUnit.HOURS)))
                .forEach(a -> a.setStatus(Status.ARCHIVED));

    }

    @Scheduled(cron = " 0 0 5 * * *")
    public void changeAdvertisementsStatus() {
        List<Advertisement> advertisements = advertisementService.findAll();
        advertisements.stream()
                .filter(a -> a.getDeadline().isBefore(LocalDateTime.now().plus(1, ChronoUnit.DAYS)))
                .forEach(a -> a.setStatus(Status.ARCHIVED));

    }

    @Scheduled(cron = " 0 0 7 * * *")
    public void removeUserOtp() {
        List<User> users = userRepo.findAll();
        users.stream()
                .filter(u -> u.getCreatedDate().isBefore(LocalDateTime.now().plus(7, ChronoUnit.HOURS)))
                .forEach(u -> u.setOtp(""));

    }
}

