package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;
import am.greenlight.greenlight.model.enumForUser.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AnnounForShowDto {

    private long id;
    private String fromIs;
    private String whereIs;
   // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    private double price;
    private LocalDateTime createdDate;
    private User user;
    private Car car;
    private AnnouncementType announcementType;
    private int numberOfPassengers;
    private State state;

}
