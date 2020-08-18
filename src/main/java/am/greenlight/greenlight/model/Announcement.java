package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;
import am.greenlight.greenlight.model.enumForUser.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue()
    private long id;
    private String fromIs;
    @Column(name = "where_is")
    private String whereIs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    private double price;
    private LocalDateTime createdDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Car car;
    @Enumerated(value = EnumType.STRING)
    private AnnouncementType announcementType;
    private int numberOfPassengers;
    @Enumerated(value = EnumType.STRING)
    private State state;


    // for the future
    // private ParcelType parcelType;
}
