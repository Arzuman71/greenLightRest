package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class AnnouncementResponseDto {
    private String fromIs;
    private String whereIs;
    private LocalDateTime deadline;
    private String price;
    @ManyToOne
    private User user;
    @ManyToOne
    private Car car;
    @Enumerated(value = EnumType.STRING)
    private AnnouncementType announcementType;
    private int numberOfPassengers;
    private LocalDateTime createdDate;
}
