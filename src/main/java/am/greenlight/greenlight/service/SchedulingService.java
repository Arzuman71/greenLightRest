package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.enumForUser.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulingService {

    private final AnnouncementService announcementService;
    private final AdvertisementService advertisementService;


    @Scheduled(cron = " 0 0 3 * * *")
    public void changeAnnouncementsStatus() {
        List<Announcement> announcements = announcementService.findAll();
        for (Announcement a : announcements) {
            if (a.getDeadline().isBefore(LocalDateTime.now().plus(4, ChronoUnit.HOURS))) {
                a.setStatus(Status.ARCHIVED);
            }
        }
    }

    @Scheduled(cron = " 0 0 4 * * *")
    public void changeAdvertisementsStatus() {
        List<Advertisement> advertisements = advertisementService.findAll();
        for (Advertisement a : advertisements) {
            if (a.getDeadline().isBefore(LocalDateTime.now().plus(1, ChronoUnit.DAYS))) {
                a.setStatus(Status.ARCHIVED);
            }
        }
    }
}

